package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticsManager {

    private static Logger log = LoggerFactory.getLogger(StatisticsManager.class);

    private ConcurrentSkipListMap<Long, Double> map;

    private ConcurrentSkipListMap<Long, DoubleSummaryStatistics> summaryMap;

    public StatisticsManager() {
        map = new ConcurrentSkipListMap<>();
        summaryMap = new ConcurrentSkipListMap<>();
    }

    public void add(Long timestamp, Double amount) {
        map.put(timestamp, amount);
    }

    public DoubleSummaryStatistics getLastMinute() {
        log.info(summaryMap.lastEntry().getValue().toString());
        return summaryMap.lastEntry().getValue();
    }

    @Scheduled(fixedRate = 1000)
    public void removePastMinutes() {
        // log.info("Cleaner executed");
        Instant lastMinute = Instant.now().minus(60, ChronoUnit.SECONDS);
        log.info(lastMinute.toString());

        map.keySet().removeIf(p -> Instant.ofEpochMilli(p).isBefore(lastMinute));
        log.info(map.toString());

        summaryMap.keySet().removeIf(p -> Instant.ofEpochMilli(p).isBefore(lastMinute));
        log.info(String.valueOf(summaryMap.size()));

        DoubleSummaryStatistics stats = map.entrySet().parallelStream().collect(Collectors.summarizingDouble(Entry::getValue));

        summaryMap.put(Instant.now().toEpochMilli(), stats);
        log.info(summaryMap.lastEntry().getValue().toString());

        // PassiveExpiringMap
    }
}

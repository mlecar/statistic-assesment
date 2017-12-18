package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.DoubleSummaryStatistics;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticsManager {

    private ConcurrentSkipListMap<Long, Double> map;

    private ConcurrentSkipListMap<Long, DoubleSummaryStatisticsWrapper> summaryMap;

    public StatisticsManager() {
        map = new ConcurrentSkipListMap<>();
        summaryMap = new ConcurrentSkipListMap<>();
    }

    public void add(Long timestamp, Double amount) {
        map.put(timestamp, amount);
    }

    public DoubleSummaryStatisticsWrapper getLastMinute() {
        return summaryMap.lastEntry().getValue();
    }

    @Scheduled(fixedRate = 1000)
    public void removePastMinutes() {
        Instant lastMinute = Instant.now().minus(60, ChronoUnit.SECONDS);

        map.keySet().removeIf(p -> Instant.ofEpochMilli(p).isBefore(lastMinute));

        Instant last2Seconds = Instant.now().minus(2, ChronoUnit.SECONDS);
        summaryMap.keySet().removeIf(p -> Instant.ofEpochMilli(p).isBefore(last2Seconds));

        DoubleSummaryStatistics stats = map.entrySet().parallelStream().collect(Collectors.summarizingDouble(Entry::getValue));
        summaryMap.put(Instant.now().toEpochMilli(), new DoubleSummaryStatisticsWrapper(stats));
    }
}

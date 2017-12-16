package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class StatisticsManager {

    private SortedMap<Long, Double> map;

    private ConcurrentNavigableMap<Long, DoubleSummaryStatistics> summaryMap;

    public StatisticsManager() {
        map = Collections.synchronizedSortedMap(new TreeMap<>());
    }

    public void add(Long timestamp, Double amount) {
        Instant lastMinute = Instant.now().minus(60, ChronoUnit.SECONDS);

        // filter all last minute and summarizes it
        DoubleSummaryStatistics stats = map.entrySet().stream().filter(p -> Instant.ofEpochMilli(p.getKey()).isAfter(lastMinute)).collect(Collectors.summarizingDouble(Entry::getValue));

        summaryMap.put(Instant.now().plus(60, ChronoUnit.SECONDS).toEpochMilli(), stats);

        // map.put(Instant.ofEpochMilli(timestamp).plus(60,
        // ChronoUnit.SECONDS).toEpochMilli(), map.get())

        /*
         * if (map.containsKey(timestamp)) { map.put(timestamp,
         * map.get(timestamp).add(new BigDecimal(amount))); } else {
         * map.put(timestamp, new BigDecimal(amount)); }
         */
    }

    @Async
    public void removePastMinutes() {
        Instant lastMinute = Instant.now().minus(60, ChronoUnit.SECONDS);
        Long minuteEdge = null;
        for (Long key : map.keySet()) {
            if (lastMinute.isBefore(Instant.ofEpochMilli(key))) {
                minuteEdge = key;
                break;
            }
        }
        if (minuteEdge != null) {
            map.subMap(map.firstKey(), Instant.ofEpochMilli(minuteEdge).toEpochMilli()).clear();
        }
    }

    public DoubleSummaryStatistics getLastMinute(Instant now) {

        Entry<Long, DoubleSummaryStatistics> last = summaryMap.lastEntry();
        if (now.isBefore(Instant.ofEpochMilli(last.getKey()))) {
            return last.getValue();
        }

        // DoubleSummaryStatistics stats =
        // map.values().stream().collect(Collectors.summarizingDouble(BigDecimal::doubleValue));
        return null;
    }

}

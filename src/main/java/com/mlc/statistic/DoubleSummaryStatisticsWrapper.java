package com.mlc.statistic;

import java.util.DoubleSummaryStatistics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DoubleSummaryStatisticsWrapper {

    private long count;
    private double sum;
    private double avg;
    private double max;
    private double min;

    public DoubleSummaryStatisticsWrapper(DoubleSummaryStatistics stats) {
        count = stats.getCount();
        sum = stats.getSum();
        avg = stats.getAverage();
        min = stats.getMin();
        max = stats.getMax();
    }

    public long getCount() {
        return count;
    }

    public double getSum() {
        return sum;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        if (count == 0l) {
            return 0.0;
        }
        return max;
    }

    public double getMin() {
        if (count == 0l) {
            return 0.0;
        }
        return min;
    }

}

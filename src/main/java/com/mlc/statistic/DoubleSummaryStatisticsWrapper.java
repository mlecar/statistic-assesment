package com.mlc.statistic;

import java.util.DoubleSummaryStatistics;

public class DoubleSummaryStatisticsWrapper {

    private DoubleSummaryStatistics stats;

    public DoubleSummaryStatisticsWrapper(DoubleSummaryStatistics stats) {
        this.stats = stats;
    }

    public long getCount() {
        return stats.getCount();
    }

    public double getSum() {
        return stats.getSum();
    }

    public double getAvg() {
        return stats.getAverage();
    }

    public double getMax() {
        if (stats.getCount() == 0l) {
            return 0.0;
        }
        return stats.getMax();
    }

    public double getMin() {
        if (stats.getCount() == 0l) {
            return 0.0;
        }
        return stats.getMin();
    }

}

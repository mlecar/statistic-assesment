package com.mlc.statistic;

import javax.validation.constraints.NotNull;

public class Statistic {

    @NotNull
    private Long timestamp;

    @NotNull
    private Double amount;

    public Statistic() {

    }

    public Statistic(long timestamp, double amount) {
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

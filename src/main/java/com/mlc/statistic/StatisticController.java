package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
// @RequestMapping(value = { "/transactions/", "/transactions" })
public class StatisticController {

    @Autowired
    private StatisticsManager statistics;

    @ResponseBody
    @RequestMapping(value = { "/transactions/", "/transactions" }, method = RequestMethod.POST, produces = { "application/json" })
    public String transactions(@NotNull Double amount, @NotNull long timestamp) {

        if (Instant.ofEpochMilli(timestamp).isBefore(Instant.now().minus(60, ChronoUnit.SECONDS))) {
            return 204;
        }

        statistics.add(timestamp, amount);

        statistics.calculateLastMinute();

        statistics.removePastMinutes();

        return 201;
    }

    @ResponseBody
    @RequestMapping(value = { "/statistics/", "/statistics" }, method = RequestMethod.GET, produces = { "application/json" })
    public String statistics() {

        return statistics.getLastMinute(Instant.now());
    }

}

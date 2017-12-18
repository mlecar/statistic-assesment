package com.mlc.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    @Autowired
    private StatisticsManager statistics;

    @ResponseBody
    @GetMapping(value = { "/statistics/", "/statistics" }, produces = { "application/json" })
    public DoubleSummaryStatisticsWrapper statistics() {

        return statistics.getLastMinute();
    }

}

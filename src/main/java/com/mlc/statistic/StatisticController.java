package com.mlc.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticController {

    @Autowired
    private StatisticsManager statistics;

    @ResponseBody
    @RequestMapping(value = { "/statistics/", "/statistics" }, method = RequestMethod.GET, produces = { "application/json" })
    public DoubleSummaryStatisticsWrapper statistics() {

        return statistics.getLastMinute();
    }

}

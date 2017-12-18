package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/")
public class TransactionController {

    @Autowired
    private StatisticsManager statistics;

    @ResponseBody
    @PostMapping(value = { "/transactions/", "/transactions" }, produces = { "application/json" })
    public ResponseEntity<String> transactions(@Valid @RequestBody Statistic stat) {

        if (Instant.ofEpochMilli(stat.getTimestamp()).isBefore(Instant.now().minus(60, ChronoUnit.SECONDS))) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }

        statistics.add(stat.getTimestamp(), stat.getAmount());

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

}

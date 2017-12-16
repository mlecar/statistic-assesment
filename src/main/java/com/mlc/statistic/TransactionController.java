package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class TransactionController {

    @Autowired
    private StatisticsManager statistics;

    @ResponseBody
    @RequestMapping(value = { "/transactions/", "/transactions" }, method = RequestMethod.POST, produces = { "application/json" })
    public ResponseEntity<String> transactions(@NotNull Double amount, @NotNull Long timestamp) {

        if (Instant.ofEpochMilli(timestamp).isBefore(Instant.now().minus(60, ChronoUnit.SECONDS))) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }

        statistics.add(timestamp, amount);

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

}

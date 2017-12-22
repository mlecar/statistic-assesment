package com.mlc.statistic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
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
    private JmsTemplate jmsTemplate;

    @ResponseBody
    @PostMapping(value = { "/transactions/", "/transactions" }, produces = { "application/json" })
    public ResponseEntity<String> transactions(@Valid @RequestBody Statistic stat) {

        if (Instant.ofEpochMilli(stat.getTimestamp()).isBefore(Instant.now().minus(60, ChronoUnit.SECONDS))) {
            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> message = new HashMap<>();
        message.put("amount", stat.getAmount());
        message.put("timestamp", stat.getTimestamp());
        jmsTemplate.convertAndSend("transactions", message);

        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

}

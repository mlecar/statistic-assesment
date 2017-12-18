package com.mlc.statistic;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsManager manager;

    private String baseContext = "/transactions";

    @Test
    public void currentTimestamp() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 15.8);
        params.put("timestamp", Instant.now().toEpochMilli());
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isCreated()).andExpect(content().string(""));
    }

    @Test
    public void timestampOlderThan60Seconds() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 15.8);
        params.put("timestamp", Instant.now().minus(300, ChronoUnit.SECONDS).toEpochMilli());
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isNoContent()).andExpect(content().string(""));
    }

    @Test
    public void lastCenturyTimestamp() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 15.8);
        params.put("timestamp", LocalDateTime.now().minusYears(5).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isNoContent()).andExpect(content().string(""));
    }

    @Test
    public void nullTimestamp() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 15.8);
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isBadRequest());
    }

    @Test
    public void nullAmount() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", LocalDateTime.now().minusYears(5).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isBadRequest());
    }

    @Test
    public void emptyBody() throws Exception {
        Map<String, Object> params = new HashMap<>();
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isBadRequest());
    }

    @Test
    public void nullBody() throws Exception {
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void internalServerError() throws Exception {
        doThrow(new RuntimeException("Any exception")).when(manager).add(any(), any());
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 15.8);
        params.put("timestamp", Instant.now().toEpochMilli());
        this.mockMvc.perform(post(baseContext).contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(params))).andExpect(status().isInternalServerError());
    }

}

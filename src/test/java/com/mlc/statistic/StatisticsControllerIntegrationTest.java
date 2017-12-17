package com.mlc.statistic;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.DoubleSummaryStatistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StatisticsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatisticsManager manager;

    private String baseContext = "/statistics";

    @Test
    public void notRecentStatistis() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        DoubleSummaryStatisticsWrapper customStas = new DoubleSummaryStatisticsWrapper(stats);
        when(manager.getLastMinute()).thenReturn(customStas);
        this.mockMvc.perform(get(baseContext)).andExpect(status().isOk()).andExpect(jsonPath("$.max").value(0.0)).andExpect(jsonPath("$.min").value(0.0));
    }

    @Test
    public void count() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(10.0);
        stats.accept(15.0);
        DoubleSummaryStatisticsWrapper customStas = new DoubleSummaryStatisticsWrapper(stats);
        when(manager.getLastMinute()).thenReturn(customStas);
        this.mockMvc.perform(get(baseContext)).andExpect(status().isOk()).andExpect(jsonPath("$.count").value(2l));
    }

    @Test
    public void sum() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(25.0);
        stats.accept(36.0);
        DoubleSummaryStatisticsWrapper customStas = new DoubleSummaryStatisticsWrapper(stats);
        when(manager.getLastMinute()).thenReturn(customStas);
        this.mockMvc.perform(get(baseContext)).andExpect(status().isOk()).andExpect(jsonPath("$.sum").value(61.0));
    }

    @Test
    public void average() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(10.0);
        stats.accept(30.0);
        DoubleSummaryStatisticsWrapper customStas = new DoubleSummaryStatisticsWrapper(stats);
        when(manager.getLastMinute()).thenReturn(customStas);
        this.mockMvc.perform(get(baseContext)).andExpect(status().isOk()).andExpect(jsonPath("$.avg").value(20.0));
    }

    @Test
    public void min() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(98.0);
        stats.accept(30.0);
        stats.accept(7.6);
        DoubleSummaryStatisticsWrapper customStas = new DoubleSummaryStatisticsWrapper(stats);
        when(manager.getLastMinute()).thenReturn(customStas);
        this.mockMvc.perform(get(baseContext)).andExpect(status().isOk()).andExpect(jsonPath("$.min").value(7.6));
    }

    @Test
    public void max() throws Exception {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(98.4);
        stats.accept(30.0);
        stats.accept(7.6);
        DoubleSummaryStatisticsWrapper customStas = new DoubleSummaryStatisticsWrapper(stats);
        when(manager.getLastMinute()).thenReturn(customStas);
        this.mockMvc.perform(get(baseContext)).andExpect(status().isOk()).andExpect(jsonPath("$.max").value(98.4));
    }

}

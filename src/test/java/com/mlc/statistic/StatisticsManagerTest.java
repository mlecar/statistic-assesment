package com.mlc.statistic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class StatisticsManagerTest {

    @Spy
    private StatisticsManager manager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void removePastMinutes() {
        Instant now = Instant.now();
        double amount = 10.0;

        manager.add(now.minus(65, ChronoUnit.SECONDS).toEpochMilli(), amount);
        manager.add(now.minus(61, ChronoUnit.SECONDS).toEpochMilli(), amount);
        manager.add(now.toEpochMilli(), amount);
        manager.add(now.plus(1, ChronoUnit.SECONDS).toEpochMilli(), amount);

        manager.removePastMinutes();

        DoubleSummaryStatisticsWrapper stat = manager.getLastMinute();
        assertThat(stat.getCount(), is(2l));
        assertThat(stat.getMax(), is(amount));
        assertThat(stat.getMin(), is(amount));
        assertThat(stat.getSum(), is(amount * 2));
        assertThat(stat.getAvg(), is(amount));
    }

    @Test
    public void removeBeforeAddIsCalled() {
        manager.removePastMinutes();

        DoubleSummaryStatisticsWrapper stat = manager.getLastMinute();
        assertThat(stat.getCount(), is(0l));
        assertThat(stat.getMax(), is(0.0));
        assertThat(stat.getMin(), is(0.0));
        assertThat(stat.getSum(), is(0.0));
        assertThat(stat.getAvg(), is(0.0));
    }

}

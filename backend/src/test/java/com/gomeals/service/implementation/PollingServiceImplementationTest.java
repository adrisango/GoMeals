package com.gomeals.service.implementation;

import com.gomeals.model.Polling;
import com.gomeals.repository.PollingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PollingServiceImplementationTest {
    @Mock
    PollingRepository pollingRepository;

    @InjectMocks
    PollingServiceImplementation pollingServiceImplementation;

    long millis = System.currentTimeMillis();
    Date date = new Date(millis);

    @Test
    public void testCreatePoll() {
        Polling polling = new Polling(1, date, "12", "Paneer", "Bread", "Roti", "Chips", "Butter", true, 12);

        when(pollingRepository.save(polling)).thenReturn(polling);

        Polling savedPolling = pollingServiceImplementation.createPoll(polling);

        assertThat(savedPolling).isNotNull();
    }

    @Test
    public void testCreatePollWhenPollIsNull() {
        when(pollingRepository.save(null)).thenReturn(null);

        Polling polling = pollingServiceImplementation.createPoll(null);

        assertNull(polling);
    }

    @Test
    public void testGetPollById() {
        Polling testPolling = new Polling(1, date, "12", "Paneer", "Bread", "Roti", "Chips", "Butter", true, 12);
        when(pollingRepository.findById(1)).thenReturn(Optional.of(testPolling));

        Polling polling = pollingServiceImplementation.getPollById(1);

        assertEquals("pollId", 1, polling.getPollId());
    }

    @Test
    public void testGetPollByIdWhenIdNotExists() {
        when(pollingRepository.findById(1)).thenReturn(Optional.empty());

        Polling polling = pollingServiceImplementation.getPollById(1);

        assertNull(polling);
    }

    @Test
    public void testUpdatePollWhenPollIsPresent() {
        Polling polling = new Polling(1, date, "12", "Paneer", "Bread", "Roti", "Chips", "Butter", true, 12);

        when(pollingRepository.findById(1)).thenReturn(Optional.of(polling));
        polling.setItem1("Rice");
        polling.setVote("15");

        Polling updatedPolling = pollingServiceImplementation.updatePoll(polling);

        assertThat(updatedPolling.getItem1()).isEqualTo("Rice");
        assertThat(updatedPolling.getVote()).isEqualTo("15");
    }

    @Test
    public void testUpdatePollWhenPollIsNotPresent() {
        Polling polling = new Polling(1, date, "12", "Paneer", "Bread", "Roti", "Chips", "Butter", true, 12);

        when(pollingRepository.findById(1)).thenReturn(Optional.empty());

        Polling updatedPolling = pollingServiceImplementation.updatePoll(polling);

        assertNull(updatedPolling);
    }
}

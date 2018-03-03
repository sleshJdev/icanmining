package com.miner.mono.config;

import com.miner.mono.service.UpdateService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

@Configuration
@EnableScheduling
@Profile("!" + Profiles.TEST)
public class ScheduleConfiguration {
    private final UpdateService updateService;

    public ScheduleConfiguration(UpdateService updateService) {
        this.updateService = updateService;
    }

    @Scheduled(fixedDelayString = "${app.schedule.exchange-rate.update.delay}")
    public void updateExchangeRate() throws IOException {
        updateService.updateExchangeRate();
    }

    @Scheduled(fixedDelayString = "${app.schedule.wallet.update.delay}")
    public void updateBalance() throws IOException {
        updateService.updateBalance();
    }
}

package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsService {

    private final PowerStatsRepository powerStatsRepository;

    @Transactional
    public UUID create(PowerStats powerStats) {
        return powerStatsRepository.create(powerStats);
    }

    public PowerStats getPowerStats(UUID id) {
        return powerStatsRepository.getPowerStats(id);
    }

    public void delete(UUID powerStatsId) {
        powerStatsRepository.delete(powerStatsId);
    }

    public void update(UUID id, PowerStats powerStats) {
        powerStatsRepository.update(id, powerStats);
    }
}

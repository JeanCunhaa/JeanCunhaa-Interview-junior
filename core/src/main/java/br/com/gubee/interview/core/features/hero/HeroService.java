package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import br.com.gubee.interview.model.response.HeroDifferenceResponse;
import br.com.gubee.interview.model.response.HeroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HeroService {

    private final HeroRepository heroRepository;
    private final PowerStatsService powerStatsService;

    @Transactional
    public UUID create(CreateHeroRequest createHeroRequest) {
        UUID powerStatsId = powerStatsService.create(new PowerStats(createHeroRequest));
        return heroRepository.create(new Hero(createHeroRequest, powerStatsId));
    }

    public HeroResponse getHeroById(String id) {
        Hero hero = heroRepository.getHeroById(UUID.fromString(id));
        PowerStats powerStats = powerStatsService.getPowerStats(hero.getPowerStatsId());
        return HeroResponse.createHeroResponse(hero, powerStats);
    }

    public List<HeroResponse> getHeroByName(String name) {
        try {
            List<HeroResponse> heroes = heroRepository.getHeroByName(name).stream().map(hero -> {
                PowerStats powerStats = powerStatsService.getPowerStats(hero.getPowerStatsId());
                return HeroResponse.createHeroResponse(hero, powerStats);
                    }
            ).collect(Collectors.toList());
            return heroes;
        }catch (EmptyResultDataAccessException e){
            return new ArrayList<>();
        }
    }

    @Transactional
    public void delete(String id) {
        Hero hero = heroRepository.getHeroById(UUID.fromString(id));
        heroRepository.delete(UUID.fromString(id));
        powerStatsService.delete(hero.getPowerStatsId());
    }

    public List<HeroResponse> getAll() {
        return heroRepository.getAll().stream().map(hero -> {
            PowerStats powerStats = powerStatsService.getPowerStats(hero.getPowerStatsId());
            return HeroResponse.createHeroResponse(hero, powerStats);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void update(UpdateHeroRequest updateHeroRequest) {
        Hero hero = heroRepository.getHeroById(UUID.fromString(updateHeroRequest.getId()));
        powerStatsService.update(hero.getPowerStatsId(), updateHeroRequest.toPowerStats());
        updateHeroRequest.setIdPowerStats(hero.getPowerStatsId());
        heroRepository.update(updateHeroRequest.tohero());
    }
    public HeroDifferenceResponse getDifference(String heroId1, String heroId2) {
        PowerStats powerStats1 = powerStatsService.getPowerStats(heroRepository.getHeroById(UUID.fromString(heroId1)).getPowerStatsId());
        PowerStats powerStats2 = powerStatsService.getPowerStats(heroRepository.getHeroById(UUID.fromString(heroId2)).getPowerStatsId());
        return HeroDifferenceResponse.difference(heroId1, powerStats1, heroId2, powerStats2);
    }
}

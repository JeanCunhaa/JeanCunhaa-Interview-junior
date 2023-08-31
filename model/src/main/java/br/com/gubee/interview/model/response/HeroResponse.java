package br.com.gubee.interview.model.response;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroResponse {

    private String id;

    private String name;

    private Race race;

    @JsonIgnore
    private String powerStatsId;

    private int strength;

    private int agility;

    private int dexterity;

    private int intelligence;

    private Instant createdAt;

    private Instant updatedAt;

    private Boolean enabled;

    public static HeroResponse createHeroResponse(Hero hero, PowerStats powerStats) {
        return HeroResponse.builder().id(hero.getId().toString())
                .name(hero.getName())
                .race(hero.getRace())
                .strength(powerStats.getStrength())
                .agility(powerStats.getAgility())
                .dexterity(powerStats.getDexterity())
                .intelligence(powerStats.getIntelligence())
                .createdAt(hero.getCreatedAt())
                .updatedAt(hero.getUpdatedAt())
                .enabled(hero.isEnabled()).build();
    }
}

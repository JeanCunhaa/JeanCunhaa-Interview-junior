package br.com.gubee.interview.model.request;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateHeroRequest {

    @NotNull
    @NotBlank
    private String id;

    @NotBlank(message = "message.name.mandatory")
    @Length(min = 1, max = 255, message = "message.name.length")
    private String name;

    @NotNull(message = "message.race.mandatory")
    private Race race;

    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    @NotNull(message = "message.powerstats.strength.mandatory")
    private int strength;

    @Min(value = 0, message = "message.powerstats.agility.min")
    @Max(value = 10, message = "message.powerstats.agility.max")
    @NotNull(message = "message.powerstats.agility.mandatory")
    private int agility;

    @Min(value = 0, message = "message.powerstats.dexterity.min")
    @Max(value = 10, message = "message.powerstats.dexterity.max")
    @NotNull(message = "message.powerstats.dexterity.mandatory")
    private int dexterity;

    @Min(value = 0, message = "message.powerstats.intelligence.min")
    @Max(value = 10, message = "message.powerstats.intelligence.max")
    @NotNull(message = "message.powerstats.intelligence.mandatory")
    private int intelligence;

    @JsonIgnore
    private UUID idPowerStats;

    public PowerStats toPowerStats() {
        return new PowerStats(
                UUID.fromString(id),
                strength,
                agility,
                dexterity,
                intelligence,
                null,
                null
        );
    }
    public Hero tohero() {
        Hero hero = new Hero(new CreateHeroRequest(
                name,
                race,
                strength,
                agility,
                dexterity,
                intelligence
        ), idPowerStats);
        hero.setId(UUID.fromString(id));
        return hero;
    }
}

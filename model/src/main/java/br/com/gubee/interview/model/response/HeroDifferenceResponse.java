package br.com.gubee.interview.model.response;

import br.com.gubee.interview.model.PowerStats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeroDifferenceResponse {

    private String idHero1;

    private String idHero2;

    private int strength;

    private int agility;

    private int dexterity;

    private int intelligence;

    public static HeroDifferenceResponse difference(String idHero1, PowerStats powerStats1, String idHero2, PowerStats powerStats2){
        HeroDifferenceResponse winner = new HeroDifferenceResponse();
        winner.setIdHero1(idHero1);
        winner.setIdHero2(idHero2);
        winner.setStrength(powerStats1.getStrength() - powerStats2.getStrength());
        winner.setAgility(powerStats1.getAgility() - powerStats2.getAgility());
        winner.setDexterity(powerStats1.getDexterity() - powerStats2.getDexterity());
        winner.setIntelligence(powerStats1.getIntelligence() - powerStats2.getIntelligence());
        return winner;
    }
}

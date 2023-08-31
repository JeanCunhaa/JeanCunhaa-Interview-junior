package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
        " (strength, agility, dexterity, intelligence)" +
        " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";

    private static final String GET_POWERSTATS_QUERY = "SELECT * FROM power_stats" +
            " WHERE id = :id";

    private static final String UPDATE_POWERSTATS_QUERY = "UPDATE power_stats SET " +
            "strength = :strength, " +
            "agility = :agility, " +
            "dexterity = :dexterity, " +
            "intelligence = :intelligence " +
            "WHERE id = :id";

    private static final String DELETE_POWERSTATS_QUERY_BY_ID = "DELETE FROM power_stats WHERE id = :id";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
            CREATE_POWER_STATS_QUERY,
            new BeanPropertySqlParameterSource(powerStats),
            UUID.class);
    }

    PowerStats getPowerStats(UUID id) {
        final Map<String, Object> params = Map.of("id", id);
        return namedParameterJdbcTemplate.queryForObject(
                GET_POWERSTATS_QUERY,
                params,
                (rs, rowNum) -> {
                    PowerStats powerStats = new PowerStats(rs.getObject("id", UUID.class),
                            rs.getInt("strength"),
                            rs.getInt("agility"),
                            rs.getInt("dexterity"),
                            rs.getInt("intelligence"),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant());
                    return powerStats;
                    }
        );
    }

    public void update(UUID id, PowerStats powerStats) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue("strength", powerStats.getStrength());
        params.addValue("agility", powerStats.getAgility());
        params.addValue("dexterity", powerStats.getDexterity());
        params.addValue("intelligence", powerStats.getIntelligence());

        namedParameterJdbcTemplate.update(UPDATE_POWERSTATS_QUERY, params);
    }

    public void delete (UUID id) {
        namedParameterJdbcTemplate.update(DELETE_POWERSTATS_QUERY_BY_ID, Map.of("id", id));
    }


}

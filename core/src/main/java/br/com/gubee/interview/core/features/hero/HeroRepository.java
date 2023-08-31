package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HeroRepository {

    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
            " (name, race, power_stats_id)" +
            " VALUES (:name, :race, :powerStatsId) RETURNING id";

    private static final String GET_SINGLE_HERO_ID_QUERY = "SELECT * FROM hero" +
            " WHERE id = :id";

    private static final String GET_ALL_HEROES_QUERY = "SELECT * FROM hero";

    private static final String GET_HEROES_NAME_QUERY = "SELECT * FROM hero" +
            " WHERE name ilike :name";

    private static final String UPDATE_HERO_QUERY = "UPDATE hero" +
            " SET name = :name, race = :race, " +
            " updated_at = NOW()" +
            " WHERE id = :id";

    private static final String DELETE_HERO_BY_ID = "DELETE FROM hero WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
                "race", hero.getRace().name(),
                "powerStatsId", hero.getPowerStatsId());

        return namedParameterJdbcTemplate.queryForObject(
                CREATE_HERO_QUERY,
                params,
                UUID.class);
    }

    public Hero getHeroById(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        return namedParameterJdbcTemplate.queryForObject(
                GET_SINGLE_HERO_ID_QUERY,
                params,
                (rs, rowNum) -> {
                    Hero hero = new Hero(
                            rs.getObject("id", UUID.class),
                            rs.getString("name"),
                            Race.valueOf(rs.getString("race")),
                            rs.getObject("power_stats_id", UUID.class),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant(),
                            rs.getBoolean("enabled")
                            );

                    return hero;
                }
        );
    }

    public List<Hero>  getHeroByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", "%"+name+"%");

        List<Hero> heroes = namedParameterJdbcTemplate.query(
                GET_HEROES_NAME_QUERY,
                params,
                (rs, rowNum) -> {
                    Hero hero = new Hero(
                            rs.getObject("id", UUID.class),
                            rs.getString("name"),
                            Race.valueOf(rs.getString("race")),
                            rs.getObject("power_stats_id", UUID.class),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant(),
                            rs.getBoolean("enabled")
                    );
                    return hero;
                }
        );
        return heroes;
    }

    public void delete(UUID id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcTemplate.update(
                DELETE_HERO_BY_ID,
                params
        );
    }

    public List<Hero> getAll() {
        return namedParameterJdbcTemplate.query(
                GET_ALL_HEROES_QUERY,
                (rs, rowNum) -> {
                    Hero hero = new Hero(
                            rs.getObject("id", UUID.class),
                            rs.getString("name"),
                            Race.valueOf(rs.getString("race")),
                            rs.getObject("power_stats_id", UUID.class),
                            rs.getTimestamp("created_at").toInstant(),
                            rs.getTimestamp("updated_at").toInstant(),
                            rs.getBoolean("enabled")
                    );
                    return hero;
                }
        );
    }

    public void update( Hero hero) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", hero.getId())
                .addValue("name", hero.getName())
                .addValue("race", hero.getRace().name());
        namedParameterJdbcTemplate.update(UPDATE_HERO_QUERY, params);
    }
}

package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import br.com.gubee.interview.model.response.HeroDifferenceResponse;
import br.com.gubee.interview.model.response.HeroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/heroes", produces = APPLICATION_JSON_VALUE)
public class HeroController {

    private final HeroService heroService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@Validated
                                       @RequestBody CreateHeroRequest createHeroRequest) {
        final UUID id = heroService.create(createHeroRequest);
        return created(URI.create(format("/api/v1/heroes/%s", id))).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HeroResponse> getHero(@PathVariable String id) {
        final HeroResponse response = heroService.getHeroById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<HeroResponse>> getHeroByName(@RequestParam @NotBlank String name) {
        final List<HeroResponse> response = heroService.getHeroByName(name);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/deletar")
    public ResponseEntity<Object> delete(@RequestParam String id) {
        heroService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/buscarTodos/")
    public ResponseEntity<List<HeroResponse>> getAll() {
        final List<HeroResponse> response = heroService.getAll();
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/editar", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateHero(@Validated @RequestBody  UpdateHeroRequest updateHeroRequest) {

        heroService.update(updateHeroRequest);
        return ResponseEntity.ok(null);
    }

    @GetMapping(value = "/batalha")
    public ResponseEntity<HeroDifferenceResponse> getHeroById(@RequestParam String heroId1, String heroId2) {
        return ResponseEntity.ok().body(heroService.getDifference(heroId1, heroId2));
    }
}

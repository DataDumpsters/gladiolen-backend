package Project40.gladiolen_backend.controllers;

import Project40.gladiolen_backend.models.Job;
import Project40.gladiolen_backend.models.Sex;
import Project40.gladiolen_backend.models.Size;
import Project40.gladiolen_backend.models.Tshirt;
import Project40.gladiolen_backend.services.TshirtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/tshirt")
@RequiredArgsConstructor
public class TshirtController {

    private final TshirtService tshirtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTshirt(@RequestBody Tshirt tshirt) {
        tshirtService.createTshirt(tshirt);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt getTshirtById(@PathVariable Long id) {
        return tshirtService.getTshirtById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTshirt(@PathVariable Long id, @RequestBody Tshirt tshirt) {
        tshirtService.updateTshirt(id, tshirt);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTshirt(@PathVariable Long id) {
        tshirtService.deleteTshirt(id);
    }

    @GetMapping("/sizes")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSizes() {
        return Stream.of(Size.values())
                .map(Size::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/sexes")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSex() {
        return Stream.of(Sex.values())
                .map(Sex::name)
                .collect(Collectors.toList());
    }

    @GetMapping("/jobs")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getJobs() {
        return Stream.of(Job.values())
                .map(Job::name)
                .collect(Collectors.toList());
    }
}

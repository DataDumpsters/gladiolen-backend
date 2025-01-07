package Project40.gladiolen_backend.controllers;

import Project40.gladiolen_backend.dto.TshirtCountDto;
import Project40.gladiolen_backend.models.*;
import Project40.gladiolen_backend.services.TshirtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/tshirt")
@RequiredArgsConstructor
public class TshirtController {

    private final TshirtService tshirtService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createTshirt(@RequestBody Tshirt tshirt) {
//        tshirtService.createTshirt(tshirt);
//    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new tshirt")
    public ResponseEntity<?> createTshirt(@RequestBody Tshirt tshirt) {
        tshirtService.createTshirt(tshirt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Tshirt getTshirtById(@PathVariable Long id) {
        return tshirtService.getTshirtById(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns all tshirt details")
    public List<Tshirt> allTshirtsRetrievalHandler() {
        return tshirtService.getAllTshirts();
    }

    @GetMapping(value = "/counts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Returns tshirt counts by role, sex, and size")
    public List<TshirtCountDto> getTshirtCountsByRoleSexAndSize() {
        return tshirtService.getTshirtCountsByRoleSexAndSize();
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

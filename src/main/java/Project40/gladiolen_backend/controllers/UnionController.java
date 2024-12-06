package Project40.gladiolen_backend.controllers;

import Project40.gladiolen_backend.models.Union;
import Project40.gladiolen_backend.services.UnionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/union")
@RequiredArgsConstructor
public class UnionController {

    private final UnionService unionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUnion(@RequestBody Union union) {
        unionService.createUnion(union);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Union getUnionById(@PathVariable Long id) {
        return unionService.getUnionById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Union> getAllUnions() {
        return unionService.getAllUnions();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUnion(@PathVariable Long id, @RequestBody Union union) {
        unionService.updateUnion(id, union);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUnion(@PathVariable Long id) {
        unionService.deleteUnion(id);
    }
}

package hse.presentation;


import hse.domain.Animal;
import hse.infrastructure.facade.ZooFacade;
import hse.valueObjects.AnimalParams;
import hse.valueObjects.AnimalStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/animal")
@Tag(name = "Животные", description = "Управление животными в зоопарке")
public class AnimalController {
    private final ZooFacade facade;

    @PostMapping("/create")
    @Operation(
        summary = "Добавить животное в зоопарк",
        description = "Тип животного должен совпадать с типами AnimalType"
    )
    public ResponseEntity<Animal> createAnimal(@Valid @RequestBody AnimalParams request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        try {
            Animal result = facade.addAnimal(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                e.getMessage());
        }
    }

    @DeleteMapping("/delete/{name}")
    @Operation(summary = "Убрать животное из зоопарка",
        description = "Принимает имя животного")
    public ResponseEntity<Void> deleteAnimal(@PathVariable String name) {
        try {
            facade.removeAnimal(name);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{name}")
    @Operation(summary = "Получает животное по имени")
    public ResponseEntity<Animal> getAnimal(@PathVariable String name) {
        try {
            return ResponseEntity.ok(facade.getAnimal(name));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/get/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Возвращает статистику по животным")
    public ResponseEntity<AnimalStatistics> getStatistics() {
        return ResponseEntity.ok(facade.getAnimalsStats());
    }
}

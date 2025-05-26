package hse.presentation;

import hse.domain.Enclosure;
import hse.domain.events.AnimalMovedEvent;
import hse.infrastructure.facade.ZooFacade;
import hse.valueobjects.AnimalMovingParams;
import hse.valueobjects.EnclosureParams;
import hse.valueobjects.EnclosureStatistics;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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

/**
 * Rest controller for enclosures.
 */
@RestController
@RequestMapping("/api/enclosure")
@RequiredArgsConstructor
@Tag(name = "Вольеры", description = "Управление вольерами в зоопарке")
public class EnclosureController {


    private final ZooFacade facade;

    /**
     * Create enclosure method.
     *
     * @param params        Params of enclosure.
     * @param bindingResult Result of binding.
     * @return Enclosure created.
     */
    @PostMapping("/create")
    @Operation(summary = "Создать вольер")
    ResponseEntity<Enclosure> createEnclosure(@Valid @RequestBody EnclosureParams params, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            var result = facade.createEnclosure(params);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Delete enclosure.
     *
     * @param ind           Enclosure index.
     * @param bindingResult Result of binding.
     * @return Void.
     */
    @DeleteMapping("/delete/{ind}")
    @Operation(summary = "Убрать вольер", description = "Для удаления вольера он должен быть пустым")
    ResponseEntity<Void> deleteEnclosure(@Valid @PathVariable Integer ind, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            facade.deleteEnclosure(ind);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Get statistics on enclosures.
     *
     * @return Statistics of all enclosures.
     */
    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Возвращает статистику по всем вольерам")
    ResponseEntity<List<EnclosureStatistics>> getStats() {
        return ResponseEntity.ok(facade.getEnclosureStats());
    }

    /**
     * Get enclosure info.
     *
     * @param ind Enclosure index
     * @return Report
     */
    @GetMapping(value = "/get/{ind}")
    @Operation(summary = "Возвращает информацию по вольеру с номером ind")
    ResponseEntity<String> getEnclosure(@Valid @PathVariable Integer ind) {
        try {
            var res = facade.getEnclosure(ind);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Return all events.
     *
     * @return Report
     */
    @GetMapping(value = "/events")
    @Operation(summary = "Возвращает все ивенты перемещений")
    ResponseEntity<String> getEvents() {
        return ResponseEntity.ok(facade.getMovedEvents());
    }

    /**
     * Transfer animal.
     *
     * @param params        Transfer params
     * @param bindingResult Result of binding
     * @return Event report
     */
    @PostMapping(value = "/transfer")
    @Operation(summary = "Перемещает животное между вольерами")
    ResponseEntity<AnimalMovedEvent> transferAnimal(@Valid @RequestBody AnimalMovingParams params,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            return ResponseEntity.ok(facade.transferAnimal(params.animalName(), params.from(), params.to()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

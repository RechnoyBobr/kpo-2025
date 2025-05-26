package hse.presentation;

import hse.domain.FeedingSchedule;
import hse.infrastructure.facade.ZooFacade;
import hse.valueobjects.FeedingScheduleParams;
import hse.valueobjects.ScheduleUpdateParams;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for feeding schedules.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
@Tag(name = "Расписание кормления", description = "Управление расписанием кормления животных")
public class FeedingSchedulesController {
    private final ZooFacade facade;

    /**
     * Create schedule.
     *
     * @param request       Request
     * @param bindingResult Binding request
     * @return Schedule
     */
    @PostMapping()
    @Operation(
        summary = "Создать расписание кормления",
        description = "Создает новое расписание кормления для животного"
    )
    public ResponseEntity<FeedingSchedule> createSchedule(@Valid @RequestBody FeedingScheduleParams request,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {
            FeedingSchedule result = facade.createFeedingSchedule(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    /**
     * Update schedule.
     *
     * @param animalName    Animal name
     * @param newt          New time params
     * @param bindingResult Result of binding
     * @return Schedule
     */
    @PutMapping(value = "/update/{animalName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Обновить расписание кормления",
        description = "Обновляет время кормления для животного (в формате 12:00)"
    )
    public ResponseEntity<FeedingSchedule> updateSchedule(
        @PathVariable String animalName,
        @Valid @RequestBody ScheduleUpdateParams newt,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                bindingResult.getAllErrors().getFirst().getDefaultMessage());
        }
        try {

            FeedingSchedule result = facade.updateFeedingSchedule(animalName, newt.feedingTime());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Delete animal schedule by name.
     *
     * @param animalName Animal name
     * @return Status
     */
    @DeleteMapping("/delete/{animalName}")
    @Operation(
        summary = "Удалить расписание кормления",
        description = "Удаляет расписание кормления для животного"
    )
    public ResponseEntity<Void> deleteSchedule(@PathVariable String animalName) {
        try {
            facade.deleteFeedingSchedule(animalName);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * Get animal schedule by name.
     *
     * @param animalName Name
     * @return Schedule
     */
    @GetMapping(value = "/get/{animalName}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Получить расписание кормления",
        description = "Возвращает расписание кормления для животного"
    )
    public ResponseEntity<String> getSchedule(@PathVariable String animalName) {
        try {
            return ResponseEntity.ok(facade.getFeedingSchedule(animalName));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Get all schedules.
     *
     * @return Schedules.
     */
    @GetMapping(value = "/get/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
        summary = "Получить все расписания кормления",
        description = "Возвращает список всех расписаний кормления"
    )
    public ResponseEntity<String> getAllSchedules() {
        return ResponseEntity.ok(facade.getAllFeedingSchedules());
    }

    /**
     * Feed animals.
     *
     * @return Report
     */
    @GetMapping(value = "/feed")
    @Operation(summary = "Кормит всех животных в текущее время (до минуты)")
    public ResponseEntity<String> feedAnimals() {
        return ResponseEntity.ok(facade.feedAnimals());
    }

    /**
     * Get all events.
     *
     * @return Report
     */
    @GetMapping(value = "/events")
    @Operation(summary = "Возвращает все ивенты кормления животных")
    public ResponseEntity<String> getFeedingEvents() {
        return ResponseEntity.ok(facade.getFeedingEvents());
    }
}

package hse.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hse.domain.FeedingSchedule;
import hse.infrastructure.facade.ZooFacade;
import hse.valueobjects.FeedingScheduleParams;
import hse.valueobjects.ScheduleUpdateParams;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

@ExtendWith(MockitoExtension.class)
class FeedingSchedulesControllerTest {

    @Mock
    private ZooFacade facade;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private FeedingSchedulesController controller;

    private FeedingScheduleParams scheduleParams;
    private FeedingSchedule schedule;

    @BeforeEach
    void setUp() {
        scheduleParams = new FeedingScheduleParams(
            "TestAnimal",
            LocalTime.of(12, 0),
            true
        );

        schedule = new FeedingSchedule(
            null, // Animal will be set in tests
            LocalTime.of(12, 0),
            true
        );
    }

    @Test
    void createSchedule_ShouldReturnCreatedSchedule() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(facade.createFeedingSchedule(any(FeedingScheduleParams.class))).thenReturn(schedule);

        // Act
        ResponseEntity<FeedingSchedule> response = controller.createSchedule(scheduleParams, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(facade).createFeedingSchedule(any(FeedingScheduleParams.class));
    }



    @Test
    void updateSchedule_ShouldReturnUpdatedSchedule() {
        // Arrange
        LocalTime newTime = LocalTime.of(14, 0);
        when(bindingResult.hasErrors()).thenReturn(false);
        when(facade.updateFeedingSchedule(anyString(), any(LocalTime.class))).thenReturn(schedule);

        // Act
        ResponseEntity<FeedingSchedule> response = controller.updateSchedule(
            "TestAnimal", new ScheduleUpdateParams(newTime), bindingResult
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(facade).updateFeedingSchedule(anyString(), any(LocalTime.class));
    }



    @Test
    void deleteSchedule_ShouldReturnOk() {
        // Act
        ResponseEntity<Void> response = controller.deleteSchedule("TestAnimal");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(facade).deleteFeedingSchedule(anyString());
    }



    @Test
    void getAllSchedules_ShouldReturnAllSchedules() {
        // Arrange
        String expectedResponse = "Schedules: \n\t TestSchedule";
        when(facade.getAllFeedingSchedules()).thenReturn(expectedResponse);

        // Act
        ResponseEntity<String> response = controller.getAllSchedules();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(facade).getAllFeedingSchedules();
    }
} 
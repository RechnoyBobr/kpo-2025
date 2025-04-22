package hse.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.infrastructure.facade.ZooFacade;
import hse.valueobjects.AnimalType;
import hse.valueobjects.EnclosureParams;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@ExtendWith(MockitoExtension.class)
class EnclosureControllerTest {

    @Mock
    private ZooFacade facade;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private EnclosureController controller;

    private EnclosureParams enclosureParams;
    private Enclosure enclosure;
    private Animal animal;

    @BeforeEach
    void setUp() {
        enclosureParams = new EnclosureParams(
           "CARNIVORE",
            20
        );

        enclosure = new Enclosure(
            AnimalType.CARNIVORE,
            20
        );

        animal = new Animal(
            "TestAnimal",
            AnimalType.CARNIVORE,
            "Lion",
            java.time.LocalDate.now(),
            true,
            "Meat",
            true
        );
    }

    @Test
    void createEnclosure_ShouldReturnCreatedEnclosure() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(facade.createEnclosure(any(EnclosureParams.class))).thenReturn(enclosure);

        // Act
        ResponseEntity<Enclosure> response = controller.createEnclosure(enclosureParams, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(facade).createEnclosure(any(EnclosureParams.class));
    }

    @Test
    void createEnclosure_WithValidationErrors_ShouldReturnBadRequest() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("Test", "Test")));

        // Act & Assert
        assertThrows(org.springframework.web.server.ResponseStatusException.class,
            () -> controller.createEnclosure(enclosureParams, bindingResult));
    }

    @Test
    void deleteEnclosure_ShouldReturnOk() {
        // Act
        ResponseEntity<Void> response = controller.deleteEnclosure(0, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(facade).deleteEnclosure(anyInt());
    }

} 
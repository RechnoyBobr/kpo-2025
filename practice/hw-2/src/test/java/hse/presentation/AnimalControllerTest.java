package hse.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hse.domain.Animal;
import hse.infrastructure.facade.ZooFacade;
import hse.valueobjects.AnimalParams;
import hse.valueobjects.AnimalType;
import java.time.LocalDate;
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
class AnimalControllerTest {

    @Mock
    private ZooFacade facade;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AnimalController controller;

    private AnimalParams animalParams;
    private Animal animal;

    @BeforeEach
    void setUp() {
        animalParams = new AnimalParams(
            "TestAnimal",
            "CARNIVORE",
            "Lion",
            LocalDate.now(),
            true,
            "Meat",
            true
        );

        animal = new Animal(
            "TestAnimal",
            AnimalType.CARNIVORE,
            "Lion",
            LocalDate.now(),
            true,
            "Meat",
            true
        );
    }

    @Test
    void createAnimal_ShouldReturnCreatedAnimal() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(facade.addAnimal(any(AnimalParams.class))).thenReturn(animal);

        // Act
        ResponseEntity<Animal> response = controller.createAnimal(animalParams, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(animal.getName(), response.getBody().getName());
        verify(facade).addAnimal(any(AnimalParams.class));
    }

    @Test
    void createAnimal_WithValidationErrors_ShouldReturnBadRequest() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("Test", "bad")));

        // Act & Assert
        assertThrows(org.springframework.web.server.ResponseStatusException.class,
            () -> controller.createAnimal(animalParams, bindingResult));
    }

    @Test
    void deleteAnimal_ShouldReturnOk() {
        // Act
        ResponseEntity<Void> response = controller.deleteAnimal("TestAnimal");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(facade).removeAnimal(anyString());
    }

    @Test
    void getAnimal_ShouldReturnAnimal() {
        // Arrange
        when(facade.getAnimal(anyString())).thenReturn(animal);

        // Act
        ResponseEntity<Animal> response = controller.getAnimal("TestAnimal");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(animal.getName(), response.getBody().getName());
        verify(facade).getAnimal(anyString());
    }

} 
package hse.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.infrastructure.storage.EnclosureStorage;
import hse.valueobjects.AnimalType;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ZooStatisticsServiceTest {

    @InjectMocks
    private ZooStatisticsService service;

    private Animal healthyAnimal;
    private Animal sickAnimal;
    private Enclosure enclosure;

    @BeforeEach
    void setUp() {
        // Clear storage before each test
        EnclosureStorage.getEnclosures().clear();

        // Create test animals
        healthyAnimal = new Animal(
            "HealthyAnimal",
            AnimalType.CARNIVORE,
            "Lion",
            LocalDate.now(),
            true,
            "Meat",
            true
        );

        sickAnimal = new Animal(
            "SickAnimal",
            AnimalType.CARNIVORE,
            "Elephant",
            LocalDate.now(),
            false,
            "Grass",
            true
        );

        // Create test enclosure
        enclosure = new Enclosure(
            AnimalType.CARNIVORE,
            20
        );

        // Add animals to enclosure
        enclosure.addAnimal(healthyAnimal);
        enclosure.addAnimal(sickAnimal);

        // Add enclosure to storage
        EnclosureStorage.addEnclosure(enclosure);
    }

    @Test
    void getAllAnimalsInfo_ShouldReturnAllAnimalsInfo() {
        // Act
        List<String> animalInfos = service.getAllAnimalsInfo();

        // Assert
        assertEquals(2, animalInfos.size());
        assertTrue(animalInfos.stream().anyMatch(info -> info.contains("HealthyAnimal")));
        assertTrue(animalInfos.stream().anyMatch(info -> info.contains("SickAnimal")));
    }

    @Test
    void getAnimalsByType_ShouldReturnCorrectTypeCounts() {
        // Act
        Map<AnimalType, Integer> typeStats = service.getAnimalsByType();

        // Assert
        assertEquals(1, typeStats.size());
        assertEquals(2, typeStats.get(AnimalType.CARNIVORE));
        assertNull(typeStats.get(AnimalType.HERBIVORE));
    }

    @Test
    void getSickAnimalsInfo_ShouldReturnOnlySickAnimals() {
        // Act
        List<String> sickAnimals = service.getSickAnimalsInfo();

        // Assert
        assertEquals(0, sickAnimals.size());
    }

    @Test
    void getAnimalsBySpecies_ShouldReturnCorrectSpeciesCounts() {
        // Act
        Map<String, Integer> speciesStats = service.getAnimalsBySpecies();

        // Assert
        assertEquals(2, speciesStats.size());
        assertEquals(1, speciesStats.get("Lion"));
        assertEquals(1, speciesStats.get("Elephant"));
    }

    @Test
    void getTotalAnimalsCount_ShouldReturnCorrectCount() {
        // Act
        int totalCount = service.getTotalAnimalsCount();

        // Assert
        assertEquals(2, totalCount);
    }

} 
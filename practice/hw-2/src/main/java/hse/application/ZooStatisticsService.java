package hse.application;

import hse.domain.Animal;
import hse.domain.Enclosure;
import hse.infrastructure.storage.EnclosureStorage;
import hse.valueobjects.AnimalType;
import hse.valueobjects.EnclosureStatistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * Service for collecting and providing zoo statistics.
 */
@Service
public class ZooStatisticsService {

    /**
     * Gets detailed information about all animals in the zoo.
     *
     * @return List of string representations of all animals
     */
    public List<String> getAllAnimalsInfo() {
        List<String> animalInfos = new ArrayList<>();
        for (Enclosure enclosure : EnclosureStorage.getEnclosures()) {
            for (Animal animal : enclosure.getAnimals()) {
                animalInfos.add(animal.toString());
            }
        }
        return animalInfos;
    }

    /**
     * Gets statistics about animals grouped by their type.
     *
     * @return Map of animal type to count of animals of that type
     */
    public Map<AnimalType, Integer> getAnimalsByType() {
        Map<AnimalType, Integer> typeStats = new HashMap<>();
        for (Enclosure enclosure : EnclosureStorage.getEnclosures()) {
            for (Animal animal : enclosure.getAnimals()) {
                typeStats.merge(animal.getType(), 1, Integer::sum);
            }
        }
        return typeStats;
    }

    /**
     * Gets a list of all sick animals that need attention.
     *
     * @return List of sick animals' information
     */
    public List<String> getSickAnimalsInfo() {
        List<String> sickAnimals = new ArrayList<>();
        for (Enclosure enclosure : EnclosureStorage.getEnclosures()) {
            for (Animal animal : enclosure.getAnimals()) {
                if (!animal.getIsHealthy()) {
                    sickAnimals.add(animal.toString());
                }
            }
        }
        return sickAnimals;
    }

    /**
     * Gets statistics about animals grouped by their species.
     *
     * @return Map of species to count of animals of that species
     */
    public Map<String, Integer> getAnimalsBySpecies() {
        Map<String, Integer> speciesStats = new HashMap<>();
        for (Enclosure enclosure : EnclosureStorage.getEnclosures()) {
            for (Animal animal : enclosure.getAnimals()) {
                speciesStats.merge(animal.getSpecie(), 1, Integer::sum);
            }
        }
        return speciesStats;
    }


    /**
     * Gets the total number of animals in the zoo.
     *
     * @return Total number of animals
     */
    public int getTotalAnimalsCount() {
        return EnclosureStorage.getEnclosures().stream()
            .mapToInt(enclosure -> enclosure.getAnimals().size())
            .sum();
    }

    /**
     * Gets statistics for all enclosures in the zoo.
     *
     * @return List of enclosure statistics
     */
    public List<EnclosureStatistics> getEnclosuresStatistics() {
        List<EnclosureStatistics> statistics = new ArrayList<>();
        for (int i = 0; i < EnclosureStorage.getEnclosures().size(); i++) {
            Enclosure enclosure = EnclosureStorage.getEnclosures().get(i);
            statistics.add(new EnclosureStatistics(
                i,
                enclosure.getType(),
                enclosure.getCurrentAnimals(),
                enclosure.getMaxAnimals()
            ));
        }
        return statistics;
    }
}

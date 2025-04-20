package hse.infrastructure.facade;

import hse.application.AnimalTransferService;
import hse.application.FeedingOrganizationService;
import hse.application.ZooStatisticsService;
import hse.domain.Animal;
import hse.infrastructure.storage.EnclosureStorage;
import hse.valueObjects.AnimalParams;
import hse.valueObjects.AnimalType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Facade for zoo management.
 */
@RequiredArgsConstructor
@Service
public class ZooFacade {
    private final AnimalTransferService transferService;
    private final FeedingOrganizationService feedingService;
    private final ZooStatisticsService statisticsService;

    public Animal addAnimal(final AnimalParams params) {
        AnimalType type = AnimalType.fromString(params.type());
        Animal animal = new Animal(params.name(), type, params.specie(), params.birthDate(),
            params.isMale(), params.favouriteFood(), params.isHealthy());
        int ind = EnclosureStorage.getCompatibleEnclosureInd(type);
        transferService.addAnimal(animal, ind);
        return animal;
    }

    public void removeAnimal(final String name) {
        transferService.removeAnimal(name);
    }

    public Animal getAnimal(final String name) {
        return EnclosureStorage.getAnimalFromName(name);
    }

    public void transferAnimal(final String name, final int from, final int to) {
        transferService.transfer(name, from, to);
    }
}

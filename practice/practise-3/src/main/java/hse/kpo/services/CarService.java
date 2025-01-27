package studying;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarService implements ICarProvider {

    private final List<Car> cars = new ArrayList<>();

    private int carNumberCounter = 0;

    @Override
    public Car takeCar(Customer customer) {
        var filteredCars = cars.stream().filter(car -> car.isCompatible(customer)).toList();
        if (filteredCars.isEmpty()) {
            return null;
        }
        cars.remove(filteredCars.getFirst());
        return filteredCars.getFirst();
    }

    public <TParams> void addCar(ICarFactory<TParams> carFactory, TParams carParams) {
        // создаем автомобиль из переданной фабрики
        var car = carFactory.createCar(
                carParams, // передаем параметры
                ++carNumberCounter // передаем номер - номер будет начинаться с 1
        );

        cars.add(car); // добавляем автомобиль
    }
}

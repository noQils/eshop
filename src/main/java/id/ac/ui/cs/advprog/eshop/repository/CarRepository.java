package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;

@Repository
public class CarRepository extends AbstractRepository<Car> {
    @Override
    protected String getId(Car car) {
        return car.getId();
    }

    @Override
    protected void setId(Car car, String id) {
        car.setId(id);
    }

    @Override
    protected void edit(Car existingCar, Car updatedCar) {
        existingCar.setName(updatedCar.getName());
        existingCar.setCarColor(updatedCar.getCarColor());
        existingCar.setQuantity(updatedCar.getQuantity());
    }
}

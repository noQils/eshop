package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    CarServiceImpl carServiceImpl;
    @BeforeEach
    void setUp() {
        // This method is kept for maintainability, could be useful for future needs
    }

    @Test
    void testCreateCar() {
        // create a car
        Car car = new Car();
        car.setId("eb558e9f-1c59-460e-8860-71af6af63b46");
        car.setName("Eb558e9f");
        car.setQuantity(100);

        // make sure findAll() returns a non-null iterator
        when(carRepository.create(car)).thenReturn(car);

        Car result = carServiceImpl.create(car);

        // verify car exists
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63b46", result.getId());
        assertEquals("Eb558e9f", result.getName());
        assertEquals(100, result.getQuantity());
    }

    @Test
    void testFindAllCars() {
        // create a car
        Car car1 = new Car();
        car1.setId("eb558e9f-1c59-460e-8860-71af6af63b47");
        car1.setName("Subaru BRZ");
        car1.setQuantity(2);

        List<Car> carList = List.of(car1);

        // make sure findAll() returns a non-null iterator
        when(carRepository.findAll()).thenReturn(carList);

        // call findAll function
        List<Car> result = carServiceImpl.findAll();

        // verify created car exist
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63b47", result.getFirst().getId());
        assertEquals("Subaru BRZ", result.getFirst().getName());
        assertEquals(2, result.getFirst().getQuantity());
    }

    @Test
    void testFindByIdCarExists() {
        // create a car
        Car car = new Car();
        car.setId("eb558e9f-1c59-460e-8860-71af6af63g40");
        car.setName("Mercedes-AMG GT");
        car.setQuantity(9);

        // when findById(car.getId()) is called, return car
        when(carRepository.findById(car.getId())).thenReturn(car);

        // call the method
        Car result = carServiceImpl.findById(car.getId());

        // verify car exists
        assertNotNull(result);
        assertEquals("eb558e9f-1c59-460e-8860-71af6af63g40", result.getId());
        assertEquals("Mercedes-AMG GT", result.getName());
        assertEquals(9, result.getQuantity());
    }

    @Test
    void testFindByIdCarNotExists() {
        // when findById("999") is called, return null
        when(carRepository.findById("999")).thenReturn(null);

        // call the method
        Car result = carServiceImpl.findById("999");

        // verify car does not exist
        assertNull(result);
    }

    @Test
    void testEditCarExists() {
        String carId = "eb558e9f-1c59-460e-8860-71af6af63g45";
        Car existingCar = new Car();
        existingCar.setId(carId);
        existingCar.setName("Toyota Gazoo");
        existingCar.setQuantity(10);
        Car updatedCar = new Car();
        updatedCar.setId(carId);
        updatedCar.setName("Toyota Supra MK4");
        updatedCar.setQuantity(5);

        // edit should return the updated car
        when(carRepository.edit(carId, updatedCar)).thenReturn(updatedCar);

        // call the method
        Car result = carServiceImpl.edit(carId, updatedCar);

        // verify the car is updated
        assertNotNull(result);
        assertEquals(updatedCar.getId(), result.getId());
        assertEquals(updatedCar.getName(), result.getName());
        assertEquals(updatedCar.getQuantity(), result.getQuantity());
    }

    @Test
    void testEditCarNotFound() {
        String carId = "eb558e9f-1c59-460e-8860-71af6af63k82";

        // create a car
        Car updatedCar = new Car();
        updatedCar.setId(carId);
        updatedCar.setName("Audi R8");
        updatedCar.setQuantity(5);

        // call the method
        Car result = carServiceImpl.edit(carId, updatedCar);

        // verify method returns null (car does not exist)
        assertNull(result);
    }

    @Test
    void testDeleteCarExists() {
        String carId = "eb558e9f-1c59-460e-8860-71af6af63g45";

        // create a car
        Car deletedCar = new Car();
        deletedCar.setId(carId);
        deletedCar.setName("Hennessey Venom F5");
        deletedCar.setQuantity(10);

        // delete should return the deleted car
        when(carRepository.delete(carId)).thenReturn(deletedCar);

        // call the method
        Car result = carServiceImpl.delete(carId);

        // verify car is deleted
        assertNotNull(result);
        assertEquals(deletedCar.getId(), result.getId());
        assertEquals(deletedCar.getName(), result.getName());
        assertEquals(deletedCar.getQuantity(), result.getQuantity());
    }

    @Test
    void testDeleteCarNotExist() {
        Car result = carServiceImpl.delete("testing-car-id");

        // verify car does not exist
        assertNull(result);
    }
}

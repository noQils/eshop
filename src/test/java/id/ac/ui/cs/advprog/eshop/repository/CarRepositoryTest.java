package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;
    @BeforeEach
    void setUp() {
        // This method is kept for maintainability, could be useful for future needs
    }
    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setId("eb558e9f-1c59-460e-8860-71af6af63b46");
        car.setName("Nissan GTR R35");
        car.setQuantity(100);
        carRepository.create(car);

        List<Car> carList = carRepository.findAll();
        assertTrue(carList.contains(car));
        Car savedCar = carList.getFirst();
        assertEquals(car.getId(), savedCar.getId());
        assertEquals(car.getName(), savedCar.getName());
        assertEquals(car.getQuantity(), savedCar.getQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Car> carList = carRepository.findAll();
        assertTrue(carList.isEmpty());
    }
    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setId("eb558e9f-1c39-460e-8860-71af6af63b46");
        car1.setName("BMW M5");
        car1.setQuantity(100);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        car2.setName("Prosche 911 Carrera");
        car2.setQuantity(50);
        carRepository.create(car2);

        List<Car> carList = carRepository.findAll();
        assertFalse(carList.isEmpty());
        Car savedCar = carList.removeFirst();

        assertEquals(car1.getId(), savedCar.getId());
        savedCar = carList.removeFirst();
        assertEquals(car2.getId(), savedCar.getId());
        assertTrue(carList.isEmpty());
    }

    @Test
    void testEditCar() {
        // create initial car
        Car car = new Car();
        car.setName("Nissan GTR R34");
        car.setQuantity(100);
        carRepository.create(car);
        String carId = car.getId();

        // create updated car with same ID
        Car updatedCar = new Car();
        updatedCar.setName("Mazda RX-7");
        updatedCar.setQuantity(200);

        // perform update
        Car result = carRepository.edit(carId, updatedCar);

        // verify update
        assertNotNull(result);
        assertEquals(updatedCar.getName(), result.getName());
        assertEquals(updatedCar.getQuantity(), result.getQuantity());

        // verify through findAll
        List<Car> carList = carRepository.findAll();
        assertFalse(carList.isEmpty());
        Car savedCar = carList.getFirst();
        assertEquals(updatedCar.getName(), savedCar.getName());
        assertEquals(updatedCar.getQuantity(), savedCar.getQuantity());
    }

    @Test
    void testEmptyCar() {
        // create a car
        Car updatedCar = new Car();
        updatedCar.setName("Updated Car");
        updatedCar.setQuantity(100);

        // verify car does not exist
        Car result = carRepository.edit("hello-world", updatedCar);
        assertNull(result);
    }

    @Test
    void testDeleteCar() {
        // create a car
        Car car = new Car();
        car.setName("Koegniggseg Jesko");
        car.setQuantity(100);
        carRepository.create(car);

        // verify car exists
        List<Car> carList = carRepository.findAll();
        assertTrue(carList.contains(car));

        // delete the car
        Car deletedCar = carRepository.delete(car.getId());
        assertEquals(car.getId(), deletedCar.getId());

        // verify car no longer exists
        carList = carRepository.findAll();
        assertFalse(carList.contains(car));
    }

    @Test
    void testDeleteNonExistentCar() {
        // delete non-existent car
        Car deletedCar = carRepository.delete("hello-world");

        // verify no cars deleted
        assertNull(deletedCar);

        // verify no cars exist
        List<Car> carList = carRepository.findAll();
        assertTrue(carList.isEmpty());
    }

    @Test
    void testFindCarById() {
        // create a car
        Car car = new Car();
        car.setName("Subaru Forester");
        car.setQuantity(100);

        // add car to database
        carRepository.create(car);

        // verify car exists in database
        Car dummyCar = carRepository.findById(car.getId());
        assertEquals(car.getId(), dummyCar.getId());
        assertEquals(car.getName(), dummyCar.getName());
        assertEquals(car.getQuantity(), dummyCar.getQuantity());
    }

    @Test
    void testFindNonExistenceCarById() {
        // verify function returns null given non existent id
        Car car = carRepository.findById("non-existent-id");
        assertNull(car);
    }
}
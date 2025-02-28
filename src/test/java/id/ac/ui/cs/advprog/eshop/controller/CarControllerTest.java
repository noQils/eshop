package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.ObjectGetService;
import id.ac.ui.cs.advprog.eshop.service.ObjectPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {
    @Mock
    private Model model;
    @Mock
    private ObjectGetService getService;
    @Mock
    private ObjectPostService postService;
    @InjectMocks
    CarController carController;
    @BeforeEach
    void setUp() {
        // This method is kept for maintainability, could be useful for future needs
    }

    @Test
    void testCreateCarPage() {
        String viewName = carController.createCarPage(model);

        // verify expected view name
        assertEquals("CreateCar", viewName);
    }

    @Test
    void testCreateCarPost() {
        // create a car
        Car car = new Car();
        car.setId("eb558e9f-1c59-460e-8860-71af6af63j88");
        car.setName("Aspark Owl");
        car.setQuantity(10);

        // call the method
        String result = carController.createCarPost(car);

        // verify expected URL
        assertEquals("redirect:/car/listCar", result);
    }

    @Test
    void testCarListPage() {
        // create cars
        Car car1 = new Car();
        car1.setId("eb558e9f-1c59-460e-8860-71af6af63k23");
        car1.setName("Koenigsegg Regera");
        car1.setQuantity(5);
        Car car2 = new Car();
        car2.setId("eb558e9f-1c59-460e-8860-71af6af63x98");
        car2.setName("Aston Martin Valkyrie");
        car2.setQuantity(10);

        // call the method
        String result = carController.carListPage(model);

        // verify expected view name
        assertEquals("CarList", result);
    }

    @Test
    void testEditCarPage() {
        // create a car
        Car car = new Car();
        car.setId("eb558e9f-1c59-460e-8860-71af6af63h88");
        car.setName("Lamborghini Huracan");
        car.setQuantity(5);

        // call the method
        String result = carController.editCarPage(car.getId(), model);

        // verify expected view name
        assertEquals("EditCar", result);
    }

    @Test
    void testEditCarPost() {
        // create cars
        String carId = "eb558e9f-1c59-460e-8860-71af6af63gg3";
        Car existingCar = new Car();
        existingCar.setId(carId);
        existingCar.setName("Lamborghini Aventador");
        existingCar.setQuantity(10);
        Car updatedCar = new Car();
        updatedCar.setId(carId);
        updatedCar.setName("Ferrari F40");
        updatedCar.setQuantity(5);

        // call method
        String result = carController.editCarPost(existingCar.getId(), updatedCar);

        // verify expected URL
        assertEquals("redirect:/car/listCar", result);
    }

    @Test
    void testDeleteCar() {
        String result = carController.deleteCar("eb558e9f-1c59-460e-8860-71af6af638i2");

        // verify expected URL
        assertEquals("redirect:/car/listCar", result);
    }
}

package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.ObjectPostService;
import id.ac.ui.cs.advprog.eshop.service.ObjectGetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/car")
public class CarController extends AbstractController<Car> {

    public CarController(ObjectGetService<Car> getService, ObjectPostService<Car> postService) {
        super(getService,
                postService,
                "CreateCar",
                "CarList",
                "EditCar",
                "car",
                "cars",
                "redirect:/car/listCar");
    }

    @Override
    protected Car createNewInstance() {
        return new Car();
    }

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        return createPage(model);
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car) {
        return createPost(car);
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        return listPage(model);
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        return editPage(carId, model);
    }

    @PostMapping("/editCar/{carId}")
    public String editCarPost(@PathVariable String carId, @ModelAttribute Car car) {
        return editPost(carId, car);
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        return delete(carId);
    }


}

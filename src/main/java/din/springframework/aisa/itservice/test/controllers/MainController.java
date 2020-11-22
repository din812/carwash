package din.springframework.aisa.itservice.test.controllers;

import din.springframework.aisa.itservice.test.model.CarInQueue;
import din.springframework.aisa.itservice.test.repositories.CarInQueueService;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    private final CarInQueueService carInQueueService;

    public MainController(CarInQueueService carInQueueService) {
        this.carInQueueService = carInQueueService;
    }

    @GetMapping("/queue/get/{car_plate}")
    public CarInQueue queuePlace(@PathVariable String car_plate) {
        return carInQueueService.getByCarPlate(car_plate);
    }

    @PostMapping("/queue/post/")
    public CarInQueue addCarInQueue(@RequestBody CarInQueue carInQueue) {
        return carInQueueService.save(carInQueue);
    }
}

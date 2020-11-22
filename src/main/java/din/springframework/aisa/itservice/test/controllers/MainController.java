package din.springframework.aisa.itservice.test.controllers;

import din.springframework.aisa.itservice.test.model.CarInQueue;
import din.springframework.aisa.itservice.test.model.CarWash;
import din.springframework.aisa.itservice.test.model.CarWashingServiceType;
import din.springframework.aisa.itservice.test.service.CarInQueueService;
import din.springframework.aisa.itservice.test.service.CarWashService;
import din.springframework.aisa.itservice.test.service.CarWashingServiceTypeService;
import io.swagger.v3.oas.annotations.Operation;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    private final CarInQueueService carInQueueService;
    private final CarWashingServiceTypeService carWashingServiceTypeService;
    private final CarWashService carWashService;

    public MainController(CarInQueueService carInQueueService, CarWashingServiceTypeService carWashingServiceTypeService, CarWashService carWashService) {
        this.carInQueueService = carInQueueService;
        this.carWashingServiceTypeService = carWashingServiceTypeService;
        this.carWashService = carWashService;
    }

    @Operation(summary = "Returns queue number and expected queue time for specified car plate string.")
    @GetMapping(value = "/queue/get/{car_plate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> queuePlace(@PathVariable String car_plate) {
        JSONObject json = new JSONObject();
        CarInQueue carInQueue = carInQueueService.getByCarPlate(car_plate);

        if (carInQueue == null) {
            json.put("Car in queue", false); //Not sure if this needed
            json.put("Queue number", 0);
            json.put("Queue time expected to end on", 0);
            return ResponseEntity.status(HttpStatus.OK).body(json.toString());
        }

        LocalDateTime expectedQueueTime = carInQueueService.timeUntilQueueEnd(carInQueue);

        json.put("Car in queue", true);
        json.put("Queue number", carInQueue.getPlaceInQueue());
        json.put("Queue time expected to end on", expectedQueueTime.toString());
        return ResponseEntity.status(HttpStatus.OK).body(json.toString());
    }

    @Operation(summary = "Only returns time until queue.")
    @GetMapping(value = "/queue/get/time/{car_plate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> queueTime(@PathVariable String car_plate) {
        JSONObject json = new JSONObject();
        CarInQueue carInQueue = carInQueueService.getByCarPlate(car_plate);

        if (carInQueue == null) {
            //Keeping key the same for error handling (?), maybe switch 0 to -1
            json.put("Queue time expected to end on", 0);
            return ResponseEntity.status(HttpStatus.OK).body(json.toString());
        }

        LocalDateTime expectedQueueTime = carInQueueService.timeUntilQueueEnd(carInQueue);

        json.put("Queue time expected to end on", expectedQueueTime.toString());
        return ResponseEntity.status(HttpStatus.OK).body(json.toString());
    }

    @Operation(summary = "Adds new car in car wash queue.")
    @PostMapping(value = "/queue/post/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CarInQueue addCarInQueue(@RequestBody CarInQueue carInQueue) {
        //refactor into separate method/class, maybe create command pattern.
        // edit: actually, had no time to switch to command pattern or DTO, already wasted ~10 hours rewriting model and
        // thinking how to realise model (requirements don't state anything about it)

        CarInQueue addCarInQueue = new CarInQueue();

        addCarInQueue.setCarPlate(carInQueue.getCarPlate());

        Long carWashId = carInQueue.getCarWash().getId();

        if (carWashId > 0 && carWashId < 5) {
            addCarInQueue.setCarWash(carWashService.findById(carWashId).get());
        } else {
            CarWash freeCarWash = carWashService.findFirstByOccupiedFalse();
            if (freeCarWash != null) {
                addCarInQueue.setCarWash(freeCarWash);
            } else {
                addCarInQueue.setCarWash(carWashService.findById(
                        ThreadLocalRandom.current().nextLong(1, 5)).get()
                );
            }
        }

        List<CarWashingServiceType> orderedServices = new ArrayList<>();
        carInQueue.getOrderedServices().stream()
                .map(CarWashingServiceType::getId)
                .forEach(aLong -> orderedServices.
                        add(carWashingServiceTypeService
                                .findById(aLong)
                                .orElse(null)));

        long washTime = orderedServices.stream()
                .mapToLong(CarWashingServiceType::getRequiredTime)
                .sum();
        addCarInQueue.setWashTime(washTime);

        addCarInQueue.setOrderedServices(orderedServices);
        return carInQueueService.save(addCarInQueue);
    }
}

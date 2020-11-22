package din.springframework.aisa.itservice.test.bootstrap;

import din.springframework.aisa.itservice.test.model.CarInQueue;
import din.springframework.aisa.itservice.test.model.CarWashingServiceType;
import din.springframework.aisa.itservice.test.service.CarInQueueService;
import din.springframework.aisa.itservice.test.service.CarWashService;
import din.springframework.aisa.itservice.test.service.CarWashingServiceTypeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;


/**
 * Keeping it as is, used for testing swagger.
 */
@Transactional
@Component
public class BootStrap implements CommandLineRunner {

    private final CarWashService carWashService;
    private final CarWashingServiceTypeService carWashingServiceTypeService;
    private final CarInQueueService carInQueueService;

    public BootStrap(CarWashService carWashService,
                     CarWashingServiceTypeService carWashingServiceTypeService,
                     CarInQueueService carInQueueService) {
        this.carWashService = carWashService;
        this.carWashingServiceTypeService = carWashingServiceTypeService;
        this.carInQueueService = carInQueueService;
    }

    @Override
    public void run(String... args) throws Exception {
        CarInQueue carQueue = new CarInQueue();
        carQueue.setCarPlate("a245ra");
        carQueue.getOrderedServices().add(carWashingServiceTypeService.findById(1L).get());
        carQueue.setWashTime(carQueue.getOrderedServices().stream()
                .mapToLong(CarWashingServiceType::getRequiredTime).sum());
        carQueue.setCarWash(carWashService.findById(1L).get());
        carInQueueService.save(carQueue);


        CarInQueue carQueue1 = new CarInQueue();
        carQueue1.setCarPlate("a335ra");
        carQueue1.getOrderedServices()
                .addAll((Collection<? extends CarWashingServiceType>) carWashingServiceTypeService.findAll());
        carQueue1.setWashTime(carQueue1.getOrderedServices().stream()
                .mapToLong(CarWashingServiceType::getRequiredTime).sum());
        carQueue1.setCarWash(carWashService.findById(1L).get());
        carInQueueService.save(carQueue1);


        CarInQueue carQueue2 = new CarInQueue();
        carQueue2.setCarPlate("g345ra");
        carQueue2.getOrderedServices().add(carWashingServiceTypeService.findById(1L).get());
        carQueue2.getOrderedServices().add(carWashingServiceTypeService.findById(5L).get());
        carQueue2.setWashTime(carQueue2.getOrderedServices().stream()
                .mapToLong(CarWashingServiceType::getRequiredTime).sum());
        carQueue2.setCarWash(carWashService.findById(3L).get());

        carInQueueService.save(carQueue2);

        /*CarWash carWash = carWashService.findFirstByOccupiedFalse();
        carWash.setOccupied(true);
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(carQueue.getOrderedServices()
                .stream()
                .mapToLong(CarWashingServiceType::getRequiredTime).sum());
        carWash.setOccupiedUntil(ldt);

        carWashService.save(carWash);*/

        CarInQueue car = carInQueueService.getByCarPlate("a335ra");

        /*for (CarWashingServiceType type : car.getOrderedServices()) {
            System.out.println(type.toString());
        }*/
    }
}

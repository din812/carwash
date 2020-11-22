package din.springframework.aisa.itservice.test.service;


import din.springframework.aisa.itservice.test.model.CarInQueue;
import din.springframework.aisa.itservice.test.model.CarWash;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Set;

/**
 * Class is used for moving and cleaning car wash queue. Task didn't state anything in particular, like moving queue by
 * command, so i made it automatic with @Schedule. Couldn't figure out or find better solution. :(
 */
@Service
public class MainService {

    private final CarWashService carWashService;
    private final CarInQueueService carInQueueService;

    public MainService(CarWashService carWashService, CarInQueueService carInQueueService) {
        this.carWashService = carWashService;
        this.carInQueueService = carInQueueService;
    }

    @Scheduled(fixedDelay = 1000L)
    private synchronized void scheduledCarWashQueueMover() {
        Set<CarWash> carWashSetOfCompletedWashes = carWashService.findAllByOccupiedUntilBefore(LocalDateTime.now());
        Set<CarWash> emptyCarWashes = carWashService.findAllByOccupiedFalse();

        if (!emptyCarWashes.isEmpty()) {
            for (CarWash carWash : emptyCarWashes) {
                nextCarFromQueue(carWash);
            }
        }

        if (!carWashSetOfCompletedWashes.isEmpty()) {
            for (CarWash carWash : carWashSetOfCompletedWashes) {
                nextCarFromQueue(carWash);
            }
        }
    }

    private synchronized void nextCarFromQueue(CarWash carWash) {
        CarInQueue firstCarInQueue = carInQueueService.findFirstByCarWash_IdOrderByPlaceInQueueAsc(carWash.getId());

        if (firstCarInQueue != null) {
            carWash.setOccupied(true);
            carWash.setOccupiedUntil(LocalDateTime.now().plusMinutes(firstCarInQueue.getWashTime()));
            carWash.setCarPlate(firstCarInQueue.getCarPlate());
            carInQueueService.deleteById(firstCarInQueue.getId());

            LinkedList<CarInQueue> carsInQueueByCarWashId = carInQueueService
                                                            .findAllByCarWashIdOrderByPlaceInQueueAsc(carWash.getId());

            carsInQueueByCarWashId.forEach(carInQueue -> carInQueue.setPlaceInQueue(carInQueue.getPlaceInQueue() - 1L));
            carInQueueService.saveAll(carsInQueueByCarWashId);
        } else {
            carWash.setOccupied(false);
            carWash.setOccupiedUntil(null);
            carWash.setCarPlate(null);
        }
        carWashService.save(carWash);
    }
}

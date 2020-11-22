package din.springframework.aisa.itservice.test.service;


import din.springframework.aisa.itservice.test.model.CarInQueue;
import din.springframework.aisa.itservice.test.model.CarWash;
import din.springframework.aisa.itservice.test.model.CarWashingServiceType;
import din.springframework.aisa.itservice.test.repositories.CarInQueueService;
import din.springframework.aisa.itservice.test.repositories.CarWashService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Testing stuff, refactor/remove after.
 */
@Slf4j
@Service
public class MainService {

    private final CarWashService carWashService;
    private final CarInQueueService carInQueueService;

    public MainService(CarWashService carWashService, CarInQueueService carInQueueService) {
        this.carWashService = carWashService;
        this.carInQueueService = carInQueueService;
    }

    @Scheduled(fixedDelay = 5000L)
    private synchronized void scheduledCarWashQueueMover() {
        System.out.println("//////////////////////////////////////////////////////////////////////////");
        System.out.println("scheduledCarWashQueueMover started, timestamp: " + LocalDateTime.now());

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

        System.out.println("scheduledCarWashQueueMover ends, timestamp: " + LocalDateTime.now());
        System.out.println("//////////////////////////////////////////////////////////////////////////");
    }

    private synchronized void nextCarFromQueue(CarWash carWash) {
        System.out.println(carWash.getId());
        CarInQueue firstCarInQueue = carInQueueService.findFirstByCarWash_IdOrderByPlaceInQueueAsc(carWash.getId());
        System.out.println(firstCarInQueue);

        if (firstCarInQueue != null) {
            carWash.setOccupied(true);
            carWash.setOccupiedUntil(LocalDateTime.now().plusMinutes(firstCarInQueue.getWashTime()));
            carWash.setCarPlate(firstCarInQueue.getCarPlate());
            carWashService.save(carWash);
            carInQueueService.deleteById(firstCarInQueue.getId());
        } else {
            carWash.setOccupied(false);
            carWash.setOccupiedUntil(null);
            carWash.setCarPlate(null);
        }
    }
}

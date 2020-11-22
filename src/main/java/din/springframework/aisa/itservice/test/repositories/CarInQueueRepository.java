package din.springframework.aisa.itservice.test.repositories;

import din.springframework.aisa.itservice.test.model.CarInQueue;
import org.springframework.data.repository.CrudRepository;

import java.util.LinkedList;
import java.util.UUID;


public interface CarInQueueRepository extends CrudRepository<CarInQueue, UUID> {

    CarInQueue getByCarPlate(String plate);

    LinkedList<CarInQueue> findAllByCarWashIdOrderByPlaceInQueueAsc(Long carWashId);

    CarInQueue findFirstByCarWash_IdOrderByPlaceInQueueAsc(Long carWashId);

    void deleteAllByCarWash_Id(Long carWashId);
}

package din.springframework.aisa.itservice.test.repositories;

import din.springframework.aisa.itservice.test.model.CarInQueue;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarInQueueService implements CarInQueueRepository{

    private final CarInQueueRepository carInQueueRepository;

    public CarInQueueService(CarInQueueRepository carInQueueRepository) {
        this.carInQueueRepository = carInQueueRepository;
    }

    @Override
    public <S extends CarInQueue> S save(S entity) {
        LinkedList<CarInQueue> carsInQueue = carInQueueRepository
                                                .findAllByCarWashIdOrderByPlaceInQueueAsc(entity.getCarWash().getId());

        if (carsInQueue.isEmpty()) {
            entity.setPlaceInQueue(1L);
        } else {
            entity.setPlaceInQueue(carsInQueue.getLast().getPlaceInQueue() + 1L);
        }

        return carInQueueRepository.save(entity);
    }

    @Override
    public <S extends CarInQueue> Iterable<S> saveAll(Iterable<S> entities) {
        return carInQueueRepository.saveAll(entities);
    }

    @Override
    public Optional<CarInQueue> findById(UUID id) {
        return carInQueueRepository.findById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return carInQueueRepository.existsById(id);
    }

    @Override
    public Iterable<CarInQueue> findAll() {
        return carInQueueRepository.findAll();
    }

    @Override
    public Iterable<CarInQueue> findAllById(Iterable<UUID> longs) {
        return carInQueueRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return carInQueueRepository.count();
    }

    @Override
    public void deleteById(UUID id) {
        Optional<CarInQueue> carInQueue = carInQueueRepository.findById(id);

        if (carInQueue.isPresent()) {
            CarInQueue car = carInQueue.get();
            if (car.getPlaceInQueue() == 1) {
                carInQueueRepository.deleteById(id);
            } else if (car.getPlaceInQueue() != 1) {
                LinkedList<CarInQueue> carsInQueue = carInQueueRepository
                                                    .findAllByCarWashIdOrderByPlaceInQueueAsc(car.getCarWash().getId());
                carsInQueue.stream()
                        .skip(car.getPlaceInQueue())
                        .forEachOrdered(carInQueue1 -> carInQueue1.setPlaceInQueue(carInQueue1.getPlaceInQueue() - 1L));
                carInQueueRepository.deleteById(id);
            }
        }
    }

    @Override
    public void delete(CarInQueue entity) {
        carInQueueRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends CarInQueue> entities) {
        carInQueueRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        carInQueueRepository.deleteAll();
    }

    @Override
    public CarInQueue getByCarPlate(String plate) {
        return carInQueueRepository.getByCarPlate(plate);
    }

    @Override
    public LinkedList<CarInQueue> findAllByCarWashIdOrderByPlaceInQueueAsc(Long carWashId) {
        return carInQueueRepository.findAllByCarWashIdOrderByPlaceInQueueAsc(carWashId);
    }

    @Override
    public CarInQueue findFirstByCarWash_IdOrderByPlaceInQueueAsc(Long carWashId) {
        return carInQueueRepository.findFirstByCarWash_IdOrderByPlaceInQueueAsc(carWashId);
    }

    @Override
    public void deleteAllByCarWash_Id(Long carWashId) {
        carInQueueRepository.deleteAllByCarWash_Id(carWashId);
    }
}

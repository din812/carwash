package din.springframework.aisa.itservice.test.service;

import din.springframework.aisa.itservice.test.model.CarWash;
import din.springframework.aisa.itservice.test.repositories.CarWashRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class CarWashService implements CarWashRepository {

    private final CarWashRepository carWashRepository;

    public CarWashService(CarWashRepository carWashRepository) {
        this.carWashRepository = carWashRepository;
    }

    @Override
    public <S extends CarWash> S save(S entity) {
        return carWashRepository.save(entity);
    }

    @Override
    public <S extends CarWash> Iterable<S> saveAll(Iterable<S> entities) {
        return carWashRepository.saveAll(entities);
    }

    @Override
    public Optional<CarWash> findById(Long aLong) {
        return carWashRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return carWashRepository.existsById(aLong);
    }

    @Override
    public Iterable<CarWash> findAll() {
        return carWashRepository.findAll();
    }

    @Override
    public Iterable<CarWash> findAllById(Iterable<Long> longs) {
        return carWashRepository.findAllById(longs);
    }

    @Override
    public long count() {
        return carWashRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        carWashRepository.deleteById(aLong);
    }

    @Override
    public void delete(CarWash entity) {
        carWashRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends CarWash> entities) {
        carWashRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        carWashRepository.deleteAll();
    }

    @Override
    public CarWash findFirstByOccupiedFalse() {
        return carWashRepository.findFirstByOccupiedFalse();
    }

    @Override
    public Set<CarWash> findAllByOccupiedUntilBefore(LocalDateTime time) {
        return carWashRepository.findAllByOccupiedUntilBefore(time);
    }

    @Override
    public Set<CarWash> findAllByOccupiedFalse() {
        return carWashRepository.findAllByOccupiedFalse();
    }
}

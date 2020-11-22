package din.springframework.aisa.itservice.test.repositories;

import din.springframework.aisa.itservice.test.model.CarWashingServiceType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarWashingServiceTypeService implements CarWashingServiceTypeRepository {

    private final CarWashingServiceTypeRepository carWashingServiceTypeRepository;

    public CarWashingServiceTypeService(CarWashingServiceTypeRepository carWashingServiceTypeRepository) {
        this.carWashingServiceTypeRepository = carWashingServiceTypeRepository;
    }

    @Override
    public <S extends CarWashingServiceType> S save(S entity) {
        return carWashingServiceTypeRepository.save(entity);
    }

    @Override
    public <S extends CarWashingServiceType> Iterable<S> saveAll(Iterable<S> entities) {
        return carWashingServiceTypeRepository.saveAll(entities);
    }

    @Override
    public Optional<CarWashingServiceType> findById(Long s) {
        return carWashingServiceTypeRepository.findById(s);
    }

    @Override
    public boolean existsById(Long s) {
        return carWashingServiceTypeRepository.existsById(s);
    }

    @Override
    public Iterable<CarWashingServiceType> findAll() {
        return carWashingServiceTypeRepository.findAll();
    }

    @Override
    public Iterable<CarWashingServiceType> findAllById(Iterable<Long> aLongs) {
        return carWashingServiceTypeRepository.findAllById(aLongs);
    }

    @Override
    public long count() {
        return carWashingServiceTypeRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        carWashingServiceTypeRepository.deleteById(aLong);
    }

    @Override
    public void delete(CarWashingServiceType entity) {
        carWashingServiceTypeRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends CarWashingServiceType> entities) {
        carWashingServiceTypeRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        carWashingServiceTypeRepository.deleteAll();
    }
}

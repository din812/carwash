package din.springframework.aisa.itservice.test.repositories;

import din.springframework.aisa.itservice.test.model.CarWash;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Set;

public interface CarWashRepository extends CrudRepository<CarWash, Long> {

    CarWash findFirstByOccupiedFalse();

    Set<CarWash> findAllByOccupiedUntilBefore(LocalDateTime time);

    Set<CarWash> findAllByOccupiedFalse();
}

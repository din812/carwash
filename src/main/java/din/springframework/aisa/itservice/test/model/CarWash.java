package din.springframework.aisa.itservice.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Entity
public class CarWash {

    @Id
    @NotNull
    private Long id;
    private boolean occupied;
    private LocalDateTime occupiedUntil;
    private String carPlate;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "carWash")
    private List<CarInQueue> carQueue = new LinkedList<>();
}

package din.springframework.aisa.itservice.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarWashingServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceName;
    private Long requiredTime;
    private BigDecimal price;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "ordered_services",
            joinColumns = @JoinColumn(name = "car_washing_service_type_id"),
            inverseJoinColumns = @JoinColumn(name = "car_in_queue_id"))
    private List<CarInQueue> carInQueue = new LinkedList<>();
}

package din.springframework.aisa.itservice.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class CarInQueue{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private UUID id;

    private Long placeInQueue;

    @NotBlank
    @Size(min = 5, max = 10)
    private String carPlate;

    private Long washTime;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    //@NotBlank
    @ManyToMany
    @JoinTable(name = "ordered_services",
        joinColumns = @JoinColumn(name = "car_in_queue_id"),
        inverseJoinColumns = @JoinColumn(name = "car_washing_service_type_id"))
    private List<CarWashingServiceType> orderedServices = new LinkedList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    private CarWash carWash;
}

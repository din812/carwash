package din.springframework.aisa.itservice.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
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

    private String carPlate;

    private Long washTime;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ordered_services",
        joinColumns = @JoinColumn(name = "car_in_queue_id"),
        inverseJoinColumns = @JoinColumn(name = "car_washing_service_type_id"))
    private List<CarWashingServiceType> orderedServices = new LinkedList<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private CarWash carWash;
}

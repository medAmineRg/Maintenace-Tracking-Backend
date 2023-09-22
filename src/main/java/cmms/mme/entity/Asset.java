package cmms.mme.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Asset extends Auditable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String model;
    private LocalDate purchaseDate;
    @ManyToOne(cascade = {CascadeType.REFRESH}) @JoinColumn(name = "creator")
    private AppUser creator;
}

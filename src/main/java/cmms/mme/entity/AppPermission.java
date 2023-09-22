package cmms.mme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppPermission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String namePerm;

    @Override
    public String toString() {
        return "AppPermission{" +
                "id=" + id +
                ", namePerm='" + namePerm + '\'' +
                '}';
    }
}

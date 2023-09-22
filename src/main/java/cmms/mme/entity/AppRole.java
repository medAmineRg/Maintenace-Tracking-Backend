package cmms.mme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AppRole implements GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String authority;
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(name = "role_permission")
    private List<AppPermission> permission = new ArrayList<>();

    public Long getId() {
        return id;
    }
    public List<AppPermission> getPermission() {
        return permission;
    }
    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", permission=" + permission +
                '}';
    }
}

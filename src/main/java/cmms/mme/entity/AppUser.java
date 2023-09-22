package cmms.mme.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Getter @AllArgsConstructor @NoArgsConstructor
public class AppUser implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25)
    @Size(min = 3, max = 25, message = "first name must be between 3 and 25 char.")
    private String firstName;
    @Column(length = 25)
    @Size(min = 3, max = 25, message = "last name must be between 3 and 25 char.")
    private String lastName;
    @Column(unique = true, length = 50)
    @Email
    @Size(max = 50, message = "max length for email is 50 char")
    private String email;
    private String password;
    private boolean enabled=false;
    @Column(unique = true)
    @Pattern(regexp = "0[6-7]\\d{8}", message = "Please provide us with a valid phone number")
    @Size(min = 10, max = 10)
    private String phone;
    @ManyToMany(fetch = FetchType.EAGER) @JoinTable(name = "user_role")
    private Collection<AppRole> role = new ArrayList<>();


    public AppUser(String firstName, String lastName, String email, String password, String phone, Collection<AppRole> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }
    public Collection<AppRole> getRole() {
        return role;
    }
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setRole(Collection<AppRole> role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}

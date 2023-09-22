package cmms.mme.dto;

import lombok.*;
import java.util.List;


@Setter @Getter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean enabled;
    private String phone;
    private List<RoleDto> role;
    private String token;
    private String refresh_token;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", token='" + token + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}

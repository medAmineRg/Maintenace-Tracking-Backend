package cmms.mme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data @AllArgsConstructor @NoArgsConstructor
public class UpdateRequest {

    @Size(min = 3, max = 25)
    private String firstName;
    @Size(min = 3, max = 25)
    private String lastName;
    @Email
    @Size(max = 50)
    private String username;
    private String password;
    @Size(min = 10, max = 10)
    private String phone;
    private long idRole;
    private String enabled;
}

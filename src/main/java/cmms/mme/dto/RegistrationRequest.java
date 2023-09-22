package cmms.mme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class RegistrationRequest {

    @NotBlank
    @Size(min = 3, max = 25, message = "first name must be between 3 and 25 char.")
    private String firstName;
    @NotBlank
    @Size(min = 3, max = 25, message = "last name must be between 3 and 25 char.")
    private String lastName;
    @Email
    @NotBlank
    @Size(max = 50, message = "max length for email is 50 char")
    private String email;
    @NotBlank
    @Size(min = 8)
    private String password;
    @NotBlank
    @Pattern(regexp = "0[6-7]\\d{8}", message = "Please provide us with a valid phone number")
    private String phone;
}

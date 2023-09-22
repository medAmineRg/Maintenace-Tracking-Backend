package cmms.mme.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class Invitation {
    @Email
    @NotNull
    private String email;
}

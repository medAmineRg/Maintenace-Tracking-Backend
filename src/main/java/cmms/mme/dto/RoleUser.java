package cmms.mme.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RoleUser {
    @NotBlank
    private String role;
    @NotBlank
    private String user;

}

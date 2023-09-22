package cmms.mme.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RolePermission {

    @NotBlank
    private String role;
    private List<PermissionDto> permissions;
}

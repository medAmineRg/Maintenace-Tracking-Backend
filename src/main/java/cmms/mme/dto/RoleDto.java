package cmms.mme.dto;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RoleDto {
    private Long id;
    private String authority;
    private List<PermissionDto> permission;

    @Override
    public String toString() {
        return "RoleDto{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", permission=" + permission +
                '}';
    }
}

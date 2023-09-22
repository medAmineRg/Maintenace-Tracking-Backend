package cmms.mme.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDto {

    private Long id;
    @NotBlank
    private String namePerm;

    @Override
    public String toString() {
        return "PermissionDto{" +
                "id=" + id +
                ", namePerm='" + namePerm + '\'' +
                '}';
    }
}

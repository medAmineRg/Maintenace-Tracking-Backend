package cmms.mme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class AssetDto {
    private long id;
    private String name;
    private String description;
    private String model;
    private LocalDate purchaseDate;
    private UserDto creator;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
}

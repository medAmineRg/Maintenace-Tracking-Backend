package cmms.mme.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Setter @Getter
public class WorkOrderDto {

    private Long id;
    @Size(min = 15, max = 80)
    private String title;
    @Size(min = 50, message = "A good description must contain at least 50 char")
    private String description;
    private short esDuration;
    private UserDto creator;
    private List<UserDto> assignee;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startOn;
    private String category;
    private String priority;
    private String createdBy;
    private Date createdDate;
    private String lastModifiedBy;
    private Date lastModifiedDate;
    private UserDto completedBy;
    private AssetDto asset;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;


    @Override
    public String toString() {
        return "WorkOrderDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", esDuration=" + esDuration +
                ", creator=" + creator +
                ", assignee=" + assignee +
                ", status='" + status + '\'' +
                ", startOn=" + startOn +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", completedBy=" + completedBy +
                ", asset=" + asset +
                ", completedAt=" + completedAt +
                '}';
    }
}

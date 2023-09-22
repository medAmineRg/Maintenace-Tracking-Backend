package cmms.mme.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity @AllArgsConstructor @NoArgsConstructor @Data
public class WorkOrder extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private short esDuration;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "creator")
    private AppUser creator;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "asset")
    private Asset asset;
    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "work_workers",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "assignee")

    )
    private List<AppUser> assignee;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status = Status.Open;
    @Column(length = 80)
    @Size(min = 15, max = 80)
    private String title;
    @Column(columnDefinition = "TEXT")
    @Size(min = 50)
    private String description;
    private LocalDateTime startOn;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Category category=Category.None;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 6)
    private Priority priority=Priority.None;
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "completedBy")
    private AppUser completedBy;
    private LocalDateTime completedAt;


    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", esDuration=" + esDuration +
                ", creator=" + creator +
                ", asset=" + asset +
                ", assignee=" + assignee +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startOn=" + startOn +
                ", category=" + category +
                ", priority=" + priority +
                ", completedBy=" + completedBy +
                ", completedAt=" + completedAt +
                '}';
    }
}

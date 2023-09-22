package cmms.mme.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    @CreatedBy @Column(name = "created_by")
    private String createdBy;
    @CreatedDate@Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @LastModifiedBy @Column(name = "modified_by")
    private String lastModifiedBy;
    @LastModifiedDate@Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
}

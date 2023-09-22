package cmms.mme.repository;

import cmms.mme.dto.WOByMonth;
import cmms.mme.dto.WOStatus;
import cmms.mme.entity.AppUser;
import cmms.mme.entity.WorkOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

    @Query("SELECT p FROM WorkOrder p WHERE CONCAT(p.title, p.description, p.esDuration, p.creator.lastName, p.category, p.status, p.priority) LIKE %?1%")
    List<WorkOrder> search(String keyword);

    @Query("SELECT count(wo) from WorkOrder wo WHERE wo.status = 'Complete'")
    Long completedWoCount();

    @Query("SELECT new cmms.mme.dto.WOStatus(c.status, COUNT(c.status)) "
            + "FROM WorkOrder AS c GROUP BY c.status")
    List<WOStatus>  woStatus();

    @Query("SELECT new cmms.mme.dto.WOByMonth(MONTH(wo.createdDate), count(wo.createdDate)) \n" +
            "FROM WorkOrder wo \n" +
            "where YEAR(wo.createdDate) = YEAR(current_date()) \n" +
            "GROUP BY MONTH(wo.createdDate)")
    List<WOByMonth> woByMonth();

    @Query("SELECT COUNT(*) " +
            "FROM WorkOrder wo " +
            "WHERE TO_CHAR(wo.createdDate, 'YYYY-MM-DD') = TO_CHAR(current_date(), 'YYYY-MM-DD')")
    int woForToday();
    @Query("SELECT COUNT(*)" +
            "FROM WorkOrder wo " +
            "WHERE DATE_PART('WEEK', wo.createdDate) = DATE_PART('WEEK', current_date()) " +
            "AND YEAR(wo.createdDate) = YEAR(current_date())")
    int woForThisWeek();

    @Query("SELECT COUNT(*)" +
            "FROM WorkOrder wo " +
            "WHERE DATE_PART('MONTH', wo.createdDate) = DATE_PART('MONTH', current_date()) " +
            "AND YEAR(wo.createdDate) = YEAR(current_date())")
    int woForThisMonth();

    @Query(value = "SELECT wo " +
            "FROM WorkOrder wo " +
            "JOIN wo.assignee ass " +
            "where ass.id = :assigneeID",
            countQuery = "SELECT count(*) FROM WorkOrder"
    )
    Page<WorkOrder> findWorkOrderByAssignee(@Param("assigneeID") Long id, Pageable pageable);
}

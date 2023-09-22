package cmms.mme.repository;

import cmms.mme.entity.AppUser;
import cmms.mme.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByPhone(String phone);

    @javax.transaction.Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = true " +
            "WHERE a.email = ?1")
    int updateEnabled(String email);

    @Query("SELECT p FROM AppUser p WHERE CONCAT(p.firstName, p.lastName, p.email, p.phone) LIKE %?1%")
    List<AppUser> search(String keyword);
}

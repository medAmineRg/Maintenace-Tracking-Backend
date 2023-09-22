package cmms.mme.repository;

import cmms.mme.entity.AppPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<AppPermission, Long> {

    Optional<AppPermission> findByNamePerm(String perm);
}

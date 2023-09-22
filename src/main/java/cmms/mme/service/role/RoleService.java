package cmms.mme.service.role;

import cmms.mme.dto.RoleDto;
import javassist.NotFoundException;

import java.util.List;

public interface RoleService {

    List<RoleDto> getRoles();
    RoleDto addRole(RoleDto role);
    RoleDto updateRole(RoleDto role,Long id) throws NotFoundException;
    void deleteRole(Long id) throws NotFoundException;

    RoleDto getRoleById(long id) throws NotFoundException;
}

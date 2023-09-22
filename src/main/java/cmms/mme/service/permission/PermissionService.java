package cmms.mme.service.permission;

import cmms.mme.dto.PermissionDto;
import javassist.NotFoundException;

import java.util.List;

public interface PermissionService {

    List<PermissionDto> getPermissions();
    PermissionDto addPermission(PermissionDto permission);
    PermissionDto updatePermission(PermissionDto permission,Long id) throws NotFoundException;
    void deletePermission(Long id) throws NotFoundException;
    PermissionDto getPermissionById(long id) throws NotFoundException;

}

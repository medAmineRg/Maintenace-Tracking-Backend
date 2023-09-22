package cmms.mme.service.permission;

import cmms.mme.dto.PermissionDto;
import cmms.mme.entity.AppPermission;
import cmms.mme.mapper.PermissionMapper;
import cmms.mme.repository.PermissionRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepo;
    private final static PermissionMapper permissionMapper = Mappers.getMapper(PermissionMapper.class);
    @Override
    public List<PermissionDto> getPermissions() {
        return permissionMapper.permissionEntityToDtoList(permissionRepo.findAll());
    }

    @Override
    public PermissionDto addPermission(PermissionDto permission) {
        return permissionMapper.permissionEntityToDto
                (permissionRepo.save(permissionMapper.permissionDtoToEntity(permission)));
    }

    @Override
    public PermissionDto updatePermission(PermissionDto permission, Long id) throws NotFoundException {
        try {
            AppPermission updPermission = permissionRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("role not found"));

            updPermission.setNamePerm(permission.getNamePerm());
            return permissionMapper.permissionEntityToDto(permissionRepo.save(updPermission));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletePermission(Long id) throws NotFoundException {
        try {
            AppPermission delPermission = permissionRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("role not found"));

            permissionRepo.deleteById(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PermissionDto getPermissionById(long id) throws NotFoundException {
        AppPermission perm = permissionRepo.findById(id).orElseThrow(() -> new NotFoundException("Permission Not Found"));
        return permissionMapper.permissionEntityToDto(perm);
    }
}

package cmms.mme.mapper;

import cmms.mme.dto.PermissionDto;
import cmms.mme.entity.AppPermission;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    List<PermissionDto> permissionEntityToDtoList(List<AppPermission> permissions);
    List<AppPermission> permissionDtoToEntityList(List<PermissionDto> permissions);
    PermissionDto permissionEntityToDto(AppPermission permission);
    AppPermission permissionDtoToEntity(PermissionDto permission);

}

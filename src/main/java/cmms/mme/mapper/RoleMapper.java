package cmms.mme.mapper;

import cmms.mme.dto.RoleDto;
import cmms.mme.entity.AppRole;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<AppRole> roleDtoToEntityList(List<RoleDto> role);
    List<RoleDto> roleEntityToDtoList(List<AppRole> appRole);

    AppRole roleDtoToEntity(RoleDto role);
    RoleDto roleEntityToDto(AppRole appRole);
}

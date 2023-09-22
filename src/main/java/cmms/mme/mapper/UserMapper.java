package cmms.mme.mapper;
import cmms.mme.dto.PageDto;
import cmms.mme.dto.UserDto;
import cmms.mme.entity.AppUser;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper
public interface UserMapper {

    UserDto userEntityToDto(AppUser appUser);
    List<UserDto> userEntityToDtoList(List<AppUser> appUser);

    PageDto<UserDto> userEntityPageToDtoPage(Page<AppUser> appUser);
    AppUser userDtoToEntity(UserDto userDto);
    List<AppUser> userDtoToEntityList(List<UserDto> userDto);

}

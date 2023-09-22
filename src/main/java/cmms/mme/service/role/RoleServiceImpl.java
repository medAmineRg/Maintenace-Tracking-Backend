package cmms.mme.service.role;

import cmms.mme.dto.RoleDto;
import cmms.mme.entity.AppRole;
import cmms.mme.mapper.RoleMapper;
import cmms.mme.mapper.UserMapper;
import cmms.mme.repository.RoleRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepo;
    private final static RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);


    @Override
    public List<RoleDto> getRoles() {
        return roleMapper.roleEntityToDtoList(roleRepo.findAll());
    }

    @Override
    public RoleDto addRole(RoleDto role) {
        return roleMapper.roleEntityToDto(roleRepo.save(roleMapper.roleDtoToEntity(role)));
    }

    @Override
    public RoleDto updateRole(RoleDto role, Long id) throws NotFoundException {

        try {
            AppRole updRole = roleRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("role not found"));

            updRole.setAuthority(role.getAuthority());
            return roleMapper.roleEntityToDto(roleRepo.save(updRole));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRole(Long id) throws NotFoundException{
        try {
            AppRole updRole = roleRepo.findById(id)
                    .orElseThrow(() -> new NotFoundException("role not found"));

            roleRepo.deleteById(id);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RoleDto getRoleById(long id) throws NotFoundException {
         AppRole role = roleRepo.findById(id).orElseThrow(() -> new NotFoundException("Role Not Found"));
         return roleMapper.roleEntityToDto(role);
    }
}

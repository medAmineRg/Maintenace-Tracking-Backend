package cmms.mme.controller;

import cmms.mme.dto.RoleDto;
import cmms.mme.service.role.RoleService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("v1")
public class RoleController {

    private final RoleService roleService ;
    @GetMapping(path = "/role")
    public List<RoleDto> getRoles() {
        return roleService.getRoles();
    }

    @GetMapping(path = "/role/{id}")
    public RoleDto getRoleById(@PathVariable("id") long id) throws NotFoundException {
        return roleService.getRoleById(id);
    }
    @PostMapping(path = "/role")
    public RoleDto addRole(@RequestBody RoleDto role) {
        return roleService.addRole(role);
    }

    @PutMapping(path = "/role/{id}")
    public RoleDto updateRole(@RequestBody RoleDto role,@PathVariable("id") Long id) throws NotFoundException {
        System.out.println("id = " + id);
        return roleService.updateRole(role, id);
    }

    @DeleteMapping(path = "/role/{id}")
    public void deleteRole(@PathVariable("id") Long id) throws NotFoundException {
        roleService.deleteRole(id);
    }
}

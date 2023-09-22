package cmms.mme.controller;

import cmms.mme.dto.PermissionDto;
import cmms.mme.service.permission.PermissionService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor @RequestMapping("/v1")
public class PermissionController {

    private final PermissionService permissionService ;
    @GetMapping(path = "/permission")
    public List<PermissionDto> getRoles() {
        return permissionService.getPermissions();
    }

    @GetMapping(path = "/permission/{id}")
    public PermissionDto getPermissionById(@PathVariable("id") long id) throws NotFoundException {
        return permissionService.getPermissionById(id);
    }
    @PostMapping(path = "/permission")
    public PermissionDto addRole(@RequestBody PermissionDto permission) {
        return permissionService.addPermission(permission);
    }

    @PutMapping(path = "/permission/{id}")
    public PermissionDto updateRole(@RequestBody PermissionDto permission,@PathVariable("id") Long id) throws NotFoundException {
        System.out.println("id = " + id);
        return permissionService.updatePermission(permission, id);
    }

    @DeleteMapping(path = "/permission/{id}")
    public void deleteRole(@PathVariable("id") Long id) throws NotFoundException {
        permissionService.deletePermission(id);
    }

}

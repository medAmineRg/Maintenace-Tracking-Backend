package cmms.mme.controller;

import cmms.mme.dto.*;
import cmms.mme.exception.AlreadyTakenException;
import cmms.mme.exception.UserHasRoleException;
import cmms.mme.service.account.AccountService;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController @RequiredArgsConstructor
@RequestMapping("/v1")
public class AccountController {

    private final AccountService accountService;
    @PostMapping("/signup")
    public String register(@Valid @RequestBody RegistrationRequest request) throws AlreadyTakenException {
        return accountService.register(request);
    }
    @GetMapping("/signup/confirm")
    public String enableUser(@RequestParam("token") String token) {
        return accountService.confirmToken(token);
    }

    @PostMapping("/user/send-invitation")
    public String sendInvitation(@RequestBody @Valid Set<Invitation> emails) {
        return accountService.sendInvitation(emails);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthCredentialsRequest request) throws BadCredentialsException{
        return accountService.login(request);
    }
    @GetMapping("/user")
    public APIResponse<PageDto<UserDto>> listAllUsers(@RequestParam(name ="start", required = false) Integer start, @RequestParam(name="size", required = false) Integer size) {

        int st = (start != null) ? start : 0;
        int sz = (size!= null) ? size : 10;
        System.out.println("start = " + start);
        System.out.println("size = " + size);
        PageDto<UserDto> pageUserDto = accountService.listAllUsers(st, sz);
        return new APIResponse<>(pageUserDto.getSize(), pageUserDto);
    }
    @GetMapping("/user/{id}")
    public UserDto listAllUsers(@PathVariable("id") long id) throws NotFoundException {
        return accountService.getUserById(id);
    }

    @GetMapping("/user/search")
    public List<UserDto> getWorkOrderByKeyword(@RequestParam(name="keyword", required = false) String keyword) {
        return accountService.getUserByKey(keyword);
    }

    @PutMapping("/user/{id}")
    public UserDto updateUser(@PathVariable("id") long id, @RequestBody(required = false) @Valid UpdateRequest request) throws NotFoundException, AlreadyTakenException {
        return accountService.updateUser(id, request);
    }

    @PostMapping("/user")
    public String addUser(@RequestBody RegistrationRequest request) throws AlreadyTakenException {
        return accountService.register(request);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws NotFoundException {
         accountService.deleteUser(id);
    }
    @PostMapping("/role-to-user")
    public void addRoleToUser(@RequestBody @Valid RoleUser roleUser) throws UserHasRoleException, NotFoundException {
        accountService.addRoleToUser(roleUser.getUser(), roleUser.getRole());
    }
    @DeleteMapping("/role-to-user")
    public void removeRoleToUser(@RequestBody @Valid RoleUser roleUser) throws UserHasRoleException, NotFoundException {
        accountService.removeRoleToUser(roleUser.getUser(), roleUser.getRole());
    }
    @PostMapping("/permission-to-role")
    public void addPermissionToRole(@RequestBody @Valid RolePermission rolePermission) throws Exception {
        accountService.addPermissionsToRole(rolePermission.getRole(), rolePermission.getPermissions());
    }

    @DeleteMapping("/permission-to-role")
    public void removePermissionToRole(@RequestBody @Valid RolePermission rolePermission) throws NotFoundException {
        accountService.removePermissionsToRole(rolePermission.getRole(), rolePermission.getPermissions());
    }


}


package cmms.mme.service.account;

import cmms.mme.dto.*;
import cmms.mme.exception.AlreadyTakenException;
import cmms.mme.exception.UserHasRoleException;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.List;
import java.util.Set;


public interface AccountService {

    String register(RegistrationRequest request) throws AlreadyTakenException;
    ResponseEntity<?> login(AuthCredentialsRequest request) throws BadCredentialsException;
    String confirmToken(String token);
    void addPermissionsToRole(String role, List<PermissionDto> permissions) throws NotFoundException, AlreadyTakenException;
    void removePermissionsToRole(String role, List<PermissionDto> permissions) throws NotFoundException;
    PageDto<UserDto> listAllUsers(int start, int size);
    void addRoleToUser(String username, String nameRole) throws UserHasRoleException, NotFoundException;
    void removeRoleToUser(String username, String nameRole) throws NotFoundException, UserHasRoleException;
    UserDto getUserById(long id) throws NotFoundException;
    void deleteUser(Long id) throws NotFoundException;
    UserDto updateUser(long id, UpdateRequest request) throws NotFoundException, AlreadyTakenException;
    List<UserDto> getUserByKey(String keyword);
    String sendInvitation(Set<Invitation> emails);
}

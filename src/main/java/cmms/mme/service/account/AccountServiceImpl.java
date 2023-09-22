package cmms.mme.service.account;

import cmms.mme.dto.*;
import cmms.mme.email.EmailSender;
import cmms.mme.entity.AppPermission;
import cmms.mme.entity.AppRole;
import cmms.mme.entity.AppUser;
import cmms.mme.entity.ConfirmationToken;
import cmms.mme.exception.AlreadyTakenException;
import cmms.mme.exception.UserHasRoleException;
import cmms.mme.mapper.PermissionMapper;
import cmms.mme.mapper.UserMapper;
import cmms.mme.repository.PermissionRepository;
import cmms.mme.repository.RoleRepository;
import cmms.mme.repository.UserRepository;
import cmms.mme.service.token.ConfirmationTokenService;
import cmms.mme.service.user.UserService;
import cmms.mme.utils.Email;
import cmms.mme.utils.JwtTokenUtil;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final static UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final static PermissionMapper permissionMapper = Mappers.getMapper(PermissionMapper.class);
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    public final PermissionRepository permissionRepo;
    public final RoleRepository roleRepo;
    public final UserRepository userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public String register(RegistrationRequest request) throws AlreadyTakenException {

        String token = userService.signUp(request);
        String link = "http://localhost:8082/api/v1/signup/confirm?token="+token;
        emailSender.send(request.getEmail(), "MME, Confirmation email","MME APPLICATION", Email.buildEmail(request.getFirstName(), link));
        return "Please go check your email to validate your account.";
    }

    @Override
    public void addRoleToUser(String username, String nameRole) throws UserHasRoleException, NotFoundException {
        AppRole role = roleRepo.findByAuthority(nameRole)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        AppUser user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isAlreadyHasRole =  user.getRole().stream().anyMatch(ro -> Objects.equals(ro.getAuthority(), nameRole));
        if (isAlreadyHasRole) throw new UserHasRoleException("User already has this role.");
        user.getRole().clear();
        user.getRole().add(role);
        userRepo.save(user);
    }

    @Override
    public void removeRoleToUser(String username, String nameRole) throws NotFoundException, UserHasRoleException {
        AppUser user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean isAlreadyHasRole =  user.getRole().stream().anyMatch(role -> Objects.equals(role.getAuthority(), nameRole));
        if (!isAlreadyHasRole) throw new UserHasRoleException("User doesn't have this role.");
        AppRole role = roleRepo.findByAuthority(nameRole)
                .orElseThrow(() -> new NotFoundException("Role not found"));
        System.out.println("role = " + role);
        user.getRole().remove(role);
        System.out.println(user);
        userRepo.save(user);
    }

    @Override
    public UserDto getUserById(long id) throws NotFoundException {

        AppUser user = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        return userMapper.userEntityToDto(user);
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        AppUser user = userRepo.findById(id)
                .orElseThrow(() -> new javassist.NotFoundException("User Not Found"));
        userRepo.delete(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(long id, UpdateRequest request) throws NotFoundException, AlreadyTakenException {

        System.out.println(" request " + request);
        AppUser user = userRepo.findById(id)
                .orElseThrow(() -> new javassist.NotFoundException("User Not Found"));

        if(request.getLastName() != null &&
            request.getLastName().length() > 0 &&
              !Objects.equals(request.getLastName(), user.getLastName())
        )   {
            user.setLastName(request.getLastName());
        }

        List<AppRole> role = new ArrayList<>(user.getRole());

        if(request.getIdRole() != 0 &&
            role.get(0).getId() != request.getIdRole()
        ) {
            List<AppRole> newRole = new ArrayList<>();
            AppRole foundRole = roleRepo.findById(request.getIdRole()).orElseThrow(() -> new NotFoundException("role not found"));
            newRole.add(foundRole);
            user.setRole(newRole);
        }

        if(request.getEnabled() != null) {
           if(request.getEnabled().equals("true")) {
               user.setEnabled(true);
           } else if (request.getEnabled().equals("false")) {
               user.setEnabled(false);

           }
        }

        if(request.getFirstName() != null &&
                request.getFirstName().length() > 0 &&
                !Objects.equals(request.getFirstName(), user.getFirstName())
        )   {
            user.setFirstName(request.getFirstName());
        }

        if(request.getPhone() != null &&
                request.getPhone().length() == 10 &&
                !Objects.equals(request.getPhone(), user.getPhone())
        )   {
            if (userRepo.findByPhone(request.getPhone()).isPresent()) {
                throw new AlreadyTakenException("The phone number already taken");
            }
            user.setPhone(request.getPhone());
        }

        if(request.getPassword() != null &&
                request.getPassword().length() >= 8
        )   {
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }

        if(request.getUsername() != null &&
                request.getUsername().length() > 0 &&
                !Objects.equals(user.getUsername(), request.getUsername())) {
            if (userRepo.findByEmail(request.getUsername()).isPresent()) {
                throw new AlreadyTakenException("The email already taken");
            }
            user.setEmail(request.getUsername());
        }
        return userMapper.userEntityToDto(user);
    }

    @Override
    @Transactional
    public String sendInvitation(Set<Invitation> emails) {
        String link = "http://127.0.0.1:5173/signin";
        emails.forEach(email -> emailSender.send(email.getEmail(), "MME, Invitation Email","MME APPLICATION", Email.invitationEmail(link)));
        return "Email was send successfully";
    }

    @Override
    public void addPermissionsToRole(String role, List<PermissionDto> permissions) throws NotFoundException, AlreadyTakenException {
        AppRole appRole = roleRepo.findByAuthority(role)
                .orElseThrow(() -> new NotFoundException("Role not found."));

        for (AppPermission appPermission: appRole.getPermission()) {
            for (PermissionDto permissionDto: permissions) {
                if (permissionDto.getNamePerm().equals(appPermission.getNamePerm())) {
                    throw new AlreadyTakenException("User already has the permission");
                }
            }
        }

        permissions.forEach(perm -> {
            try {
                AppPermission appPermission = permissionRepo.findByNamePerm(perm.getNamePerm())
                        .orElseThrow(() -> new NotFoundException("Permission not found"));
                appRole.getPermission().add(appPermission);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        roleRepo.save(appRole);
    }

    @Override
    public void removePermissionsToRole(String role, List<PermissionDto> permissions) throws NotFoundException {

        AppRole appRole = roleRepo.findByAuthority(role)
                .orElseThrow(() -> new NotFoundException("Role not found."));

        permissions.forEach(perm -> {
            try {
                AppPermission appPermission = permissionRepo.findByNamePerm(perm.getNamePerm())
                        .orElseThrow(() -> new NotFoundException("Permission not found"));
                appRole.getPermission().remove(appPermission);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println(appRole.getPermission());
        roleRepo.save(appRole);
    }

    @Override
    public  ResponseEntity<?> login(AuthCredentialsRequest request) throws BadCredentialsException {
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(), request.getPassword()
                            )
                    );

            AppUser user = (AppUser) authenticate.getPrincipal();
            UserDto userDto = userMapper.userEntityToDto(user);
            userDto.setToken(jwtTokenUtil.generateToken(user));
            userDto.setRefresh_token(jwtTokenUtil.generateRefreshToken(user.getUsername()));
            return ResponseEntity.ok()
                    .body(userDto);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }




    @Override
    public PageDto<UserDto> listAllUsers(int start, int size) {
        PageDto<UserDto> userDtos = userMapper.userEntityPageToDtoPage(userRepo.findAll(PageRequest.of(start, size)));
        return userDtos;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getAppUser().getUsername());
        return "confirmed";
    }

    @Override
    public List<UserDto> getUserByKey(String keyword) {
        return userMapper.userEntityToDtoList(userRepo.search(keyword));
    }
}

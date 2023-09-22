package cmms.mme.service.user;

import cmms.mme.dto.RegistrationRequest;
import cmms.mme.dto.WorkOrderDto;
import cmms.mme.entity.AppRole;
import cmms.mme.entity.AppUser;
import cmms.mme.entity.ConfirmationToken;
import cmms.mme.exception.AlreadyTakenException;
import cmms.mme.mapper.UserMapper;
import cmms.mme.repository.RoleRepository;
import cmms.mme.repository.UserRepository;
import cmms.mme.service.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final static UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public String signUp(RegistrationRequest request) throws AlreadyTakenException {

        Optional<AppUser> userByEmail = userRepository.findByEmail(request.getEmail());
        Optional<AppUser> userByPhone = userRepository.findByPhone(request.getPhone());

        if(userByEmail.isPresent()) throw new AlreadyTakenException("Email already taken, please login.");
        if(userByPhone.isPresent()) throw new AlreadyTakenException("Phone already exist.");

        AppRole appRole = roleRepository.findByAuthority("ROLE_LIMITED-TECHNICIAN")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Collection<AppRole> role = new ArrayList<>();
        role.add(appRole);

        String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        AppUser appUser = userRepository.save(new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), hashedPassword,request.getPhone(), role));

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60),
                appUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableUser(String email) {
        userRepository.updateEnabled(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{

            AppUser user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("please check your Email or password?"));

            return user;

    }
}

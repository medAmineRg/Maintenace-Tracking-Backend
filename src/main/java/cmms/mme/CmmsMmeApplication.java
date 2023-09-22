package cmms.mme;

import cmms.mme.dto.RegistrationRequest;
import cmms.mme.dto.RoleDto;
import cmms.mme.exception.AlreadyTakenException;
import cmms.mme.exception.UserHasRoleException;
import cmms.mme.service.account.AccountService;
import cmms.mme.service.role.RoleService;
import cmms.mme.service.user.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication
@RequiredArgsConstructor
public class CmmsMmeApplication {

    public final AccountService accountService;
    public final RoleService roleService;
    public final UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(CmmsMmeApplication.class, args);
    }


    @EventListener
    public void seedDB(ContextRefreshedEvent cre) throws AlreadyTakenException, UserHasRoleException, NotFoundException {
        if(roleService.getRoles().size()==0) {
            roleService.addRole( new RoleDto(null, "ROLE_SUPER-ADMIN", new ArrayList<>()));
            roleService.addRole(new RoleDto(null, "ROLE_ADMIN", new ArrayList<>()));
            roleService.addRole(new RoleDto(null, "ROLE_TECHNICIAN", new ArrayList<>()));
            roleService.addRole(new RoleDto(null, "ROLE_LIMITED-TECHNICIAN", new ArrayList<>()));
            accountService.register(new RegistrationRequest(
                    "Mohamed Amine",
                    "RGUIG",
                    "amine.mohamed.rg@gmail.com",
                    "test1234", "0779795192"));
            userService.enableUser("amine.mohamed.rg@gmail.com");
            accountService.addRoleToUser("amine.mohamed.rg@gmail.com", "ROLE_SUPER-ADMIN");
            accountService.register(new RegistrationRequest(
                    "Hamza",
                    "Jettou",
                    "aminerg07@gmail.com",
                    "test1234", "0632996000"));
            userService.enableUser("aminerg07@gmail.com");
            accountService.register(new RegistrationRequest(
                    "Mohamed Amine",
                    "nbr2",
                    "storybdarijja@gmail.com",
                    "test1234", "0632996002"));
            userService.enableUser("storybdarijja@gmail.com");
        }

    }

}

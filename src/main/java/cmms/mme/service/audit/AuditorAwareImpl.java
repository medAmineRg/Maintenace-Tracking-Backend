package cmms.mme.service.audit;

import cmms.mme.entity.AppUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        String firstName = ((AppUser) authentication.getPrincipal()).getFirstName();
        String lastName = ((AppUser) authentication.getPrincipal()).getLastName();

        return Optional.of(lastName + " " + firstName);
    }
}

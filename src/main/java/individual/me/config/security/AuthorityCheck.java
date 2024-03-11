package individual.me.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service("ac")
public class AuthorityCheck {

    public boolean check(String authority){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getAuthorities().stream().anyMatch(obj-> obj.getAuthority().equals(authority));
    }
}

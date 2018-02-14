package by.miner.mono.security;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository userRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.userRepository = applicationUserRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(
                applicationUser.getUsername(),
                applicationUser.getPassword(),
                applicationUser.getRoles().stream()
                        .map(it -> new SimpleGrantedAuthority(it.getName().name()))
                        .collect(toList()));
    }
}

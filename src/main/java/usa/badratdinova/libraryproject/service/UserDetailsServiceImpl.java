package usa.badratdinova.libraryproject.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import usa.badratdinova.libraryproject.model.User;
import usa.badratdinova.libraryproject.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        log.info("Try to find a user by name {}", name);
        Optional<User> user = userRepository.findUserByName(name);
        if (user.isPresent()) {
            UserDetails userDetails = UserDetailsImpl.builder()
                    .id(user.get().getId())
                    .name(user.get().getName())
                    .password(user.get().getPassword())
                    .authorities(user.get().getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList()))
                    .build();
            return userDetails;
        } else {
            log.error("User with name {} not found.", name);
            throw new UsernameNotFoundException("User not found.");
        }
    }
}

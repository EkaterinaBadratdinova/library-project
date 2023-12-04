package usa.badratdinova.libraryproject.controller.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.libraryproject.dto.CustomUserDto;

import usa.badratdinova.libraryproject.model.ERole;
import usa.badratdinova.libraryproject.model.Role;
import usa.badratdinova.libraryproject.model.User;
import usa.badratdinova.libraryproject.repository.RoleRepository;
import usa.badratdinova.libraryproject.repository.UserRepository;
import usa.badratdinova.libraryproject.service.UserDetailsImpl;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@SecurityRequirement(name = "library-users")
@Slf4j
public class AuthRestController {
   private final AuthenticationManager authenticationManager;

   private final UserRepository userRepository;

   private final RoleRepository roleRepository;

   private final PasswordEncoder encoder;

   @PostMapping("/signin")
   public ResponseEntity<?> authenticateUser(@Valid @RequestBody CustomUserDto customUserDto) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(customUserDto.getName(), customUserDto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .toList();
      log.info("User: {} singed-in successfully.", customUserDto.getName());
      return ResponseEntity
              .ok("User signed-in successfully.");
   }

   @PostMapping("/signup")
   public ResponseEntity<?> registerUser(@Valid @RequestBody CustomUserDto customUserDto) {
      if (userRepository.existsByName(customUserDto.getName())) {
         return ResponseEntity
                 .badRequest()
                 .body("This name is already taken.");
      }
      log.info("Try to register user: {}.", customUserDto.toString());
      User user = new User();
      user.setName(customUserDto.getName());
      user.setPassword(encoder.encode(customUserDto.getPassword()));
      Optional<Role> optionalRole = roleRepository.findByName(ERole.ROLE_USER);
      if (optionalRole.isPresent()) {
         Role role = optionalRole.get();
         user.setRoles(Collections.singleton(role));
      }
      userRepository.save(user);
      log.info("User {} registered successfully.", user.toString());
      return ResponseEntity.ok("User registered successfully.");
   }
}

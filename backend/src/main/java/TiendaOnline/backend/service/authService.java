package TiendaOnline.backend.service;

import TiendaOnline.backend.controller.DTOs.authResponse;
import TiendaOnline.backend.controller.DTOs.loginRequest;
import TiendaOnline.backend.controller.DTOs.registerRequest;
import TiendaOnline.backend.entity.Role;
import TiendaOnline.backend.entity.userEntity;
import TiendaOnline.backend.repository.userRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class authService {

    private userRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private userDetailService userDetailsService;
    private jwtService jwtService;

    // constructor con todas las dependencias

    public authResponse register(registerRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }
        userEntity u = new userEntity();
        u.setName(req.name());
        u.setEmail(req.email());
        u.setHashPassword(passwordEncoder.encode(req.password()));
        u.setRole(Role.CUSTOMER); // el rol NUNCA viene del cliente
        userRepository.save(u);
        return new authResponse(jwtService.generateToken(userDetailsService.loadUserByUsername(u.getEmail())));
    }

    public authResponse login(loginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        UserDetails user = userDetailsService.loadUserByUsername(req.email());
        return new authResponse(jwtService.generateToken(user));
    }
}
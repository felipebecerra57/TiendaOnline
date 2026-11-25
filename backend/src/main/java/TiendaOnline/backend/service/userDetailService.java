package TiendaOnline.backend.service;

import TiendaOnline.backend.entity.userEntity;
import TiendaOnline.backend.repository.userRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userDetailService {
    private final userRepository userRepository;

    public userDetailService(userRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String email) {
        userEntity u = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.withUsername(u.getEmail())
                .password(u.getHashPassword())
                .roles(u.getRole().name()) // Spring agrega el prefijo ROLE_
                .build();
    }

}

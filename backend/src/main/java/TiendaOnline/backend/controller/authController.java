package TiendaOnline.backend.controller;

import TiendaOnline.backend.controller.DTOs.authResponse;
import TiendaOnline.backend.controller.DTOs.loginRequest;
import TiendaOnline.backend.controller.DTOs.registerRequest;
import TiendaOnline.backend.service.authService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class authController {

    private final authService authService;

    public authController(authService authService) { this.authService = authService; }

    @PostMapping("/register")
    public ResponseEntity<authResponse> register(@Valid @RequestBody registerRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(req));
    }

    @PostMapping("/login")
    public authResponse login(@Valid @RequestBody loginRequest req) {
        return authService.login(req);
    }
}

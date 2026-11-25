package TiendaOnline.backend.controller.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record registerRequest(@NotBlank String name,
                              @Email @NotBlank String email,
                              @Size(min = 8) String password) {}

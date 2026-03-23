package egovframework.msa.user.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank @Size(max = 50) String name,
        @NotBlank @Email @Size(max = 120) String email
) {
}

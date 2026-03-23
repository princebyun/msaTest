package egovframework.msa.order.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRequest(
        @NotNull @Min(1) Long userId,
        @NotBlank @Size(max = 120) String productName,
        @NotNull @Min(1) Integer quantity
) {
}

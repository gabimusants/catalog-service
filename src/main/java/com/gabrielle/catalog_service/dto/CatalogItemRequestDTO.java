package com.gabrielle.catalog_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;

public record CatalogItemRequestDTO(
        @NotBlank(message = "SKU é obrigatório")
        String sku,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Categoria é obrigatória")
        String category,

        @NotNull(message = "Preço de referência é obrigatório")
        BigDecimal referencePrice,

        Map<String, Object> attributes
) {}
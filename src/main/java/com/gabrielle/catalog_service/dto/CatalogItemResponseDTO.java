package com.gabrielle.catalog_service.dto;

import java.math.BigDecimal;
import java.util.Map;

public record CatalogItemResponseDTO(
        String id,
        String sku,
        String name,
        String category,
        BigDecimal referencePrice,
        Map<String, Object> attributes
) {}
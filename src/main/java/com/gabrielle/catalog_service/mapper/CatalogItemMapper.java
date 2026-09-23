package com.gabrielle.catalog_service.mapper;

import com.gabrielle.catalog_service.dto.CatalogItemRequestDTO;
import com.gabrielle.catalog_service.dto.CatalogItemResponseDTO;
import com.gabrielle.catalog_service.model.CatalogItem;
import org.springframework.stereotype.Component;

@Component
public class CatalogItemMapper {

    public CatalogItem toEntity(CatalogItemRequestDTO dto) {
        CatalogItem item = new CatalogItem();
        item.setSku(dto.sku());
        item.setName(dto.name());
        item.setCategory(dto.category());
        item.setReferencePrice(dto.referencePrice());
        item.setAttributes(dto.attributes());
        return item;
    }

    public CatalogItemResponseDTO toResponseDTO(CatalogItem item) {
        return new CatalogItemResponseDTO(
                item.getId(),
                item.getSku(),
                item.getName(),
                item.getCategory(),
                item.getReferencePrice(),
                item.getAttributes()
        );
    }
}
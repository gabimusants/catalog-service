package com.gabrielle.catalog_service.service;

import com.gabrielle.catalog_service.dto.CatalogItemRequestDTO;
import com.gabrielle.catalog_service.dto.CatalogItemResponseDTO;
import com.gabrielle.catalog_service.exception.CatalogItemNotFoundException;
import com.gabrielle.catalog_service.mapper.CatalogItemMapper;
import com.gabrielle.catalog_service.model.CatalogItem;
import com.gabrielle.catalog_service.repository.CatalogItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogItemService {

    private final CatalogItemRepository catalogItemRepository;
    private final CatalogItemMapper catalogItemMapper;

    public CatalogItemService(CatalogItemRepository catalogItemRepository, CatalogItemMapper catalogItemMapper) {
        this.catalogItemRepository = catalogItemRepository;
        this.catalogItemMapper = catalogItemMapper;
    }

    public CatalogItemResponseDTO create(CatalogItemRequestDTO dto) {
        CatalogItem item = catalogItemMapper.toEntity(dto);
        CatalogItem saved = catalogItemRepository.save(item);
        return catalogItemMapper.toResponseDTO(saved);
    }

    public List<CatalogItemResponseDTO> findAll() {
        return catalogItemRepository.findAll()
                .stream()
                .map(catalogItemMapper::toResponseDTO)
                .toList();
    }

    public List<CatalogItemResponseDTO> findByCategory(String category) {
        return catalogItemRepository.findByCategory(category)
                .stream()
                .map(catalogItemMapper::toResponseDTO)
                .toList();
    }

    public CatalogItemResponseDTO findById(String id) {
        CatalogItem item = catalogItemRepository.findById(id)
                .orElseThrow(() -> new CatalogItemNotFoundException("Item de catálogo não encontrado: " + id));
        return catalogItemMapper.toResponseDTO(item);
    }

    public CatalogItemResponseDTO update(String id, CatalogItemRequestDTO dto) {
        CatalogItem existing = catalogItemRepository.findById(id)
                .orElseThrow(() -> new CatalogItemNotFoundException("Item de catálogo não encontrado: " + id));

        existing.setSku(dto.sku());
        existing.setName(dto.name());
        existing.setCategory(dto.category());
        existing.setReferencePrice(dto.referencePrice());
        existing.setAttributes(dto.attributes());

        CatalogItem updated = catalogItemRepository.save(existing);
        return catalogItemMapper.toResponseDTO(updated);
    }

    public void delete(String id) {
        if (!catalogItemRepository.existsById(id)) {
            throw new CatalogItemNotFoundException("Item de catálogo não encontrado: " + id);
        }
        catalogItemRepository.deleteById(id);
    }
}
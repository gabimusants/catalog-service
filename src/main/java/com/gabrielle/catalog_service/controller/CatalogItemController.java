package com.gabrielle.catalog_service.controller;

import com.gabrielle.catalog_service.dto.CatalogItemRequestDTO;
import com.gabrielle.catalog_service.dto.CatalogItemResponseDTO;
import com.gabrielle.catalog_service.service.CatalogItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog-items")
public class CatalogItemController {

    private final CatalogItemService catalogItemService;

    public CatalogItemController(CatalogItemService catalogItemService) {
        this.catalogItemService = catalogItemService;
    }

    @PostMapping
    public ResponseEntity<CatalogItemResponseDTO> create(@Valid @RequestBody CatalogItemRequestDTO dto) {
        CatalogItemResponseDTO created = catalogItemService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<CatalogItemResponseDTO>> findAll(
            @RequestParam(required = false) String category) {
        if (category != null) {
            return ResponseEntity.ok(catalogItemService.findByCategory(category));
        }
        return ResponseEntity.ok(catalogItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogItemResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(catalogItemService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogItemResponseDTO> update(@PathVariable String id, @Valid @RequestBody CatalogItemRequestDTO dto) {
        return ResponseEntity.ok(catalogItemService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        catalogItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
package com.gabrielle.catalog_service.service;

import com.gabrielle.catalog_service.dto.CatalogItemRequestDTO;
import com.gabrielle.catalog_service.dto.CatalogItemResponseDTO;
import com.gabrielle.catalog_service.exception.CatalogItemNotFoundException;
import com.gabrielle.catalog_service.mapper.CatalogItemMapper;
import com.gabrielle.catalog_service.model.CatalogItem;
import com.gabrielle.catalog_service.repository.CatalogItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogItemServiceTest {

    @Mock
    private CatalogItemRepository catalogItemRepository;

    @Mock
    private CatalogItemMapper catalogItemMapper;

    @InjectMocks
    private CatalogItemService catalogItemService;

    @Test
    void findById_deveRetornarItemQuandoExiste() {
        CatalogItem item = new CatalogItem();
        item.setId("abc123");
        item.setSku("CERV-001");
        item.setName("Cerveja Pilsen");

        CatalogItemResponseDTO expectedDto = new CatalogItemResponseDTO(
                "abc123", "CERV-001", "Cerveja Pilsen", "bebida",
                new BigDecimal("4.50"), Map.of("volumeMl", 350)
        );

        when(catalogItemRepository.findById("abc123")).thenReturn(Optional.of(item));
        when(catalogItemMapper.toResponseDTO(item)).thenReturn(expectedDto);

        CatalogItemResponseDTO result = catalogItemService.findById("abc123");

        assertThat(result).isEqualTo(expectedDto);
    }

    @Test
    void findById_deveLancarExcecaoQuandoNaoExiste() {
        when(catalogItemRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> catalogItemService.findById("id-inexistente"))
                .isInstanceOf(CatalogItemNotFoundException.class);
    }

    @Test
    void create_deveSalvarItemComAtributosFlexiveis() {
        CatalogItemRequestDTO requestDto = new CatalogItemRequestDTO(
                "PARAF-001", "Parafuso Phillips", "ferragem",
                new BigDecimal("0.15"), Map.of("diametroMm", 6, "material", "aço inox")
        );

        CatalogItem itemToSave = new CatalogItem();
        CatalogItem savedItem = new CatalogItem();
        savedItem.setId("xyz789");

        CatalogItemResponseDTO expectedResponse = new CatalogItemResponseDTO(
                "xyz789", "PARAF-001", "Parafuso Phillips", "ferragem",
                new BigDecimal("0.15"), Map.of("diametroMm", 6, "material", "aço inox")
        );

        when(catalogItemMapper.toEntity(requestDto)).thenReturn(itemToSave);
        when(catalogItemRepository.save(itemToSave)).thenReturn(savedItem);
        when(catalogItemMapper.toResponseDTO(savedItem)).thenReturn(expectedResponse);

        CatalogItemResponseDTO result = catalogItemService.create(requestDto);

        assertThat(result.attributes()).containsEntry("diametroMm", 6);
        assertThat(result).isEqualTo(expectedResponse);
    }
}
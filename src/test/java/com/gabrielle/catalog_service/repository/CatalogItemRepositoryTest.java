package com.gabrielle.catalog_service.repository;

import com.gabrielle.catalog_service.model.CatalogItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.mongodb.test.autoconfigure.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Testcontainers
class CatalogItemRepositoryTest {

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7");

    @Autowired
    private CatalogItemRepository catalogItemRepository;

    @Test
    void findBySku_deveEncontrarItemComAtributosFlexiveis() {
        CatalogItem item = new CatalogItem();
        item.setSku("CERV-002");
        item.setName("Cerveja IPA");
        item.setCategory("bebida");
        item.setReferencePrice(new BigDecimal("7.90"));
        item.setAttributes(Map.of("teorAlcoolico", 6.2, "volumeMl", 500));

        catalogItemRepository.save(item);

        Optional<CatalogItem> found = catalogItemRepository.findBySku("CERV-002");

        assertThat(found).isPresent();
        assertThat(found.get().getAttributes()).containsEntry("volumeMl", 500);
    }

    @Test
    void findByCategory_deveRetornarSoItensDaCategoria() {
        CatalogItem bebida = new CatalogItem();
        bebida.setSku("BEB-001");
        bebida.setCategory("bebida");
        bebida.setReferencePrice(BigDecimal.ONE);
        catalogItemRepository.save(bebida);

        CatalogItem ferragem = new CatalogItem();
        ferragem.setSku("FER-001");
        ferragem.setCategory("ferragem");
        ferragem.setReferencePrice(BigDecimal.ONE);
        catalogItemRepository.save(ferragem);

        var resultado = catalogItemRepository.findByCategory("bebida");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getSku()).isEqualTo("BEB-001");
    }
}
package com.gabrielle.catalog_service.repository;

import com.gabrielle.catalog_service.model.CatalogItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CatalogItemRepository extends MongoRepository<CatalogItem, String> {

    Optional<CatalogItem> findBySku(String sku);

    List<CatalogItem> findByCategory(String category);
}
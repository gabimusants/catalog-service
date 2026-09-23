package com.gabrielle.catalog_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Document(collection = "catalog_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class CatalogItem {

    @Id
    private String id;

    private String sku;
    private String name;
    private String category;
    private BigDecimal referencePrice;

    private Map<String, Object> attributes;
}
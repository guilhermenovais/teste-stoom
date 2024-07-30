package br.com.stoom.store.data.dto;

import br.com.stoom.store.data.model.Brand;
import br.com.stoom.store.data.model.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class ProductGetDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private CategoryGetDto category;

    @JsonProperty("brand")
    private BrandGetDto brand;

    @JsonProperty("price")
    private Double price;
}

package br.com.stoom.store.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ProductPostDto {
    @NonNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private CategoryGetDto category;

    @JsonProperty("brand")
    private BrandGetDto brand;

    @NonNull
    @JsonProperty("price")
    private Double price;
}

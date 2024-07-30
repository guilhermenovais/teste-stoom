package br.com.stoom.store.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandGetDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;
}

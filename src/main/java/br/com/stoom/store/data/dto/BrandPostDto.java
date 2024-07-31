package br.com.stoom.store.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BrandPostDto {
    @NotNull
    @JsonProperty("name")
    private String name;

    @JsonProperty("enabled")
    private Boolean enabled = true;
}

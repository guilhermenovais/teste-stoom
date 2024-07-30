package br.com.stoom.store.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CategoryPostDto {
    @NonNull
    @JsonProperty("name")
    private String name;
}

package io.swagger.petstore.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetNotFoundResponse {

    private int code;
    private String type;
    private String message;
}

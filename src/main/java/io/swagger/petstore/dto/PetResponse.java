package io.swagger.petstore.dto;

import io.swagger.petstore.dto.Category;
import io.swagger.petstore.dto.Tag;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetResponse {

    @EqualsAndHashCode.Exclude
    private long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;
}

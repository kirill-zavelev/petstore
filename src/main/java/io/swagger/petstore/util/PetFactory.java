package io.swagger.petstore.util;

import com.github.javafaker.Faker;
import io.swagger.petstore.api.PetApiClient;
import io.swagger.petstore.dto.Category;
import io.swagger.petstore.dto.Tag;
import io.swagger.petstore.dto.PetResponse;

import java.util.List;

public class PetFactory {

    private Faker faker;
    private Category category;
    private Tag tag;
    private PetResponse pet;

    public PetFactory() {
        faker = new Faker();
        category = new Category();
        category.setId(faker.number().randomDigit());
        category.setName(faker.name().suffix());
        tag = new Tag();
        tag.setId(faker.number().randomDigit());
        tag.setName(faker.name().prefix());
        pet = PetResponse.builder()
                .id(faker.number().numberBetween(0, Long.MAX_VALUE))
                .category(category)
                .name(faker.animal().name())
                .photoUrls(List.of(faker.internet().url()))
                .tags(List.of(tag))
                .status(faker.internet().domainWord())
                .build();
    }

    public PetResponse getPet() {
        return new PetApiClient().postAddPet(pet);
    }
}

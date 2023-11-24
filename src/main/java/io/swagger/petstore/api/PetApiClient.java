package io.swagger.petstore.api;

import io.swagger.petstore.dto.PetDeleteResponse;
import io.swagger.petstore.dto.PetResponse;

import java.util.Map;

public class PetApiClient extends BaseApiClient {

    private static final String STATUS = "status";

    private final String PET_PATH = properties.getProperty("pet.path");
    private final String PET_ID_PATH = properties.getProperty("pet.id.path");
    private final String FIND_BY_STATUS_PATH = properties.getProperty("find.by.status.path");

    public PetResponse postAddPet(PetResponse body) {
        return post(PET_PATH, body, PetResponse.class);
    }

    public PetResponse putUpdatePet(PetResponse body) {
        return put(PET_PATH, body, PetResponse.class);
    }

    public PetDeleteResponse deletePet(String petId, int statusCode) {
        return delete(String.format(PET_ID_PATH, petId))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(PetDeleteResponse.class);
    }

    public <R> R getByPetId(String petId, int statusCode, Class<R> responseClass) {
        return get(String.format(PET_ID_PATH, petId))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(responseClass);
    }

    public PetResponse[] getByStatus(String status, int statusCode) {
        return get(FIND_BY_STATUS_PATH, Map.of(STATUS, status))
                .then()
                .statusCode(statusCode)
                .extract()
                .body()
                .as(PetResponse[].class);
    }
}

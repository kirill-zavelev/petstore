package io.swagger.petstore;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import io.swagger.petstore.api.BaseApiClient;
import io.swagger.petstore.api.PetApiClient;
import io.swagger.petstore.dto.PetDeleteResponse;
import io.swagger.petstore.dto.PetNotFoundResponse;
import io.swagger.petstore.dto.PetResponse;
import io.swagger.petstore.util.PetFactory;
import io.swagger.petstore.util.PropertiesLoader;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PetTest {

    private Faker faker;
    private PetResponse expectedPet;
    private long expectedPetId;

    @BeforeClass
    public void setUp() {
        faker = new Faker();
        expectedPet = new PetFactory().getPet();
        expectedPetId = expectedPet.getId();
    }

    @Test
    @Epic("Positive tests")
    @Description("Test to read Pet with valid id")
    public void checkReadPetWithValidId() {
        PetResponse actualPet = new PetApiClient()
                .getByPetId(String.valueOf(expectedPetId), HttpStatus.SC_OK, PetResponse.class);
        assertThat(actualPet)
                .as(actualPet + " should equals " + expectedPet)
                .isEqualTo(expectedPet);
    }

    @Test
    @Epic("Negative tests")
    @Description("Test to read Pet with not existent id")
    public void checkReadPetWithNotExistentId() {
        final String expectedMessage = "Pet not found";
        PetNotFoundResponse getPetResponse = new PetApiClient().getByPetId(String.valueOf(faker.random().nextLong()),
                HttpStatus.SC_NOT_FOUND, PetNotFoundResponse.class);
        assertThat(getPetResponse.getMessage())
                .as("Message should be " + expectedMessage)
                .isEqualTo(expectedMessage);
    }

    @Test
    @Epic("Positive tests")
    @Description("Test to read Pet by it status")
    public void checkReadPetByItStatus() {
        PetResponse[] actualPets = new PetApiClient().getByStatus(expectedPet.getStatus(), HttpStatus.SC_OK);
        assertThat(actualPets)
                .as("Response should contain " + expectedPet)
                .contains(expectedPet);
    }

    @Test
    @Epic("Positive tests")
    @Description("Test to read Pet with not existent status")
    public void checkReadPetsWithNonExistentStatus() {
        PetResponse[] actualPets = new PetApiClient().getByStatus(faker.internet().domainSuffix(), HttpStatus.SC_OK);
        assertThat(actualPets)
                .as("Response should be empty")
                .isEmpty();
    }

    @Test
    @Epic("Positive tests")
    @Description("Test to delete existent Pet")
    public void checkPetCanBeSuccessfullyDeleted() {
        PetResponse petResponse = new PetFactory().getPet();
        final PetDeleteResponse expectedPetDeleteResponse = PetDeleteResponse.builder()
                .code(HttpStatus.SC_OK)
                .type("unknown")
                .message(String.valueOf(petResponse.getId()))
                .build();
        PetDeleteResponse actualPetDeleteResponse = new PetApiClient()
                .deletePet(String.valueOf(petResponse.getId()), HttpStatus.SC_OK);
        assertThat(actualPetDeleteResponse)
                .as("Pet should be deleted")
                .isEqualTo(expectedPetDeleteResponse);
    }

    @Test
    @Epic("Negative tests")
    @Description("Test to delete not existent Pet")
    public void checkDeletePetWithNotExistedId() {
        final String notExistentPetId = String.valueOf(faker.number().numberBetween(0, Long.MAX_VALUE));
        Response actualPetDeleteResponse = new BaseApiClient()
                .delete(String.format(PropertiesLoader.loadProperties().getProperty("pet.id.path"), notExistentPetId));
        assertThat(actualPetDeleteResponse.getStatusCode())
                .as("Status code should be 404")
                .isEqualTo(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    @Epic("Positive tests")
    @Description("Test to update Pet with not existent id")
    public void checkPetNameCanBeSuccessfullyUpdated() {
        expectedPet.setName(expectedPet.getName() + "UPDATED");
        new PetApiClient().putUpdatePet(expectedPet);
        PetResponse actualPet = new PetApiClient()
                .getByPetId(String.valueOf(expectedPetId), HttpStatus.SC_OK, PetResponse.class);
        assertThat(actualPet.getName())
                .as("Name of pet should be updated to: " + expectedPet.getName())
                .isEqualTo(expectedPet.getName());
    }

    @Test
    @Epic("Positive tests")
    @Description("Test to create Pet")
    public void checkPetCanBeSuccessfullyCreated() {
        PetResponse actualPet = new PetApiClient().postAddPet(expectedPet);
        assertThat(actualPet)
                .as("Received pet should be equal to " + expectedPet)
                .isEqualTo(expectedPet);
    }

    @AfterClass(alwaysRun = true)
    public void cleanUp() {
        if (expectedPet != null) {
            new PetApiClient().deletePet(String.valueOf(expectedPetId), HttpStatus.SC_OK);
        }
    }
}

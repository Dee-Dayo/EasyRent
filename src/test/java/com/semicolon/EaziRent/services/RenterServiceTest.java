package com.semicolon.EaziRent.services;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.RegisterResponse;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class RenterServiceTest {
    @Autowired
    private RenterService renterService;

    @Test
    public void testRegisterRenter(){
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("sampleemail@email.com");
        request.setPassword("password");
        RegisterResponse response = renterService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void registerRenterWithWrongEmailFormat_throwsException(){
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("sampleemailemail.com");
        request.setPassword("password");
        assertThrows(EasyRentBaseException.class, ()->renterService.register(request));
    }

    @Test
    public void updateRenterDetailsTest() throws JsonPointerException {
        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/firstName"), new TextNode("updated name")),
                new ReplaceOperation(new JsonPointer("/lastName"), new TextNode("new last name")),
                new ReplaceOperation(new JsonPointer("/occupation"), new TextNode("Farmer")));
        JsonPatch request = new JsonPatch(operations);
        UpdateDataResponse response = renterService.update(200L, request);
        assertThat(response.getFirstName()).isEqualTo("updated name");
        assertThat(response.getLastName()).isEqualTo("new last name");
    }
}
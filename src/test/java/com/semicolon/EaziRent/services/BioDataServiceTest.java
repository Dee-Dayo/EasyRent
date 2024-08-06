package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class BioDataServiceTest {
    @Autowired
    private BioDataService bioDataService;

    @Test
    public void testRegisterBioData(){
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("myemail345@email.com");
        request.setPassword("password");
        BioData response = bioDataService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    public void testRegisterBioDataExistingEmail_throwsException(){
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("myemail@email.com");
        request.setPassword("password");
        bioDataService.register(request);
        RegisterRequest request2 = new RegisterRequest();
        request2.setFirstName("first name");
        request2.setLastName("last name");
        request2.setEmail("myemail@email.com");
        request2.setPassword("password");
        assertThrows(EasyRentBaseException.class,()-> bioDataService.register(request2));
    }

    @Test
    public void testRegisterBioDataWithInvalidEmailFormat_throwsException(){
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("first name");
        request.setLastName("last name");
        request.setEmail("testemail.com");
        request.setPassword("password");
        assertThrows(EasyRentBaseException.class,()-> bioDataService.register(request));
    }
}
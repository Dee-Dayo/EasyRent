package com.semicolon.EaziRent.services;

import com.semicolon.EaziRent.data.models.Property;
import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.responses.AddPropertyResponse;
import com.semicolon.EaziRent.dtos.responses.EaziRentAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.semicolon.EaziRent.utils.TestUtils.TEST_IMAGE_LOCATION;
import static com.semicolon.EaziRent.utils.TestUtils.buildPropertyRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Sql(scripts = "/db/data.sql")
public class PropertyServiceTest {

    @Autowired
    private PropertyService propertyService;

    @Test
    public void addPropertyTest() {
        Path path = Paths.get(TEST_IMAGE_LOCATION);
        try(InputStream inputStream = Files.newInputStream(path)) {
            AddPropertyRequest request = buildPropertyRequest(inputStream);
            EaziRentAPIResponse<AddPropertyResponse> response =
                    propertyService.addProperty(request, "jamespalmer@gmail.com");
            assertThat(response).isNotNull();
            log.info("response--> {}", response.getData());
            assertThat(response.getData().getMediaUrl()).isNotNull();
        } catch (IOException exception) {
            assertThat(exception).isNull();
        }
    }

    @Test
    public void canFindAllProperty(){
        List<Property> properties = propertyService.findAll();
        assertThat(properties.size()).isEqualTo(1);
    }

}
package com.semicolon.EaziRent.utils;

import com.semicolon.EaziRent.dtos.requests.AddPropertyRequest;
import com.semicolon.EaziRent.dtos.requests.AddressRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.semicolon.EaziRent.data.constants.State.LAGOS;
import static com.semicolon.EaziRent.data.constants.Type.HOSTEL;

public class TestUtils {
    public static final String BLACKLISTED_TOKEN = "eyJhbGciOiJSUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOi" +
            "JFYXN5UmVudEFwcCIsImlhdCI6MTcyMTgzNTkwMSwiZXhwIjoxNzIxOTIyMzAxLCJzdWIiOiJbVXNlcm5hbWU" +
            "9Y29sZXNhbUBnbWFpbC5jb20sIFBhc3N3b3JkPVtQUk9URUNURURdLCBFbmFibGVkPXRydWUsIEFjY291bnROb" +
            "25FeHBpcmVkPXRydWUsIENyZWRlbnRpYWxzTm9uRXhwaXJlZD10cnVlLCBBY2NvdW50Tm9uTG9ja2VkPXRydWUs" +
            "IEdyYW50ZWQgQXV0aG9yaXRpZXM9W1JFTlRFUl1dIiwicHJpbmNpcGFsIjoiW1VzZXJuYW1lPWNvbGVzYW1AZ21ha" +
            "WwuY29tLCBQYXNzd29yZD1bUFJPVEVDVEVEXSwgRW5hYmxlZD10cnVlLCBBY2NvdW50Tm9uRXhwaXJlZD10cnVlLCBD" +
            "cmVkZW50aWFsc05vbkV4cGlyZWQ9dHJ1ZSwgQWNjb3VudE5vbkxvY2tlZD10cnVlLCBHcmFudGVkIEF1dGhvcml0aWVz" +
            "PVtSRU5URVJdXSIsImNyZWRlbnRpYWxzIjoiW1BST1RFQ1RFRF0iLCJyb2xlcyI6WyJSRU5URVIiXX0.KPeayLBkPl_EZ2" +
            "PvR-rddNr-uo3G0Sa21aGeLE84NqrFxGLNKyeBuPn0xGRjJS2amVp9rwR7e7ijs0lW6c-XBBPmq5o4NnKK16wey9fd8GB" +
            "Im4k_MtUUq5aHAOpJsPGwYwlfJG3ecLO8Ov6OZDnxsduUNhTBw5m3-HNCjdDBWK8gkaRlzDh7WCX-1cPs8aeMz6imwPiAEG" +
            "UcgPQJ8bthZCF_ZtkBNLY6snO8oS93mbDWOpEQ8KS9RkuzELX5pXy9xo1y2mEyICe_mLqhTeiX7aPK9u4Tie4elhhk7jsu" +
            "NzmxX6KHHbxu49PdeNMRXF0cFYf4f79sRmpENtwP0hDNwQ";

    public static final String TEST_IMAGE_LOCATION = "C:\\Users\\DELL\\IdeaProjects\\EasyRent\\src\\main\\resources\\images\\juno.jpg";


    public static AddPropertyRequest buildPropertyRequest(InputStream inputStream) throws IOException {
        AddPropertyRequest request = new AddPropertyRequest();
        MultipartFile file = new MockMultipartFile("media", inputStream);
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setNumber("32");
        addressRequest.setStreet("Street");
        addressRequest.setArea("Area");
        addressRequest.setState(LAGOS);
        addressRequest.setLga("LGA");
        request.setAddressRequest(addressRequest);
        request.setMediaFile(file);
        request.setType(HOSTEL);
        request.setNoOfApartments(50);
        return request;
    }

}

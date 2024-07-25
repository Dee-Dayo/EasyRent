package com.semicolon.EaziRent.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.repositories.BioDataRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.exceptions.EmailExistsException;
import com.semicolon.EaziRent.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class EaziBioDataService implements BioDataService{
    private final BioDataRepository bioDataRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BioData register(RegisterRequest request) {
        validateEmail(request);
        BioData bioData = modelMapper.map(request, BioData.class);
        bioData.setPassword(passwordEncoder.encode(bioData.getPassword()));
        bioData.setRoles(new HashSet<>());
        bioData.getRoles().add(request.getRole());
        return bioDataRepository.save(bioData);
    }

    @Override
    public BioData getBioDataBy(String email) {
        return bioDataRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public BioData findBioDataBy(Long renterId) {
        return bioDataRepository.findById(renterId)
                .orElseThrow(() -> new EasyRentBaseException("User not found with id: " + renterId));
    }

    @Override
    public UpdateDataResponse update(Long id, JsonPatch jsonPatch) {
        try{
            BioData bioData = findBioDataBy(id);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.convertValue(bioData, JsonNode.class);
            jsonNode = jsonPatch.apply(jsonNode);
            bioData = objectMapper.convertValue(jsonNode, BioData.class);
            bioDataRepository.save(bioData);
            return modelMapper.map(bioData, UpdateDataResponse.class);
        } catch (JsonPatchException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    private void validateEmail(RegisterRequest request) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!request.getEmail().matches(emailRegex))
            throw new EasyRentBaseException("Invalid email format");
        validateExistingEmail(request);
    }

    private void validateExistingEmail(RegisterRequest request) {
        boolean emailExists = bioDataRepository.existsByEmail(request.getEmail());
        if (emailExists) throw new EmailExistsException(request.getEmail() + " already exists");
    }
}

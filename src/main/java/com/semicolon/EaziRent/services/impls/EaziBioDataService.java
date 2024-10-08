package com.semicolon.EaziRent.services.impls;

import com.semicolon.EaziRent.data.models.BioData;
import com.semicolon.EaziRent.data.repositories.BioDataRepository;
import com.semicolon.EaziRent.dtos.requests.RegisterRequest;
import com.semicolon.EaziRent.dtos.requests.SendMailRequest;
import com.semicolon.EaziRent.dtos.requests.UpdateRequest;
import com.semicolon.EaziRent.dtos.responses.UpdateDataResponse;
import com.semicolon.EaziRent.exceptions.EasyRentBaseException;
import com.semicolon.EaziRent.exceptions.EmailExistsException;
import com.semicolon.EaziRent.exceptions.UserNotFoundException;
import com.semicolon.EaziRent.services.BioDataService;
import com.semicolon.EaziRent.services.MailService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;

import static com.semicolon.EaziRent.utils.ServicesUtils.copyNonNullProperties;

@Service
@AllArgsConstructor
public class EaziBioDataService implements BioDataService {
    private final BioDataRepository bioDataRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public BioData register(RegisterRequest request) throws IOException {
        validateEmail(request);
        BioData bioData = modelMapper.map(request, BioData.class);
        bioData.setPassword(passwordEncoder.encode(bioData.getPassword()));
        bioData.setRoles(new HashSet<>());
        bioData.getRoles().add(request.getRole());
        SendMailRequest mailRequest = new SendMailRequest();
        mailRequest.setRecipientEmail(bioData.getEmail());
        mailRequest.setRecipientName(bioData.getFirstName());
        mailService.sendMail(mailRequest);
        return save(bioData);
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
    public UpdateDataResponse update(Long id, UpdateRequest request) {
        BioData bioData = findBioDataBy(id);
        BioData tempData = modelMapper.map(request, BioData.class);
        copyNonNullProperties(tempData, bioData);
        bioDataRepository.save(bioData);
        return modelMapper.map(bioData, UpdateDataResponse.class);

    }

    @Override
    public BioData update(BioData bioData, UpdateRequest request) {
        modelMapper.map(request, bioData);
        return bioDataRepository.save(bioData);
    }

    @Override
    public BioData save(BioData bioData) {
        return bioDataRepository.save(bioData);
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

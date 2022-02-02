package cz.osu.gamingblog.services;

import cz.osu.gamingblog.exceptions.EntityDoesNotExistException;
import cz.osu.gamingblog.exceptions.WrongUserCredentialsException;
import cz.osu.gamingblog.models.User;
import cz.osu.gamingblog.models.UserRole;
import cz.osu.gamingblog.repositories.IUserRepository;
import cz.osu.gamingblog.requests.LoginRequest;
import cz.osu.gamingblog.requests.RegistrationRequest;
import cz.osu.gamingblog.responses.LoginResponse;
import cz.osu.gamingblog.services.interfaces.IAuthenticationService;
import cz.osu.gamingblog.services.interfaces.IJwtService;
import cz.osu.gamingblog.utilities.HashHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static cz.osu.gamingblog.repositories.specifications.UserSpecifications.hasUsername;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final IUserRepository _userRepository;
    private final IJwtService _jwtService;
    private final ModelMapper _modelMapper;

    public AuthenticationService(IUserRepository userRepository,
                                 IJwtService jwtService, ModelMapper modelMapper) {
        _userRepository = userRepository;
        _jwtService = jwtService;
        _modelMapper = modelMapper;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        var user = _userRepository.findOne(hasUsername(loginRequest.getUsername()))
                .orElseThrow(EntityDoesNotExistException::new);

       var hashPassword = hashPassword(loginRequest);

        if (!hashPassword.equals(user.getPassword())) {
            throw new WrongUserCredentialsException();
        }

        LoginResponse loginResponse = mapToLoginResponse(user);
        try {
            loginResponse.setJwtToken(_jwtService.createJwtToken(user.getUsername(), user.getRole().toString()));
        } catch (RuntimeException ex) {
            throw new IllegalStateException("Cannot create JWT token.", ex);
        }

        return loginResponse;
    }

    private String hashPassword(LoginRequest loginRequest) {
        try {
            return HashHelper.hashPassword(loginRequest.getPassword());
        } catch (RuntimeException ex) {
            throw new IllegalStateException("Cannot hash the password.", ex);
        }
    }

    private LoginResponse mapToLoginResponse(User user) {
        try {
            return _modelMapper.map(user, LoginResponse.class);
        } catch (RuntimeException ex) {
            throw new IllegalStateException("Cannot map object User to LoginResponse.", ex);
        }
    }

    @Override
    public void register(RegistrationRequest registrationRequest) {
        registrationRequest.setPassword(HashHelper.hashPassword(registrationRequest.getPassword()));
        var user = _modelMapper.map(registrationRequest, User.class);
        user.setId(0L);
        user.setRole(UserRole.ROLE_USER);
        user.setCreationDate(LocalDate.now());

        try {
            _userRepository.save(user);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not save user.", ex);
        }
    }
}

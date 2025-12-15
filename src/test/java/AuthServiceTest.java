

import com.example.colis.dto.auth.LoginRequest;
import com.example.colis.dto.auth.LoginResponse;
import com.example.colis.model.Admin;
import com.example.colis.model.User;
import com.example.colis.repository.UserRepository;
import com.example.colis.security.JwtUtil;
import com.example.colis.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        testUser = Admin.builder()
                .id("1")
                .login("admin")
                .password("encodedPassword")
                .active(true)
                .build();

        loginRequest = new LoginRequest("admin", "password");
    }

    @Test
    void login_WithValidCredentials_ShouldReturnLoginResponse() {
        // Arrange
        when(userRepository.findByLogin("admin")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(any(), any())).thenReturn("jwt-token");

        // Act
        LoginResponse response = authService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("admin", response.getLogin());

        verify(userRepository).findByLogin("admin");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(jwtUtil).generateToken(any(), any());
    }

    @Test
    void login_WithInvalidLogin_ShouldThrowBadCredentialsException() {
        // Arrange
        when(userRepository.findByLogin("admin")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
        verify(userRepository).findByLogin("admin");
        verify(passwordEncoder, never()).matches(any(), any());
    }

    @Test
    void login_WithInvalidPassword_ShouldThrowBadCredentialsException() {
        // Arrange
        when(userRepository.findByLogin("admin")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
        verify(userRepository).findByLogin("admin");
        verify(passwordEncoder).matches("password", "encodedPassword");
        verify(jwtUtil, never()).generateToken(any(), any());
    }

    @Test
    void login_WithInactiveUser_ShouldThrowBadCredentialsException() {
        // Arrange
        testUser.setActive(false);
        when(userRepository.findByLogin("admin")).thenReturn(Optional.of(testUser));

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
        verify(userRepository).findByLogin("admin");
        verify(passwordEncoder, never()).matches(any(), any());
    }
}
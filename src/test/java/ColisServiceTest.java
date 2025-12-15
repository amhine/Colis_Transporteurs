import com.example.colis.dto.colis.AssignColisRequest;
import com.example.colis.dto.colis.ColisRequest;
import com.example.colis.dto.colis.ColisResponse;
import com.example.colis.exception.BusinessException;
import com.example.colis.exception.ResourceNotFoundException;
import com.example.colis.mapper.ColisMapper;
import com.example.colis.model.Colis;
import com.example.colis.model.ColisFragile;
import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.ColisType;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.UserStatus;
import com.example.colis.model.Transporteur;
import com.example.colis.repository.ColisRepository;
import com.example.colis.repository.UserRepository;
import com.example.colis.service.ColisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColisServiceTest {

    @Mock
    private ColisRepository colisRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ColisMapper colisMapper;

    @InjectMocks
    private ColisService colisService;

    private ColisFragile testColis;
    private Transporteur testTransporteur;
    private ColisRequest colisRequest;

    @BeforeEach
    void setUp() {
        testColis = ColisFragile.builder()
                .id("1")
                .poids(10.0)
                .adresseDestination("Paris")
                .statut(ColisStatus.EN_ATTENTE)
                .instructionsManutention("Manipuler avec précaution")
                .build();

        testTransporteur = Transporteur.builder()
                .id("trans1")
                .login("transporteur1")
                .specialite(Specialite.FRAGILE)
                .statut(UserStatus.DISPONIBLE)
                .active(true)
                .build();

        colisRequest = ColisRequest.builder()
                .type(ColisType.FRAGILE)
                .poids(10.0)
                .adresseDestination("Paris")
                .instructionsManutention("Manipuler avec précaution")
                .build();
    }

    @Test
    void createColis_WithValidFragileData_ShouldCreateColis() {
        // Arrange
        when(colisRepository.save(any(Colis.class))).thenReturn(testColis);
        when(colisMapper.toResponse(any(Colis.class))).thenReturn(new ColisResponse());

        // Act
        ColisResponse response = colisService.createColis(colisRequest);

        // Assert
        assertNotNull(response);
        verify(colisRepository).save(any(Colis.class));
    }

    @Test
    void createColis_WithFragileWithoutInstructions_ShouldThrowBusinessException() {
        // Arrange
        colisRequest.setInstructionsManutention(null);

        // Act & Assert
        assertThrows(BusinessException.class, () -> colisService.createColis(colisRequest));
        verify(colisRepository, never()).save(any());
    }

    @Test
    void createColis_WithFrigoWithoutTemperatures_ShouldThrowBusinessException() {
        // Arrange
        colisRequest.setType(ColisType.FRIGO);
        colisRequest.setTemperatureMin(null);
        colisRequest.setTemperatureMax(null);

        // Act & Assert
        assertThrows(BusinessException.class, () -> colisService.createColis(colisRequest));
        verify(colisRepository, never()).save(any());
    }

    @Test
    void assignColis_WithMatchingSpecialite_ShouldAssignColis() {
        // Arrange
        AssignColisRequest assignRequest = new AssignColisRequest("trans1");
        when(colisRepository.findById("1")).thenReturn(Optional.of(testColis));
        when(userRepository.findById("trans1")).thenReturn(Optional.of(testTransporteur));
        when(colisRepository.save(any(Colis.class))).thenReturn(testColis);
        when(colisMapper.toResponse(any(Colis.class))).thenReturn(new ColisResponse());

        // Act
        ColisResponse response = colisService.assignColis("1", assignRequest);

        // Assert
        assertNotNull(response);
        verify(colisRepository).save(any(Colis.class));
    }

    @Test
    void assignColis_WithMismatchedSpecialite_ShouldThrowBusinessException() {
        // Arrange
        testTransporteur.setSpecialite(Specialite.STANDARD); // Mismatch
        AssignColisRequest assignRequest = new AssignColisRequest("trans1");
        when(colisRepository.findById("1")).thenReturn(Optional.of(testColis));
        when(userRepository.findById("trans1")).thenReturn(Optional.of(testTransporteur));

        // Act & Assert
        assertThrows(BusinessException.class, () -> colisService.assignColis("1", assignRequest));
        verify(colisRepository, never()).save(any());
    }

    @Test
    void assignColis_WithInactiveTransporteur_ShouldThrowBusinessException() {
        // Arrange
        testTransporteur.setActive(false);
        AssignColisRequest assignRequest = new AssignColisRequest("trans1");
        when(colisRepository.findById("1")).thenReturn(Optional.of(testColis));
        when(userRepository.findById("trans1")).thenReturn(Optional.of(testTransporteur));

        // Act & Assert
        assertThrows(BusinessException.class, () -> colisService.assignColis("1", assignRequest));
        verify(colisRepository, never()).save(any());
    }

    @Test
    void assignColis_WithNonExistentColis_ShouldThrowResourceNotFoundException() {
        // Arrange
        AssignColisRequest assignRequest = new AssignColisRequest("trans1");
        when(colisRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> colisService.assignColis("1", assignRequest));
    }
}
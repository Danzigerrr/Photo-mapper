package backend.photomapper.Service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Repository.ImageInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ImageInfoServiceTest {

    @Mock
    private ImageInfoRepository imageInfoRepository;

    @InjectMocks
    private ImageInfoService imageInfoService;

    ImageInfo imageInfo;

    @BeforeEach
    public void setup(){

        this.imageInfo = ImageInfo.builder()
                .imageWidth(Integer.parseInt("3500 pixels".replaceAll("[^0-9]", "")))
                .imageHeight(Integer.parseInt("2000 pixels".replaceAll("[^0-9]", "")))
                .model("Realme 8")
                .dateTimeOriginal("2024:08:28 16:43:39")
                .timeZoneOriginal("-07:00")
                .gpsLatitudeRef("N")
                .gpsLatitude("36° 36' 24.36")
                .gpsLongitudeRef("W")
                .gpsLongitude("-117° 6' 54.44")
                .gpsAltitudeRef("Below sea level")
                .gpsAltitude("44.13 meters")
                .detectedFileTypeName("JPEG")
                .detectedFileTypeLongName("Joint Photographic Experts Group")
                .fileName("IMG20240828164339.jpg")
                .fileSize(4884167)
                .build();

    }

    @Test
    public void getImageInfoById_validId_returnsImageInfo() throws ChangeSetPersister.NotFoundException {
        // Arrange
        String imageInfoFileName = "IMG20240828164339.jpg";
        ImageInfo mockImageInfo = imageInfo;

        when(imageInfoRepository.findById(imageInfoFileName)).thenReturn(Optional.of(mockImageInfo));

        // Act
        Optional<ImageInfo> result = imageInfoService.getImageInfoById(imageInfoFileName);

        // Assert
        assertEquals(mockImageInfo, result.get());
        verify(imageInfoRepository).findById(imageInfoFileName);
    }

    @Test
    public void getImageInfoById_invalidId_throwsImageInfoNotFoundException() {
        // Arrange
        String imageInfoFileName = "20240828164339.png";
        when(imageInfoRepository.findById(imageInfoFileName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ChangeSetPersister.NotFoundException.class, () -> imageInfoService.getImageInfoById(imageInfoFileName));
        verify(imageInfoRepository).findById(imageInfoFileName);
    }


    @Test
    public void getAllImageInfos_returnsListOfImageInfos() {
        // Arrange
        List<ImageInfo> mockImageList = List.of(imageInfo, imageInfo);
        when(imageInfoRepository.findAll()).thenReturn(mockImageList);

        // Act
        List<ImageInfo> result = imageInfoService.getAllImageInfos();

        // Assert
        assertEquals(mockImageList, result);
        verify(imageInfoRepository).findAll();
    }

    @Test
    public void getAllImageInfos_returnsEmptyList_whenNoImageInfoFound() {
        // Arrange
        List<ImageInfo> mockImageList = List.of();
        when(imageInfoRepository.findAll()).thenReturn(mockImageList);

        // Act
        List<ImageInfo> result = imageInfoService.getAllImageInfos();

        // Assert
        assertEquals(mockImageList, result);
        verify(imageInfoRepository).findAll();
    }

    @Test
    public void deleteImageInfo_validId_deletesImageInfo() {
        // Arrange
        String imageInfoFileName = "IMG20240828164339.jpg";

        // Act
        imageInfoService.deleteImageInfo(imageInfoFileName);

        // Assert
        verify(imageInfoRepository).deleteById(imageInfoFileName);
    }

    @Test
    public void deleteImageInfo_nonExistentId_noExceptionThrown() {
        // Arrange
        String nonExistentFileName = "nonexistent.jpg";

        // Act
        imageInfoService.deleteImageInfo(nonExistentFileName);

        // Assert
        verify(imageInfoRepository).deleteById(nonExistentFileName); // Still called
    }

}

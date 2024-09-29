package backend.photomapper.Controller;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Service.ImageInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageInfoControllerTest {

    @InjectMocks
    private ImageInfoController imageInfoController;

    @Mock
    private ImageInfoService imageInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUploadImages_Success() throws IOException {
        // Arrange
        MultipartFile[] files = new MultipartFile[1]; // Mock your files as needed
        List<ImageInfo> imageInfos = new ArrayList<>();
        imageInfos.add(new ImageInfo()); // Add mock data as needed

        when(imageInfoService.processUploadedFiles(files)).thenReturn(imageInfos);

        // Act
        ResponseEntity<List<ImageInfo>> response = imageInfoController.uploadImages(files);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageInfos, response.getBody());
        verify(imageInfoService, times(1)).processUploadedFiles(files);
    }

    @Test
    public void testUploadImages_InternalServerError() throws IOException {
        // Arrange
        MultipartFile[] files = new MultipartFile[1]; // Mock your files as needed
        when(imageInfoService.processUploadedFiles(files)).thenThrow(new IOException());

        // Act
        ResponseEntity<List<ImageInfo>> response = imageInfoController.uploadImages(files);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(imageInfoService, times(1)).processUploadedFiles(files);
    }

    @Test
    public void testLoadImagesFromDirectory() {
        // Arrange
        String directoryPath = "some/directory/path";
        List<ImageInfo> imageInfos = new ArrayList<>();
        imageInfos.add(new ImageInfo()); // Add mock data as needed

        when(imageInfoService.loadImages(directoryPath)).thenReturn(imageInfos);

        // Act
        ResponseEntity<List<ImageInfo>> response = imageInfoController.loadImagesFromDirectory(directoryPath);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageInfos, response.getBody());
        verify(imageInfoService, times(1)).loadImages(directoryPath);
    }

    @Test
    public void testGetAllImageInfos() {
        // Arrange
        List<ImageInfo> imageInfos = new ArrayList<>();
        imageInfos.add(new ImageInfo()); // Add mock data as needed

        when(imageInfoService.getAllImageInfos()).thenReturn(imageInfos);

        // Act
        List<ImageInfo> response = imageInfoController.getAllImageInfos();

        // Assert
        assertEquals(imageInfos, response);
        verify(imageInfoService, times(1)).getAllImageInfos();
    }

    @Test
    public void testGetImageInfoById_Success() throws Exception {
        // Arrange
        String filename = "testImage.jpg";
        Optional<ImageInfo> imageInfo = Optional.of(new ImageInfo()); // Add mock data as needed

        when(imageInfoService.getImageInfoById(filename)).thenReturn(imageInfo);

        // Act
        ResponseEntity<Optional<ImageInfo>> response = imageInfoController.getImageInfoById(filename);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(imageInfo, response.getBody());
        verify(imageInfoService, times(1)).getImageInfoById(filename);
    }

    @Test
    public void testDeleteImageInfo() {
        // Arrange
        String filename = "testImage.jpg";

        // Act
        ResponseEntity<String> response = imageInfoController.deleteImageInfo(filename);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ImageInfo deleted successfully!.", response.getBody());
        verify(imageInfoService, times(1)).deleteImageInfo(filename);
    }
}

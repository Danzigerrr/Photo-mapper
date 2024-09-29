package backend.photomapper.Controller;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Service.ImageInfoService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loadimages")
public class ImageInfoController {

    private final ImageInfoService imageInfoService;

    public ImageInfoController(ImageInfoService imageInfoService) {
        this.imageInfoService = imageInfoService;
    }

    // Endpoint to handle multiple file uploads
    @PostMapping("/upload")
    public ResponseEntity<List<ImageInfo>> uploadImages(@RequestParam("files") MultipartFile[] files) {
        try {
            List<ImageInfo> imageInfos = imageInfoService.processUploadedFiles(files);
            return new ResponseEntity<>(imageInfos, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to handle a directory path sent from the frontend
    @PostMapping("/directory")
    public ResponseEntity<List<ImageInfo>> loadImagesFromDirectory(@RequestParam String directoryPath) {
        List<ImageInfo> imageInfos = imageInfoService.loadImages(directoryPath);
        return new ResponseEntity<>(imageInfos, HttpStatus.OK);
    }

    @GetMapping
    public List<ImageInfo> getAllImageInfos() {
        return imageInfoService.getAllImageInfos();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ImageInfo>> getImageInfoById(@PathVariable("id") String filename) throws ChangeSetPersister.NotFoundException {
        return new ResponseEntity<>(imageInfoService.getImageInfoById(filename), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImageInfo(@PathVariable("id") String filename) {
        imageInfoService.deleteImageInfo(filename);
        return new ResponseEntity<>("ImageInfo deleted successfully!.", HttpStatus.OK);
    }
}

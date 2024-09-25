package backend.photomapper.Controller;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Service.ImageInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loadimages")
public class ImageInfoController {
    
    private final ImageInfoService imageInfoService;

    public ImageInfoController(ImageInfoService imageInfoService) {
        this.imageInfoService = imageInfoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImageInfo createImageInfo(@RequestBody ImageInfo imageInfo){
        return imageInfoService.saveImageInfo(imageInfo);
    }
    
    @PostMapping("/load")
    public List<ImageInfo> loadImages(@RequestParam String directoryPath) {
        return imageInfoService.loadImages(directoryPath);
    }

    @GetMapping
    public List<ImageInfo> getAllImageInfos(){
        return imageInfoService.getAllImageInfos();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ImageInfo>> getImageInfoById(@PathVariable("id") String filename){
        return new ResponseEntity<>(imageInfoService.getImageInfoById(filename),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImageInfo(@PathVariable("id") String filename){
        imageInfoService.deleteImageInfo(filename);
        return new ResponseEntity<>("ImageInfo deleted successfully!.", HttpStatus.OK);

    }
}
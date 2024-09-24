package backend.photomapper.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loadimages")
public class ImageInfoController {
    
    private ImageInfoService imageInfoService;

    public ImageInfoController(ImageInfoService imageInfoService) {
        this.imageInfoService = imageInfoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImageInfo createImageInfo(@RequestBody ImageInfo imageInfo){
        return imageInfoService.saveImageInfo(imageInfo);
    }

    @GetMapping
    public List<ImageInfo> getAllImageInfos(){
        return imageInfoService.getAllImageInfos();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<ImageInfo>> getImageInfoById(@PathVariable("id") long id){
        return new ResponseEntity<Optional<ImageInfo>>(imageInfoService.getImageInfoById(id),HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ImageInfo> updateImageInfo(@PathVariable("id") long id,
                                                   @RequestBody ImageInfo imageInfo){
        return new ResponseEntity<ImageInfo>(imageInfoService.updateImageInfo(imageInfo,id),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteImageInfo(@PathVariable("id") long id){
        imageInfoService.deleteImageInfo(id);
        return new ResponseEntity<String>("ImageInfo deleted successfully!.", HttpStatus.OK);

    }
}
package backend.photomapper.Service;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Repository.ImageInfoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImageInfoService {


    private ImageInfoRepository imageInfoRepository;

    public ImageInfoService(ImageInfoRepository imageInfoRepository) {
        this.imageInfoRepository = imageInfoRepository;
    }

    public ImageInfo saveImageInfo(ImageInfo imageInfo) {

        Optional<ImageInfo> savedImageInfo = imageInfoRepository.findByFileName(imageInfo.getFileName());
        if(savedImageInfo.isPresent()){
            throw new RuntimeException("ImageInfo already exist with given filename:" + imageInfo.getFileName());
        }
        return imageInfoRepository.save(imageInfo);
    }

    public List<ImageInfo> getAllImageInfos() {
        return imageInfoRepository.findAll();
    }

    public Optional<ImageInfo> getImageInfoById(String filename) {
        return imageInfoRepository.findById(filename);
    }

    public void deleteImageInfo(String filename) {
        imageInfoRepository.deleteById(filename);
    }
}
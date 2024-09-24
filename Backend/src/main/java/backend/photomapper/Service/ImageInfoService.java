package backend.photomapper.Service;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Repository.ImageInfoRepository;
import backend.photomapper.imageloader.ImageMetadataExtractor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ImageInfoService {

    private final ImageInfoRepository imageInfoRepository;
    private final ImageMetadataExtractor imageMetadataExtractor;

    public ImageInfoService(ImageInfoRepository imageInfoRepository, ImageMetadataExtractor imageMetadataExtractor) {
        this.imageInfoRepository = imageInfoRepository;
        this.imageMetadataExtractor = imageMetadataExtractor;
    }

    public List<ImageInfo> loadImages(String directoryPath) {
        List<ImageInfo> images = imageMetadataExtractor.getImagesMetadata(directoryPath);

        imageInfoRepository.saveAll(images);

        return images;
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
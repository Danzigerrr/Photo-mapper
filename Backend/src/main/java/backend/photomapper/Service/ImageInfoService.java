package backend.photomapper.Service;

import backend.photomapper.Model.ImageInfo;
import backend.photomapper.Repository.ImageInfoRepository;
import backend.photomapper.Service.utils.ImageMetadataExtractor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageInfoService {

    private final ImageMetadataExtractor imageMetadataExtractor;
    private final ImageInfoRepository imageInfoRepository;

    public ImageInfoService(ImageMetadataExtractor imageMetadataExtractor, ImageInfoRepository imageInfoRepository) {
        this.imageMetadataExtractor = imageMetadataExtractor;
        this.imageInfoRepository = imageInfoRepository;
    }

    // Process uploaded files from the frontend
    public List<ImageInfo> processUploadedFiles(MultipartFile[] files) throws IOException {
        List<ImageInfo> imageInfos = new ArrayList<>();

        for (MultipartFile file : files) {
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);

            List<ImageInfo> extractedImages = imageMetadataExtractor.getImagesMetadata(convFile.getParent());
            imageInfos.addAll(extractedImages);

            imageInfoRepository.saveAll(extractedImages);
        }

        return imageInfos;
    }

    // Load images from a directory
    public List<ImageInfo> loadImages(String directoryPath) {
        List<ImageInfo> imageInfos = imageMetadataExtractor.getImagesMetadata(directoryPath);

        // Optionally save to the database
        imageInfoRepository.saveAll(imageInfos);

        return imageInfos;
    }

    public List<ImageInfo> getAllImageInfos() {
        return imageInfoRepository.findAll();
    }

    public Optional<ImageInfo> getImageInfoById(String filename) throws ChangeSetPersister.NotFoundException {
        return Optional.ofNullable(imageInfoRepository.findById(filename)
                .orElseThrow(ChangeSetPersister.NotFoundException::new));
    }


    public void deleteImageInfo(String filename) {
        imageInfoRepository.deleteById(filename);
    }
}
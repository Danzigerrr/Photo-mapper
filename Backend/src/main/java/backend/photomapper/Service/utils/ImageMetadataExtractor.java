package backend.photomapper.Service.utils;

import backend.photomapper.Model.ImageInfo;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class ImageMetadataExtractor {

    private final Set<String> selectedTags;
    private final Set<String> acceptedFileTypes;

    public ImageMetadataExtractor() {
        this.selectedTags = new HashSet<>();

        selectedTags.add("Image Width");
        selectedTags.add("Image Height");
        selectedTags.add("Model");
        selectedTags.add("Date/Time Original");
        selectedTags.add("Time Zone Original");
        selectedTags.add("GPS Latitude Ref");
        selectedTags.add("GPS Latitude");
        selectedTags.add("GPS Longitude Ref");
        selectedTags.add("GPS Longitude");
        selectedTags.add("GPS Altitude Ref");
        selectedTags.add("GPS Altitude");
        selectedTags.add("Detected File Type Name");
        selectedTags.add("Detected File Type Long Name");
        selectedTags.add("File Name");
        selectedTags.add("File Size");

        this.acceptedFileTypes = new HashSet<>();

        acceptedFileTypes.add("jpg");
        acceptedFileTypes.add("jpeg");
        acceptedFileTypes.add("png");
    }

    public ArrayList<ImageInfo> getImagesMetadata(String selectedDirectoryWithImages) {
        ArrayList<ImageInfo> images = new ArrayList<>();

        // Specify the directory path
        File directoryWithImages = new File(selectedDirectoryWithImages);

        // Check if it's a directory
        if (directoryWithImages.isDirectory()) {
            // Get all files in the directory
            File[] files = directoryWithImages.listFiles();

            if (files != null) {
                // Iterate through each file in the directory
                for (File file : files) {
                    if (file.isFile() && isImageFile(file)) {
                        try {
                            // Read metadata from the image file
                            Metadata metadata = ImageMetadataReader.readMetadata(file);
                            Map<String, String> tagMap = new HashMap<>();

                            // Iterate through metadata directories
                            for (Directory directoryMeta : metadata.getDirectories()) {
                                // Iterate through each tag in the directory
                                for (Tag tag : directoryMeta.getTags()) {
                                    // Only add if the tag name is in the selectedTags set
                                    if (selectedTags.contains(tag.getTagName())) {
                                        tagMap.put(tag.getTagName(), tag.getDescription());
                                    }
                                }
                            }

                            ImageInfo imageInfo = ImageInfo.builder()
                                    .imageWidth(Integer.parseInt(tagMap.get("Image Width")))
                                    .imageHeight(Integer.parseInt(tagMap.get("Image Height")))
                                    .model(tagMap.get("Model"))
                                    .dateTimeOriginal(tagMap.get("Date/Time Original"))
                                    .timeZoneOriginal(tagMap.get("Time Zone Original"))
                                    .gpsLatitudeRef(tagMap.get("GPS Latitude Ref"))
                                    .gpsLatitude(tagMap.get("GPS Latitude"))
                                    .gpsLongitudeRef(tagMap.get("GPS Longitude Ref"))
                                    .gpsLongitude(tagMap.get("GPS Longitude"))
                                    .gpsAltitudeRef(tagMap.get("GPS Altitude Ref"))
                                    .gpsAltitude(tagMap.get("GPS Altitude"))
                                    .detectedFileTypeName(tagMap.get("Detected File Type Name"))
                                    .detectedFileTypeLongName(tagMap.get("Detected File Type Long Name"))
                                    .fileName(file.getName())
                                    .fileSize(file.length())
                                    .build();

                            // Add the ImageInfo object to the list
                            images.add(imageInfo);

                        } catch (Exception e) {
                            System.err.println("Error reading metadata for " + file.getName() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }

        return images;
    }

    // Helper method to check if the file is an accepted image file
    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return acceptedFileTypes.stream().anyMatch(fileName::endsWith);
    }
}
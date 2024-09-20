package backend.photomapper.imageloader;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

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

    public void getImagesMetadata(String selectedDirectoryWithImages) {
        ImageMetadataExtractor extractor = new ImageMetadataExtractor();

        // Specify the directory path
        String workingDirectory = System.getProperty("user.dir");
        String imageDirectory = "/Backend/input_images";
        File directoryWithImages = new File(workingDirectory + imageDirectory);
//        File directoryWithImages = new File(selectedDirectoryWithImages); // TODO: use the selected directory by the user in the future

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
                            System.out.println("Metadata for file: " + file.getName());

                            // Iterate through metadata directories
                            for (Directory directoryMeta : metadata.getDirectories()) {
                                // Iterate through each tag in the directory
                                for (Tag tag : directoryMeta.getTags()) {
                                    // Only print if the tag name is in the selectedTags set
                                    if (extractor.selectedTags.contains(tag.getTagName())) {
                                        System.out.println(tag.getTagName() + ": " + tag.getDescription());
                                    }
                                }
                            }
                            System.out.println(); // Newline between different files
                        } catch (Exception e) {
                            System.err.println("Error reading metadata for " + file.getName() + ": " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    // Helper method to check if the file is an image
    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        for (String ext : acceptedFileTypes) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}

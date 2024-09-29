package backend.photomapper;

import backend.photomapper.Service.utils.ImageMetadataExtractor;
import backend.photomapper.Model.ImageInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class PhotoMapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoMapperApplication.class, args);

//		extractImageInfoExample(); // debug only
	}


	private static void extractImageInfoExample(){
		ImageMetadataExtractor imageMetadataExtractor = new ImageMetadataExtractor();

		String workingDirectory = System.getProperty("user.dir");
		String imageDirectory = "\\Backend\\input_images";
		System.out.println(workingDirectory + imageDirectory);
		ArrayList<ImageInfo> images;

		images = imageMetadataExtractor.getImagesMetadata(workingDirectory + imageDirectory);

		System.out.println(images.size());
	}
}

package backend.photomapper.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "images")
@NoArgsConstructor(force = true)
public class ImageInfo {

    @Id
    private final String fileName;

    private final int imageWidth;
    private final int imageHeight;
    private final String model;
    private final String dateTimeOriginal;
    private final String timeZoneOriginal;
    private final String gpsLatitudeRef;
    private final String gpsLatitude;
    private final String gpsLongitudeRef;
    private final String gpsLongitude;
    private final String gpsAltitudeRef;
    private final String gpsAltitude;
    private final String detectedFileTypeName;
    private final String detectedFileTypeLongName;
    private final long fileSize;
}
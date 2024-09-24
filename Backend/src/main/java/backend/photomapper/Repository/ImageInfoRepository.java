package backend.photomapper.Repository;

import backend.photomapper.Model.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, String> {
    Optional<ImageInfo> findByFileName(String filename);
}


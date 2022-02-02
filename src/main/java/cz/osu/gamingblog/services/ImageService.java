package cz.osu.gamingblog.services;

import cz.osu.gamingblog.exceptions.FileAlreadyExistsException;
import cz.osu.gamingblog.services.interfaces.IImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService implements IImageService {
    @Override
    public void saveThumbnail(String imageName, MultipartFile imageFile) {
        try {
            Path root = Paths.get("D:/GamingBlog/Images/thumbnails/");
            Path resolve = root.resolve(imageName);
            if (resolve.toFile().exists()) {
                throw new FileAlreadyExistsException("The file already exists: " + imageName);
            }

            Files.copy(imageFile.getInputStream(), resolve);
        } catch (Exception ex) {
            throw new IllegalStateException("Could not save the image file.", ex);
        }
    }
}

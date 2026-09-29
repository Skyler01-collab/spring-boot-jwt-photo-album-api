package com.example.springDemoWithRest.util.constants.AppUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class AppUtil {

    public static final String PHOTOS_FOLDER_NAME = "photos";
    public static final String THUMBNAIL_FOLDER_NAME = "thumbnail";
    public static final int THUMBNAIL_WIDTH = 300;

    public static String get_photo_upload_path(String fileName, long album_id, String folder) throws IOException {
        String path = "src\\main\\resources\\static\\uploads\\" + album_id + "\\" + folder;
        Files.createDirectories(Paths.get(path));   
        return new File(path).getAbsolutePath() + "\\" + fileName;
    }

    public static BufferedImage getThumbnail(MultipartFile originalFile, Integer width) throws IOException {
        BufferedImage thumbImg = null;
        BufferedImage img = ImageIO.read(originalFile.getInputStream());
        if (img != null) {
            thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, Scalr.OP_ANTIALIAS);
        }
        return thumbImg;
    }

    public static Resource getFileAsResource(long album_id, String folder_name, String file_name) throws IOException {
        String location = "src\\main\\resources\\static\\uploads\\" + album_id + "\\" + folder_name + "\\" + file_name;
        File file = new File(location);
        if (file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            return new UrlResource(path.toUri());
        } else {
            return null;
        }
    }

    public static boolean delete_photo_from_path(String fileName, String folder, long album_id) {
        try {
            Path path = Paths.get("src", "main", "resources", "static", "uploads", 
                                  String.valueOf(album_id), folder, fileName);
            return Files.deleteIfExists(path);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean delete_album_folder(long album_id) {
        try {
            Path albumFolderPath = Paths.get("src", "main", "resources", "static", "uploads", String.valueOf(album_id));
            if (Files.exists(albumFolderPath)) {
                return org.springframework.util.FileSystemUtils.deleteRecursively(albumFolderPath);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
package com.example.TTCN2.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class uploadFileService {
    // tham khảo cách upload file
    // https://www.youtube.com/watch?v=WFrczTORsWE
    // https://spring.io/guides/gs/uploading-files

    // nơi lưu
    private final Path rootLocation;

    public uploadFileService(){
        this.rootLocation = Paths.get("E:\\Documents\\SpringBoot\\TTCN2\\src\\main\\resources\\static\\imageTree");
    }

    public void store(MultipartFile file) {
        try {

            Path destinationFile = this.rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

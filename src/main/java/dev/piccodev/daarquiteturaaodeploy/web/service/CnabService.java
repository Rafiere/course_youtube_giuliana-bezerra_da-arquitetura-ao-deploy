package dev.piccodev.daarquiteturaaodeploy.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CnabService {

    private final Path fileStorageLocation;

    public CnabService(@Value("${file.upload-dir}") String fileUploadDir){
        this.fileStorageLocation = Paths.get(fileUploadDir);
    }

    public void uploadCnabFile(MultipartFile file) throws Exception {

        //The "tmp" director will be used to store the uploaded file temporarily.

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = fileStorageLocation.resolve(fileName);

        file.transferTo(targetLocation);

    }
}

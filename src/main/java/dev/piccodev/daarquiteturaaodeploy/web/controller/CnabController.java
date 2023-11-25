package dev.piccodev.daarquiteturaaodeploy.web.controller;

import dev.piccodev.daarquiteturaaodeploy.web.service.CnabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/cnab")
public class CnabController {

    private final CnabService cnabService;

    public CnabController(CnabService cnabService){
        this.cnabService = cnabService;
    }

    //This endpoint will be used to upload a CNAB file.
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) throws Exception {

        cnabService.uploadCnabFile(file);

        var response = Map.of("message", "Processing started in background");

        return ResponseEntity.status(200).body(response);
    }
}

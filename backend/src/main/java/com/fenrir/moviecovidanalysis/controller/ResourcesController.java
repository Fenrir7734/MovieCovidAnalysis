package com.fenrir.moviecovidanalysis.controller;

import com.fenrir.moviecovidanalysis.service.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/res")
public class ResourcesController {
    private ResourceService resourceService;

    @GetMapping(path = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> downloadFile(@RequestParam Optional<String> format) {
        try {
            File file = format.isPresent() && format.get().equals("xml")
                    ? resourceService.exportAsXML()
                    : resourceService.exportAsJSON();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @PostMapping("/import")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile files, @RequestParam Optional<String> format) {
        if (format.isPresent() && format.get().equals("xml")) {
            resourceService.importXML(files);
        } else {
            resourceService.importJSON(files);
        }
        return ResponseEntity.noContent().build();
    }
}

package com.example.service;

import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface FileUploadService {
    void saveFileToDatabase(UploadedFile file);
}

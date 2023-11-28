package com.example.service;

import com.example.model.File;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService{
    private EntityManager entityManager;
    @Transactional
    @Override
    public void saveFileToDatabase(UploadedFile file) {
        try {
            File fileEntity = new File();
            fileEntity.setFileName(file.getFileName());
            fileEntity.setContent(file.getContent());
            entityManager.persist(fileEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Your logic to save the file to the database goes here
        // You might want to use JDBC, JPA, Hibernate, or any other ORM framework

        // Example (JPA):
        // FileEntity fileEntity = new FileEntity();
        // fileEntity.setFileName(file.getFileName());
        // fileEntity.setFileContent(file.getContents());
        // entityManager.persist(fileEntity);
    }

}

package com.agubin.cards.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    boolean checkFileExist(String resType, Long id);

//    void storeFile(MultipartFile file, String resType, Long id) throws IOException;

//    byte[] retrieveFile(String resType, Long id) throws IOException;

    void writeDownFile(MultipartFile file, String resType, Long characterId);

    byte[] getFileById(String resType, Long characterId);

    void updateFile(MultipartFile file, String resType, Long characterId);
}

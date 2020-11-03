package com.agubin.cards.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileHandler {

//    String getPortraitFileName(Long characterId);

    boolean checkFileExist(String resType, Long id);

    void storeFile(MultipartFile file, String resType, Long id) throws IOException;

    byte[] retrieveFile(String resType, Long id) throws IOException;
}

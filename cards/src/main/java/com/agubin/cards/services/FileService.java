package com.agubin.cards.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean checkFileExist(String resType, Long id);

    void writeDownFile(MultipartFile file, String resType, Long characterId);

    byte[] getFileById(String resType, Long characterId);

    void updateFile(MultipartFile file, String resType, Long characterId);
}

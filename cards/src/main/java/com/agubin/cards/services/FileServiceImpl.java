package com.agubin.cards.services;

import com.agubin.cards.exceptions.*;
import com.agubin.cards.utils.ResourceTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileServiceImpl implements FileService {

    @Value("${files.path}")
    private String fileStorage;

    @Override
    public boolean checkFileExist(String resType, Long id) {
        System.out.println(getFullResourcePath(resType, id));
        return new File(getFullResourcePath(resType, id)).exists();
    }

    private String getFileNameFor(String resType, Long id) {
        switch (resType) {
            case ResourceTypes.CHR_IMG:
            case ResourceTypes.COM_IMG:
                return resType.toLowerCase() + "#" + id;
            default:
                return "non-existing-resource";
        }
    }

    private String getResourcePath(String resType) {
        switch (resType) {
            case ResourceTypes.CHR_IMG:
                return "ch_portraits/";
            case ResourceTypes.COM_IMG:
                return "comic_covers/";
            default:
                return "non-existing-path";
        }
    }

    private String getFullResourcePath(String resType, Long id) {
        return fileStorage + getResourcePath(resType) + getFileNameFor(resType, id);
    }
    private String getFullResourceDirPath(String resType) {
        return fileStorage + getResourcePath(resType);
    }

//    @Override
    private void storeFile(MultipartFile file, String resType, Long id) throws IOException {
        File resDirFile = new File(getFullResourceDirPath(resType));
        if (!resDirFile.exists() && !resDirFile.mkdirs()) {
            System.out.println("Creating directory on path: " + resDirFile.getAbsolutePath() + " failed!");
            throw new UnexpectedBehaviourException();
        }
        System.out.println(resDirFile.getAbsolutePath());
        BufferedOutputStream bous = new BufferedOutputStream(new FileOutputStream(new File(getFullResourcePath(resType, id))));
        bous.write(file.getBytes());
        bous.close();
    }

//    @Override
    private byte[] retrieveFile(String resType, Long id) throws IOException {
        return Files.readAllBytes(Paths.get(getFullResourcePath(resType, id)));
    }

    private void writeFile(MultipartFile file, String resType, Long resourceId) {
        if (file.isEmpty()) {
            throw new InvalidDataException(DataCorruptionTypes.EMPTY_FILE);
        }
        try {
            storeFile(file, resType, resourceId);
        } catch (IOException ex) {
            System.out.println(ex);
            throw new UnexpectedBehaviourException();
        }
    }

    private boolean characterPortraitExist(String resType, Long resourceId) {
        return checkFileExist(resType, resourceId);
    }

    @Override
    public void writeDownFile(MultipartFile file, String resType, Long resourceId) {
        if (characterPortraitExist(resType, resourceId)) {
            throw new ResourceAlreadyExistsException(resType, resourceId);
        }
        writeFile(file, resType, resourceId);
    }

    @Override
    public void updateFile(MultipartFile file, String resType, Long resourceId) {
        if (!characterPortraitExist(resType, resourceId)) {
            throw new ResourceNotFoundException(resType, resourceId);
        }
        writeFile(file, resType, resourceId);
    }

    @Override
    public byte[] getFileById(String resType, Long resourceId) {
        if (!characterPortraitExist(resType, resourceId)) {
            throw new ResourceNotFoundException(resType, resourceId);
        }
        byte[] bImage;
        try {
            bImage = retrieveFile(resType, resourceId);
        } catch (IOException ex) {
            System.out.println(ex);
            throw new UnexpectedBehaviourException();
        }
        if (bImage.length == 0) {
            throw new UnexpectedBehaviourException();
        }
        return bImage;
    }
}

package com.agubin.cards.utils;

import com.agubin.cards.exceptions.ResourceTypes;
import com.agubin.cards.exceptions.UnexpectedBehaviourException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileHandlerImpl implements FileHandler{

//    private static String fileStorage = "/var/fileStorage/";
//
//    public static String getPortraitFileName(Long characterId) {
//        return "character#" + characterId;
//    }
//
//    public static boolean checkFileExist(String resType, Long id) {
//        System.out.println(getFullResourcePath(resType, id));
//        return new File(getFullResourcePath(resType, id)).exists();
//    }
//
//    private static String getFileNameFor(String resType, Long id) {
//        switch (resType) {
//            case ResourceTypes.CHR_IMG:
//                return resType.toLowerCase() + "#" + id;
//            default:
//                return "non-existing-resource";
//        }
//    }
//
//    private static String getResourcePath(String resType) {
//        switch (resType) {
//            case ResourceTypes.CHR_IMG:
//                return "ch_portraits/";
//            default:
//                return "non-existing-path";
//        }
//    }
//
//    private static String getFullResourcePath(String resType, Long id) {
//        return fileStorage + getResourcePath(resType) + getFileNameFor(resType, id);
//    }
//    private static String getFullResourceDirPath(String resType) {
//        return fileStorage + getResourcePath(resType);
//    }
//
//    public static void storeFile(MultipartFile file, String resType, Long id) throws IOException {
//        File resDirFile = new File(getFullResourceDirPath(resType));
//        if (!resDirFile.exists() && !resDirFile.mkdir()) {
//            System.out.println("Creating directory on path: " + resDirFile.getAbsolutePath() + " failed!");
//            throw new UnexpectedBehaviourException();
//        }
//        System.out.println(resDirFile.getAbsolutePath());
//        BufferedOutputStream bous = new BufferedOutputStream(new FileOutputStream(new File(getFullResourcePath(resType, id))));
//        bous.write(file.getBytes());
//        bous.close();
//    }
//
//    public static byte[] retrieveFile(String resType, Long id) throws IOException {
//        return Files.readAllBytes(Paths.get(getFullResourcePath(resType, id)));
//    }

    /////////////////////

    @Value("${files.path}")
    private String fileStorage;

//    public String getPortraitFileName(Long characterId) {
//        return "character#" + characterId;
//    }

    public boolean checkFileExist(String resType, Long id) {
        System.out.println(getFullResourcePath(resType, id));
        return new File(getFullResourcePath(resType, id)).exists();
    }

    private String getFileNameFor(String resType, Long id) {
        switch (resType) {
            case ResourceTypes.CHR_IMG:
                return resType.toLowerCase() + "#" + id;
            default:
                return "non-existing-resource";
        }
    }

    private String getResourcePath(String resType) {
        switch (resType) {
            case ResourceTypes.CHR_IMG:
                return "ch_portraits/";
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

    public void storeFile(MultipartFile file, String resType, Long id) throws IOException {
        File resDirFile = new File(getFullResourceDirPath(resType));
        if (!resDirFile.exists() && !resDirFile.mkdir()) {
            System.out.println("Creating directory on path: " + resDirFile.getAbsolutePath() + " failed!");
            throw new UnexpectedBehaviourException();
        }
        System.out.println(resDirFile.getAbsolutePath());
        BufferedOutputStream bous = new BufferedOutputStream(new FileOutputStream(new File(getFullResourcePath(resType, id))));
        bous.write(file.getBytes());
        bous.close();
    }

    public byte[] retrieveFile(String resType, Long id) throws IOException {
        return Files.readAllBytes(Paths.get(getFullResourcePath(resType, id)));
    }
}

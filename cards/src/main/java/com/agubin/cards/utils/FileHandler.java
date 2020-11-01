package com.agubin.cards.utils;

import com.agubin.cards.exceptions.ResourceTypes;
import com.agubin.cards.exceptions.UnexpectedBehaviourException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileHandler {

    private static String imageStorage = "../imageStorage/";

    public static String getPortraitFileName(Long characterId) {
        return "character#" + characterId;
    }

    public static boolean checkFileExist(String resType, Long id) {
        return new File(getFullResourcePath(resType, id)).exists();
    }

    private static String getFileNameFor(String resType, Long id) {
        switch (resType) {
            case ResourceTypes.CHR_IMG:
                return resType.toLowerCase() + "#" + id;
            default:
                return "non-existing-resource";
        }
    }

    private static String getResourcePath(String resType) {
        switch (resType) {
            case ResourceTypes.CHR_IMG:
                return "ch_portraits/";
            default:
                return "non-existing-path";
        }
    }

    private static String getFullResourcePath(String resType, Long id) {
        return imageStorage + getResourcePath(resType) + getFileNameFor(resType, id);
    }
    private static String getFullResourceDirPath(String resType) {
        return imageStorage + getResourcePath(resType);
    }

    public static void storeFile(MultipartFile file, String resType, Long id) throws IOException {
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

    public static byte[] retrieveFile(String resType, Long id) throws IOException {
        return Files.readAllBytes(Paths.get(getFullResourcePath(resType, id)));
    }
}

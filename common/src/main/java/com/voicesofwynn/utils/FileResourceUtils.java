package com.voicesofwynn.utils;

import com.voicesofwynn.utils.file.ClasspathResourceFileResource;
import com.voicesofwynn.utils.file.FileResource;
import com.voicesofwynn.utils.file.URLClassLoaderFileResource;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileResourceUtils {

    public static Optional<File> getResourceAsFile(String resource) throws IOException {
        ClassLoader cl = FileResourceUtils.class.getClassLoader();
        File file;
        FileResource fileResource = new URLClassLoaderFileResource(cl, resource);
        try {
            file = fileResource.getFile();
        } catch (IOException e) {
            fileResource = new ClasspathResourceFileResource(cl, resource);
            file = fileResource.getFile();
        }
        return Optional.ofNullable(file);
    }

}

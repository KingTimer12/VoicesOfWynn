package com.voicesofwynn.utils.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClasspathResourceFileResource implements FileResource {

    private final ClassLoader cl;
    private final String resource;

    public ClasspathResourceFileResource(ClassLoader cl, String resource) {
        this.cl = cl;
        this.resource = resource;
    }

    @Override
    public File getFile() throws IOException {
        InputStream cpResource = cl.getResourceAsStream(resource);
        File tmpFile = File.createTempFile("file", "temp.yml");
        FileUtils.copyInputStreamToFile(cpResource, tmpFile);
        tmpFile.deleteOnExit();
        return tmpFile;
    }

}

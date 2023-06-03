package com.voicesofwynn.utils.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

public class URLClassLoaderFileResource implements FileResource {

    private final ClassLoader cl;
    private final String resource;

    public URLClassLoaderFileResource(ClassLoader cl, String resourcePath) {
        this.cl = cl;
        this.resource = resourcePath;
    }

    @Override
    public File getFile() throws IOException {
        File resourceFile = null;
        if (cl instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = URLClassLoader.class.cast(cl);
            URL resourceUrl = urlClassLoader.findResource(resource);
            if ("file".equals(resourceUrl.getProtocol())) {
                try {

                    URI uri = resourceUrl.toURI();
                    resourceFile = new File(uri);
                } catch (URISyntaxException e) {
                    IOException ioException = new IOException(
                            "Unable to get file through class loader: "
                                    + cl, e);
                    throw ioException;
                }

            }
        }
        if (resourceFile == null) {
            throw new IOException(
                    "Unable to get file through class loader: " + cl);
        }
        return resourceFile;
    }
}

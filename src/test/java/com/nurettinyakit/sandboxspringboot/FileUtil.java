package com.nurettinyakit.sandboxspringboot;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileUtil {

    public static String readBodyFromFile(final String fileName) throws IOException {
        final File file = ResourceUtils.getFile(CLASSPATH_URL_PREFIX + fileName);
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

}

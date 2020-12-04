package common;

import y2020.Day1;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadInputData {

    public static String read(String resource) {
        StringBuilder contentBuilder = new StringBuilder();
        URL sources = ReadInputData.class.getClassLoader().getResource(resource);
        assert sources != null;
        try (Stream<String> stream = Files.lines(Paths.get(sources.getFile()), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }
}

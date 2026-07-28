package com.reshmah.incident.ai;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;

@Service
public class LogService {

    @SneakyThrows
    public String readLogsFromCloudWatch(String environment, String service) {

        String fileName = String.format(
                "/%s/%s.log",
                environment.toLowerCase(),
                service.toLowerCase()
        );

        ClassPathResource resource =
                new ClassPathResource(fileName);

        return Files.readString(resource.getFile().toPath());
    }
}

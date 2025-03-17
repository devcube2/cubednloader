package cubednloader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cube-downloader")
@Data
public class CubeDownloaderProperties {
    private Settings settings;

    @Data
    public static class Settings {
        private String bin;
    }
}

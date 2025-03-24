package cubednloader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cube-downloader")
@Data
public class CubeDownloaderProperties {
    private Settings settings;
    private Minio minio;

    @Data
    public static class Settings {
        private String bin;
        private String downloadPath;
    }

    @Data
    public static class Minio {
        private String server;
        private String user;
        private String password;
        private String bucket;
    }
}

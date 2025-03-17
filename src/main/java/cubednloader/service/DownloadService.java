package cubednloader.service;

import cubednloader.config.CubeDownloaderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadService {
    private final CubeDownloaderProperties cubeDownloaderProperties;

    @Autowired
    public DownloadService(CubeDownloaderProperties cubeDownloaderProperties) {
        this.cubeDownloaderProperties = cubeDownloaderProperties;
    }

    public void downloadFile() {
        System.out.println("downloader: " + cubeDownloaderProperties.getSettings().getBin());
    }
}

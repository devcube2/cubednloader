package cubednloader.service;

import cubednloader.config.CubeDownloaderProperties;
import cubednloader.external.MinioOperator;
import cubednloader.external.ProcessExecutor;
import cubednloader.model.dto.DownloadInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class DownloadService {
    private final CubeDownloaderProperties cubeDownloaderProperties;
    private final ProcessExecutor processExecutor;
    private final MinioOperator minioOperator;

    public DownloadService(CubeDownloaderProperties cubeDownloaderProperties, ProcessExecutor processExecutor, MinioOperator minioOperator) {
        this.cubeDownloaderProperties = cubeDownloaderProperties;
        this.processExecutor = processExecutor;
        this.minioOperator = minioOperator;
    }

    @Async
    private CompletableFuture<Integer> downloadContent(DownloadInfoDto dto) {
        String binPath = cubeDownloaderProperties.getSettings().getBin();
        String outputPath = String.format("\"%s/%%(title)s.%%(ext)s\" %s", cubeDownloaderProperties.getSettings().getDownloadPath(), dto.getUrl());

        int ret = processExecutor.runProcess(binPath, "-f", "bestvideo[ext=mp4]+bestaudio[ext=m4a]/best[ext=mp4]", "--write-thumbnail", "-o", outputPath);

        log.debug("runProcess exit: {}", ret);
        System.out.println("ret = " + ret);

        log.debug("test: {}", outputPath);

//        minioOperator.uploadFile(cubeDownloaderProperties.getMinio().getBucket(), "power.mp4", outputPath);

        return CompletableFuture.completedFuture(ret);
    }

    public ResponseEntity<String> getPresignedURL(DownloadInfoDto dto) {
        // 1. yt-dlp 사용하여 다운로드
        CompletableFuture<Integer> result = downloadContent(dto);

        // 비동기 작업이 완료된 후의 처리 방법
        result.thenAccept(ret -> {
            log.info("Download completed with result: {}", ret);
            // 추가적인 처리
        });

        return new ResponseEntity<>("Test Presigned URL", HttpStatus.OK);
    }
}

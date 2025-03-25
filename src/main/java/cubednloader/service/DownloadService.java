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
    private String downloadContent(DownloadInfoDto dto) {
        String presignedURL = "";

        String URL = dto.getUrl();

        String outputPath = String.format("\"%s/%%(title)s.%%(ext)s\" \"%s\"", cubeDownloaderProperties.getSettings().getDownloadPath(), URL);
        if (minioOperator.isObjectExists(outputPath)) {
            // minio 에 이미 존재함. 다운로드 받지 않아도 됨.
            System.out.println("객체 존재함..");
        } else { // minio 에 존재하지 않으므로 직접 다운로드하고, minio 에 저장해야됨.
            // yt-dlp 바이너리 경로 가져오기
            String binPath = cubeDownloaderProperties.getSettings().getBin();

            // yt-dlp 이용하여 다운로드
            int ret = processExecutor.runProcess(binPath, "-f", "bestvideo+bestaudio", "--write-thumbnail", "-o", outputPath);
            if (ret != 0) { // yt-dlp 이 비정상 종료
                return "";
            }

            if (!minioOperator.uploadObject(URL, URL)) {
                return "";
            }
        }

//        minioOperator.isObjectExists(cubeDownloaderProperties.getMinio().getBucket(), "power.mp4", outputPath);

        return presignedURL;
    }

    public ResponseEntity<String> getPresignedURL(DownloadInfoDto dto) {
        String presignedURL = downloadContent(dto);
        if (presignedURL.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(presignedURL);
    }
}

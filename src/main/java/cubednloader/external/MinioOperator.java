package cubednloader.external;

import cubednloader.config.CubeDownloaderProperties;
//import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class MinioOperator {
    private final MinioClient minioClient;
    private final CubeDownloaderProperties cubeDownloaderProperties;

    public MinioOperator(CubeDownloaderProperties cubeDownloaderProperties) {
        this.cubeDownloaderProperties = cubeDownloaderProperties;
        this.minioClient = MinioClient.builder()
                .endpoint(cubeDownloaderProperties.getMinio().getServer())
                .credentials(cubeDownloaderProperties.getMinio().getUser(), cubeDownloaderProperties.getMinio().getPassword())
                .build();
    }

    // 객체 확인
    public boolean isObjectExists(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(cubeDownloaderProperties.getMinio().getBucket())
                            .object(objectName)
                            .build()
            );
        } catch (MinioException e) {
            return false; // 파일이 존재하지 않음
        } catch (Exception e) {
            log.error(e.getMessage());
            return false; // 기타 예외 발생 시 존재하지 않는 것으로 처리
        }
        return true; // 파일이 존재함
    }

    // 객체 업로드
    public boolean uploadObject(String objectName, String filePath) {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(cubeDownloaderProperties.getMinio().getBucket())
                            .object(objectName)
                            .filename(filePath)
                            .build());
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    // 객체 다운로드
//    public void downloadFile(String objectName, String downloadPath) {
//        try {
//            minioClient.downloadObject(
//                    DownloadObjectArgs.builder()
//                            .bucket(cubeDownloaderProperties.getMinio().getBucket())
//                            .object(objectName)
//                            .filename(downloadPath)
//                            .build());
//        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
//            log.error(e.getMessage());
//        }
//    }
}

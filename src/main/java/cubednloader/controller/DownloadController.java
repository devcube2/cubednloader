package cubednloader.controller;

import cubednloader.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    DownloadService downloadService;

    @GetMapping("")
    public ResponseEntity<Resource> downloadFile(@RequestParam String filename) throws IOException {
        try {
            System.out.println("DownloadController.downloadFile");
            System.out.println("filename = " + filename);

            // 1. 한글 파일명을 UTF-8 디코딩
            String decodedFilename = java.net.URLDecoder.decode(filename, StandardCharsets.UTF_8);

            // 2. 실제 파일 경로 설정
            Path filePath = Paths.get("uploads/").resolve(decodedFilename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                System.err.println(filePath);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 3. 브라우저 호환을 위한 UTF-8 파일명 인코딩
            String encodedFilename = URLEncoder.encode(decodedFilename, StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20"); // 공백 처리

            // 4. HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"; filename*=UTF-8''" + encodedFilename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }
}
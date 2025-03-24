package cubednloader.controller;

import cubednloader.model.dto.DownloadInfoDto;
import cubednloader.service.DownloadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/download")
public class DownloadController {
    DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("")
    public ResponseEntity<String> getDownloadURL(@ModelAttribute DownloadInfoDto dto) throws InterruptedException {
        return downloadService.getPresignedURL(dto);
    }
}
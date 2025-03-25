package cubednloader.controller;

import cubednloader.model.dto.DownloadInfoDto;
import cubednloader.service.DownloadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/download")
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("")
    public ResponseEntity<String> getDownloadURL(@Valid @ModelAttribute DownloadInfoDto dto) throws InterruptedException {
        return downloadService.getPresignedURL(dto);
    }
}
package cubednloader.controller;

import cubednloader.model.dto.DownloadInfoDto;
import cubednloader.service.DownloadService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/download")
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping("")
    public ResponseEntity<String> getDownloadURL(HttpServletRequest request, @Valid @ModelAttribute DownloadInfoDto dto) throws InterruptedException {
        return downloadService.getPresignedURL(dto);
    }
}

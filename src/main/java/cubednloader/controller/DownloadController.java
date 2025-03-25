package cubednloader.controller;

import cubednloader.model.dto.DownloadInfoDto;
import cubednloader.service.DownloadService;
import cubednloader.util.URLValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/download")
public class DownloadController {
    private final DownloadService downloadService;
    private final URLValidator urlValidator;

    public DownloadController(DownloadService downloadService, URLValidator urlValidator) {
        this.downloadService = downloadService;
        this.urlValidator = urlValidator;
    }

    @GetMapping("")
    public ResponseEntity<String> getDownloadURL(@Valid @ModelAttribute DownloadInfoDto dto) throws InterruptedException {
        System.out.println("dto.getUrl() = " + dto.getUrl());
//        if ( !urlValidator.isValidURL(dto.getUrl()) ) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비정상 URL");
//        }

        return downloadService.getPresignedURL(dto);
    }
}
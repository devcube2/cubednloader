package cubednloader.controller;

import cubednloader.model.dto.DownloadInfoDto;
import cubednloader.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/download")
public class DownloadController {
    @Autowired
    DownloadService downloadService;

    @PostMapping("")
    public ResponseEntity<String> downloadFile(@RequestBody DownloadInfoDto dto) {
//        String outputDirectory = "Z:/";  // 다운로드한 비디오를 저장할 디렉토리 경로
//
//        // yt-dlp 명령어 생성
//        String command = String.format("Z:/yt-dlp.exe -f bestvideo+bestaudio/best --merge-output-format mp4 -o \"%s/%%(title)s.%%(ext)s\" %s", outputDirectory, dto.getUrl());
//
//        // yt-dlp -f bestvideo+bestaudio/best --merge-output-format mp4 <URL>
//
//        System.out.println("command = " + command);
//
//        // ProcessBuilder를 사용하여 외부 명령어 실행
//        ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
//        processBuilder.redirectErrorStream(true); // 오류 메시지를 표준 출력으로 리디렉션
//
//        try {
//            // 프로세스 실행
//            Process process = processBuilder.start();
//
//            // 출력 결과를 읽어오는 BufferedReader
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//
//            // 명령어의 출력 결과를 콘솔에 출력
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // 프로세스 종료 대기
//            int exitCode = process.waitFor();
//            if (exitCode == 0) {
//                return new ResponseEntity<>("다운로드 완료", HttpStatus.OK);
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        downloadService.downloadFile();

        return new ResponseEntity<>("다운로드 완료", HttpStatus.OK);
    }
}
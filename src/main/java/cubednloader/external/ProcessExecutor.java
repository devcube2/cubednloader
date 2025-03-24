package cubednloader.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class ProcessExecutor {
    private String getBufferedReader(BufferedReader reader) {
        StringBuffer strBuf = new StringBuffer();

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return strBuf.toString();
    }

    private void printBufferedReader(BufferedReader reader, Consumer<String> logMethod) {
        reader.lines().forEach(logMethod);
    }

    public int runProcess(String binPath, String... args) {
        try {
            // 실행 커맨드 문자열 조합
            List<String> command = new ArrayList<>();
            command.add(binPath);
            command.addAll(Arrays.asList(args));

            ProcessBuilder builder = new ProcessBuilder(command);

            Process process = builder.start();

            // 커맨드 실행결과 출력
            String successResult = getBufferedReader(new BufferedReader(new InputStreamReader(process.getInputStream())));
            printBufferedReader(new BufferedReader(new InputStreamReader(process.getErrorStream())), log::error);

            log.debug("success result: {}", successResult);

            Pattern pattern = Pattern.compile("([A-Za-z0-9_ -]+\\.mp4)");
            Matcher matcher = pattern.matcher(successResult);

            while (matcher.find()) {
                System.out.println("MP4 File Path: " + matcher.group(1));
            }

            // 커맨드 실행후 대기
            int ret = process.waitFor();

            return ret;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return -1;
    }
}
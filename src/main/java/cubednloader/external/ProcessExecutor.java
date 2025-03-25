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

            // 커맨드 실행후, 버퍼를 읽어주지 않으면 waitFor 리턴이 되지 않는다. 반드시 있어야 되는 코드들이다.
            printBufferedReader(new BufferedReader(new InputStreamReader(process.getInputStream())), log::debug);
            printBufferedReader(new BufferedReader(new InputStreamReader(process.getErrorStream())), log::error);

            return process.waitFor();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return -1;
    }
}
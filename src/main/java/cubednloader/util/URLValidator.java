package cubednloader.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class URLValidator {
    private static final String URL_PATTERN = "^(https?)://[^\s/$.?#].[^\s]*$"; // HTTP, HTTPS URL 형식

    public boolean isValidURL(String url) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        return pattern.matcher(url).matches();
    }
}

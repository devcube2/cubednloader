package cubednloader.model.dto;

import lombok.Data;

@Data
public class DownloadInfoDto {
    private String url;

    public void setUrl(String url) {
        // URL이 이미 큰따옴표로 감싸져 있지 않으면 감싸기
        if (url != null && !(url.startsWith("\"") && url.endsWith("\""))) {
            this.url = "\"" + url + "\"";
        } else {
            this.url = url;
        }
    }
}

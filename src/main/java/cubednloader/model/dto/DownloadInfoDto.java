package cubednloader.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DownloadInfoDto {
    @NotNull(message = "URL 이 NULL 입니다.")
    @Pattern(regexp = "^(https?)://[^\s/$.?#].[^\s]*$", message = "유효한 URL 형식이 아닙니다.")
    private String url;
}

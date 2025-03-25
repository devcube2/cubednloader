package cubednloader.model.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class DownloadInfoDto {
    @NotNull(message = "URL은 필수 항목입니다.")
    @Pattern(regexp = "^(https?)://[^\s/$.?#].[^\s]*$", message = "유효한 URL 형식이 아닙니다.")
    private String url;
}

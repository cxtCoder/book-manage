package pers.cxt.bms.server.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Configuration
@Data
public class ImgConfig {
    @Value("${image.oriFileUrl}")
    private String oriFileUrl;

    @Value("${image.oriFileSavePath}")
    private String oriFileSavePath;

    @Value("${image.thumbFileSavePath}")
    private String thumbFileSavePath;

    @Value("${image.thumbPrefix}")
    private String thumbPrefix;

    @Value("${image.thumbSuffix}")
    private String thumbSuffix;

    @Value("${image.thumbImageHeight}")
    private Integer thumbImageHeight;
}

package pers.cxt.bms.eureka;

import com.hundsun.jrescloud.common.boot.CloudApplication;
import com.hundsun.jrescloud.common.boot.CloudBootstrap;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@CloudApplication
@EnableEurekaServer
public class EurekaStarter {
    public static void main(String[] args) {
        CloudBootstrap.run(EurekaStarter.class, args);
    }
}

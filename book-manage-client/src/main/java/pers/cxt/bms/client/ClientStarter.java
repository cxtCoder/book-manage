package pers.cxt.bms.client;

import com.hundsun.jrescloud.common.boot.CloudApplication;
import com.hundsun.jrescloud.common.boot.CloudBootstrap;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@CloudApplication
@EnableDiscoveryClient
@EnableSwagger2
//@EnableAspectJAutoProxy
public class ClientStarter {
    public static void main(String[] args) {
        CloudBootstrap.run(ClientStarter.class, args);
    }
}

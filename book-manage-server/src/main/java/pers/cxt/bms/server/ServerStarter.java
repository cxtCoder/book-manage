package pers.cxt.bms.server;

import com.hundsun.jrescloud.common.boot.CloudApplication;
import com.hundsun.jrescloud.common.boot.CloudBootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@CloudApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan(value = {"pers.cxt.bms.database.dao"})
public class ServerStarter {
    public static void main(String[] args) {
        CloudBootstrap.run(ServerStarter.class, args);
    }
}

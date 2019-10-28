package com.ants.background.provider;

import com.alibaba.dubbo.container.Main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
//开启熔断器功能
@EnableHystrix
@SpringBootApplication
public class BackgroundProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackgroundProviderApplication.class, args);
        // 启动 Provider 容器，注意这里的 Main 是 com.alibaba.dubbo.container 包下的
        Main.main(args);
    }

}

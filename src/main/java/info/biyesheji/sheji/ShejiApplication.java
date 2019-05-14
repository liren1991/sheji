package info.biyesheji.sheji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "info.biyesheji.sheji.mapper")
public class ShejiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShejiApplication.class, args);
    }

}

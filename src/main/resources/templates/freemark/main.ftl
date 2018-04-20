package ${packageName};

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "${packageName}.**.dao")
public class ${capitalProjectName}Application extends SpringBootServletInitializer {

    public static void main( String[] args ) {
        SpringApplication.run(${capitalProjectName}Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(${capitalProjectName}Application.class);
    }

}

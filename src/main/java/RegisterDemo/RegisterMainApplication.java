package RegisterDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//来标注一个主程序，说明这是一个springboot应用
public class RegisterMainApplication {
    public static void main(String[] args) {

        SpringApplication.run(RegisterMainApplication.class,args);  //Spring 应用启动起来
    }
}

package tracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableScheduling // スケジューリングを有効化
public class BlueskyTrackerApplication {
    public static void main (String[] args){
        SpringApplication.run(BlueskyTrackerApplication.class,args);
    }

    @Bean
    public RestClient restClient() {
        // ここで RestClient のインスタンスを作成し、Springに管理させます
        return RestClient.create();
    }
    
}

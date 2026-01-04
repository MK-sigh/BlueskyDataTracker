package tracker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // スケジューリングを有効化
public class BlueskyTrackerApplication {
    public void main (String[] args){
        SpringApplication.run(BlueskyTrackerApplication.class,args);
    }
    
}

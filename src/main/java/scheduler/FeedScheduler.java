package scheduler;

import java.io.IOException;

import client.BlueskyApiClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import processor.BlueskyDataProcessor;

@Service
public class FeedScheduler {
private final BlueskyApiClient apiClient;
    private final BlueskyDataProcessor dataProcessor;

    public FeedScheduler(BlueskyApiClient apiClient, BlueskyDataProcessor dataProcessor) {
        this.apiClient = apiClient;
        this.dataProcessor = dataProcessor;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchAndProcessFeed(){
        try{
            String jsonstr = apiClient.fetchFeedJson();
            dataProcessor.processFeed(jsonstr);
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}

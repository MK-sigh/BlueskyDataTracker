package tracker.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tracker.client.BlueskyApiClient;
import tracker.processor.BlueskyDataProcessor;

@Service
public class FeedScheduler {
    private final BlueskyApiClient apiClient;
    private final BlueskyDataProcessor dataProcessor;
    private String cursor;

    public FeedScheduler(BlueskyApiClient apiClient, BlueskyDataProcessor dataProcessor) {
        this.apiClient = apiClient;
        this.dataProcessor = dataProcessor;
        this.cursor = null;
    }

    @Scheduled(fixedRate = 300000)
    public void fetchAndProcessFeed(){
        String query = "#エンジニア";
        String jsonstr = apiClient.fetchFeedJson(query, cursor);
        cursor = dataProcessor.processFeed(jsonstr, query); // ★cursorを返す

    }

}

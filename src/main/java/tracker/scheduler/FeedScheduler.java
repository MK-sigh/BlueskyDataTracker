package tracker.scheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tracker.DAO.SearchWordRepository;
import tracker.client.BlueskyApiClient;
import tracker.model.SearchWord;
import tracker.processor.BlueskyDataProcessor;

@Service
public class FeedScheduler {
    private final BlueskyApiClient apiClient;
    private final BlueskyDataProcessor dataProcessor;
    private final SearchWordRepository searchWordRepository; // 追加
    private String cursor;

    public FeedScheduler(BlueskyApiClient apiClient, BlueskyDataProcessor dataProcessor,
        SearchWordRepository searchWordRepository) {
        this.apiClient = apiClient;
        this.dataProcessor = dataProcessor;
        this.searchWordRepository = searchWordRepository;
        this.cursor = null;
    }

    // キーワードごとにカーソルを管理するためのMap（任意）
    private final Map<String, String> cursorMap = new HashMap<>();

    @Scheduled(fixedRate = 300000)
    public void fetchAndProcessFeed() {
        // 1. DBからすべての検索ワードを取得
        List<SearchWord> searchWords = searchWordRepository.findAll();

        if (searchWords.isEmpty()) {
            System.out.println("追跡中の検索ワードがありません。");
            return;
        }

        for (SearchWord sw : searchWords) {
            String query = sw.getWord();
            // まだ「#」がついていない場合に備えて調整（必要に応じて）
            String searchQuery = query.startsWith("#") ? query : "#" + query;

            System.out.println("定期収集実行中: " + searchQuery);

            // 2. 各ワードのカーソルを取得（なければnull）
            String currentCursor = cursorMap.get(searchQuery);

            // 3. API実行
            String jsonstr = apiClient.fetchFeedJson(searchQuery, currentCursor);

            // 4. 保存処理とカーソルの更新
            String nextCursor = dataProcessor.processFeed(jsonstr, searchQuery);
            
            if (nextCursor != null) {
                cursorMap.put(searchQuery, nextCursor);
            }
        }
    }
}
//     public void fetchAndProcessFeed(){
//         String query = "#エンジニア";
//         String jsonstr = apiClient.fetchFeedJson(query, cursor);
//         cursor = dataProcessor.processFeed(jsonstr, query); // ★cursorを返す

//     }

// }

package tracker.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import tracker.model.SearchWord;

public interface SearchWordRepository extends JpaRepository<SearchWord, Integer> {
}

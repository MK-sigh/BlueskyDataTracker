package tracker.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.SearchWords;

@Repository
public interface SearchWordsDAO extends JpaRepository< SearchWords, Integer>{
    Optional<SearchWords> findByWord(String word);
}
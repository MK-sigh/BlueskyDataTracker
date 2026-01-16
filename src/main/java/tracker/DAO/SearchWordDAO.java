package tracker.DAO;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tracker.model.SearchWord;

@Repository
public interface SearchWordDAO extends JpaRepository< SearchWord, Integer>{
    Optional<SearchWord> findByWord(String word);
}
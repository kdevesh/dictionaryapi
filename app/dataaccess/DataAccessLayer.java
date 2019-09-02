package dataaccess;

import models.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DataAccessLayer {
    final static private Logger log = LoggerFactory.getLogger(DataAccessLayer.class);
    public static boolean saveWords(Set<String> set) {
        log.info("inside saveWords() of DataAccessLayer");
        List<Dictionary> words = set.stream().map(Dictionary::new).collect(Collectors.toList());
        try {
            words.stream().forEach(word -> {
                if (!searchWord(word.getWord()))
                    word.save();
            });
            return true;
        } catch (Exception e) {
            log.error(e.getClass().descriptorString());
            log.error(e.getMessage());
            return false;
        }
    }

    public static boolean searchWord(String word) {
        Dictionary res = Dictionary.find.query().where().eq("word", word).findOne();
        return res!=null;
    }
}

package businesslayer;

import dataaccess.DataAccessLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BusinessLayer {
    final static private Logger log = LoggerFactory.getLogger(BusinessLayer.class);
    public static boolean saveWord(File file) throws IOException {
        log.info("inside saveWord() of BusinessLayer");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Set<String> set = new HashSet<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] words = line.split("[^a-zA-Z0-9]");
            for (String word : words) {
                if (!word.equals(""))
                    set.add(word.toLowerCase());
            }
        }
        return DataAccessLayer.saveWords(set);
    }

    public static boolean searchWord(String word) {
        return DataAccessLayer.searchWord(word.toLowerCase());
    }
}

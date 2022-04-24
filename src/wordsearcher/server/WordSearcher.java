package wordsearcher.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordSearcher {
    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                line = line.toUpperCase();
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<Integer> findIndices(String word, List<String> lines) {
        List<Integer> indices = new ArrayList<Integer>();
        word = word.toUpperCase();
        for (int i = 0; i < lines.size(); i++) {
            String str = lines.get(i);
            if (str.contains(word)) {
                indices.add(i);
            }
        }
        return indices;
    }
}

package com.Clinacuity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map.Entry;
// https://medium.com/swlh/building-a-spelling-correction-system-with-soundex-and-levenshtein-distance-3d6aaad38297
public class MetaphoneDriver {
    public static MultiMap<String, String> dict = new MultiMap<>();
    public static Metaphone3 m3 = new Metaphone3();

    public static void populateDict(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = null;

            while ((line = br.readLine()) != null) {
                m3.SetWord(line);
                m3.Encode();
                String Metaphone3 = m3.GetMetaph();
                dict.put(Metaphone3, line);
            }
        }
    }
    public void setup(String word){
        try {
            populateDict("/Users/noushin/codes/OneWayHashing/dict.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // search for homophones
        m3.SetWord(word);
        m3.Encode();
        Collection<String> collection = dict.get(m3.GetMetaph());
        MultiMap<Integer, String> sortedWords = new MultiMap();

        // pruning results with Levenshtein Distance
        for (String str: collection) {
            int distance = Levenshtein.distance(word, str);

            if (distance<3) {
                sortedWords.put(distance, str);
            }
        }

        System.out.println("Word mispelled: " + word);
        System.out.println("Matching words: ");

        for (Entry<Integer, Collection<String>> entry: sortedWords.entrySet()) {
            int value = entry.getKey();

            for (String str: entry.getValue()) {
                System.out.println(str + " - " + value);
            }
        }
    }
}

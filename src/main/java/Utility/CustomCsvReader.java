package Utility;

import interview.dao.Localizable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomCsvReader {

    public static <T extends Localizable> List<T> read(String path, String separator, Class<T> type) {
        List<T> result = new ArrayList<>();
        String line;
        String[] labels = null;
        String cvsSplitBy = separator;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = br.readLine()) != null) {
                if (labels == null || labels.length == 0){
                    labels = line.split(cvsSplitBy);
                    continue;
                }

                String[] row = line.split(cvsSplitBy);
                Map<String, String> langLabels = new HashMap<>();
                for (int i=1; i<labels.length; i++){
                    if (row.length >= i) {
                        langLabels.put(labels[i], row[i].replaceAll("^\"|\"$", ""));
                    }else{
                        langLabels.put(labels[i], ""); //we could add line for localized errors on missing entries
                    }
                }
                T t = type.newInstance();
                t.setCode(row[0]);
                t.setDescriptions(langLabels);
                result.add(t);
            }

        } catch (IOException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static <T extends Localizable> List<T> read(String path, Class<T> type){
        return read(path, ";", type);
    }

}

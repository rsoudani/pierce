package Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import interview.dao.Localizable;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CustomCsvReader {
    private static final Logger log = LoggerFactory.getLogger(CustomCsvReader.class);

    public static List<Map<String, String>> read(File file) throws JsonProcessingException, IOException {
        List<Map<String, String>> response = new LinkedList<Map<String, String>>();
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(';');
        MappingIterator<Map<String, String>> iterator = mapper.readerFor(Map.class)
                .with(schema)
                .readValues(file);
        while (iterator.hasNext()) {
            response.add(iterator.next());
        }
        return response;
    }

    public static Map<String, String> readToMap(String path, String separator) {
        Map<String, String> result = new HashMap<>();
        String line;
        String[] labels = null;
        String cvsSplitBy = separator;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = StringEscapeUtils.unescapeHtml4(br.readLine())) != null) {

                String[] row = line.split(cvsSplitBy);
                result = new HashMap<>();
                for (int i=1; i<labels.length; i++){
                    if (row.length >= i) {
                        result.put(labels[i], row[i].replaceAll("^\"|\"$", ""));
                    }else{
                        result.put(labels[i], ""); //we could add line for localized errors on missing entries
                    }
                }
            }

        } catch (IOException e) {
            log.error("Error occurred during parsing scv file: " + e.getLocalizedMessage());
        }
        return result;

    }

    public static <T extends Localizable> List<T> read(String path, String separator, Class<T> type) {
        List<T> result = new ArrayList<>();
        String line;
        String[] labels = null;
        String cvsSplitBy = separator;

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            while ((line = StringEscapeUtils.unescapeHtml4(br.readLine())) != null) {

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
            log.error("");
        }
        return result;

    }

    public static <T extends Localizable> List<T> read(String path, Class<T> type){
        return read(path, ";", type);
    }

}

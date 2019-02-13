package interview.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute implements Localizable {

    private static final Logger log = LoggerFactory.getLogger(Attribute.class);
    private static final String LABEL_PATTERN = "^label\\-.*";
    private static final String CODE_PATTERN = "code";

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String code;

    @ElementCollection
    private Map<String, String> labels = new HashMap<>();

    public Attribute() {}

    public Attribute(String code, Map label) {
        this.code = code;
        this.labels = label;
    }

    public Attribute(Map<String, String> values){
        String key;
        String value;
        for (Map.Entry<String,String> entry : values.entrySet()){
            key = entry.getKey();
            value = entry.getValue();
            if (key.equals(CODE_PATTERN)){
                this.code = value;
            }else if(key.matches(LABEL_PATTERN)){
                labels.put(key, value);
            }else{
                log.warn(String.format("Attribute header unknown: %s with value of: %s", key, value));
            }
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, code, labels);
    }

    @Override
    public void setDescriptions(Map descriptions) {
        setLabels(descriptions);
    }
}

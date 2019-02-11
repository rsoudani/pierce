package interview.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Option implements Localizable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String code;

    @ElementCollection
    private Map<String, String> labels = new HashMap<>();

    public Option() {}

    public Option(String code, Map label) {
        this.code = code;
        this.labels = label;
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

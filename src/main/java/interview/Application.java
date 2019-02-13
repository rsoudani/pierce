package interview;

import Utility.CustomCsvReader;
import interview.dao.Attribute;
import interview.dao.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner loadData(AttributeRepository attributeRepository, OptionRepository optionRepository) {
        return args -> {
            //we could use projections to create custom views
            List<Attribute> attributes = new ArrayList<>();
            File attributesFile = new File("src/resources/attributes.csv");
            for (Map attributeEntry : CustomCsvReader.read(attributesFile)){
                attributes.add(new Attribute(attributeEntry));
            }
            List<Option> options = CustomCsvReader.read("src/resources/options.csv", Option.class);
            attributeRepository.saveAll(attributes);
            optionRepository.saveAll(options);
        };
    }

}

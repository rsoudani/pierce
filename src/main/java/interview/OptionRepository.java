package interview;

import interview.dao.Attribute;
import interview.dao.Option;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Map;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface OptionRepository extends PagingAndSortingRepository<Option, Map> {

}

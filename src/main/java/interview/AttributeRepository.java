package interview;

import interview.dao.Attribute;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Map;

//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface AttributeRepository extends PagingAndSortingRepository<Attribute, Map> {

}

package uk.ac.ebi.pride.mongodb.archive.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFile;

/**
 * Custom Repository that allows customized search on the MongoDB.
 *
 * @author ypriverol
 */
public interface PrideFileMongoRepositoryCustom {

    Page<PrideFile> filterByAttributes(MultiValueMap<String, String> filters, Pageable page);
}

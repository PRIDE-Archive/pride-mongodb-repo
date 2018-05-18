package uk.ac.ebi.pride.mongodb.archive.repo;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.mongodb.archive.model.MongoPrideFile;

import java.util.List;

/**
 * Custom Repository that allows customized search on the MongoDB.
 *
 * @author ypriverol
 */
public interface PrideFileMongoRepositoryCustom {

    Page<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page);
}

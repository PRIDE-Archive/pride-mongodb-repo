package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideFile;

import java.util.List;

/**
 * Custom Repository that allows customized search on the MongoDB.
 *
 * @author ypriverol
 */
public interface PrideFileMongoRepositoryCustom {

    Page<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page);

    List<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters);
}

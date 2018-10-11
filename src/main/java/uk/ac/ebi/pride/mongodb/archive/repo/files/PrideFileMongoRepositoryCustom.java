package uk.ac.ebi.pride.mongodb.archive.repo.files;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideMSRun;

import java.util.List;

/**
 * Custom Repository that allows customized search on the MongoDB.
 *
 * @author ypriverol
 */
public interface PrideFileMongoRepositoryCustom {

    Page<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page);

    List<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters);

    List<MongoPrideMSRun> filterMSRunByProjectAccession(String projectAccession);

    List<MongoPrideFile> findByProjectAccessions(List<String> accessions);
}

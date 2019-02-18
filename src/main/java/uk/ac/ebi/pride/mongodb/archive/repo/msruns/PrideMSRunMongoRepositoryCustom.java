package uk.ac.ebi.pride.mongodb.archive.repo.msruns;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.msrun.MongoPrideMSRun;

import java.util.List;
import java.util.Optional;

/**
 * Custom Repository that allows customized search on the MongoDB.
 *
 * @author ypriverol
 */
public interface PrideMSRunMongoRepositoryCustom {

    Page<MongoPrideMSRun> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page);

    List<MongoPrideMSRun> filterByAttributes(List<Triple<String, String, String>> filters);

    List<MongoPrideMSRun> filterMSRunByProjectAccession(String projectAccession);

    List<MongoPrideMSRun> findByProjectAccessions(List<String> accessions);

    Optional<MongoPrideMSRun> findMsRunByAccession(String accession);
}

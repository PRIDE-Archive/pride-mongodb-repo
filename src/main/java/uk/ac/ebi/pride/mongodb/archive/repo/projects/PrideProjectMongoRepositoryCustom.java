package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;

import java.util.List;


public interface PrideProjectMongoRepositoryCustom {

    /*
    sample_attributes = species
    ptmList = modifications
    */
    Page<MongoPrideProject> findByMultipleAttributes(Pageable page, String[] accessions, String[] sample_attributes,
                                                     String[] instruments, String contact, String ptmList,
                                                     String[] publications, String[] keywords);

    List<String> getAllProjectAccessions();

}

package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoImportedProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ImportedProjectMongoRepositoryCustomImpl implements ImportedProjectMongoRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public ImportedProjectMongoRepositoryCustomImpl(@Qualifier("archiveMongoTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /*
     sample_attributes = species
     ptmList = modifications
    */
    //TODO : this function is not used for now. But it might be useful for future
    @Override
    public Page<MongoImportedProject> findByMultipleAttributes(Pageable page, String[] accessions, String[] sampleAttributes,
                                                               String[] instruments, String contact,
                                                               String ptmList, String[] publications,
                                                               String[] keywords) {

        Query query = new Query();
        List<Criteria> criteriaList = new ArrayList<>();
        if (accessions != null) {
            String regExStr = String.join("|", accessions);
            criteriaList.add(Criteria.where(PrideArchiveField.ACCESSION).regex(regExStr));
        }
        if (sampleAttributes != null) {
            String regExStr = String.join("|", sampleAttributes);
            criteriaList.add(Criteria.where(PrideArchiveField.SAMPLE_ATTRIBUTES_NAMES).regex(regExStr));
        }

        query.addCriteria(new Criteria().orOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));

        List<MongoImportedProject> mongoImportedProjects = mongoTemplate.find(query, MongoImportedProject.class);

        return PageableExecutionUtils.getPage(mongoImportedProjects, page, () -> mongoTemplate.count(query, MongoImportedProject.class));
    }

    @Override
    public Set<String> getAllProjectAccessions() {

        Set<String> projectAccessions = mongoTemplate.getCollection(PrideArchiveField.IMPORTED_PROJECTS_COLLECTION_NAME)
                .distinct(PrideArchiveField.ACCESSION, String.class).into(new HashSet<>());

        return projectAccessions;
    }
}

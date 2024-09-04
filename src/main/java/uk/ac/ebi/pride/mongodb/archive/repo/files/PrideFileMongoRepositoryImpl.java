package uk.ac.ebi.pride.mongodb.archive.repo.files;

import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ypriverol
 */
@ComponentScan(basePackages = {"uk.ac.ebi.pride.mongodb.configs"})
public class PrideFileMongoRepositoryImpl implements PrideFileMongoRepositoryCustom {

    MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("archiveMongoTemplate")
    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }


    MongoOperations mongoOperations;

    @Autowired
    @Qualifier("archiveMongoTemplate")
    public void setMongoOperations(MongoTemplate mongoTemplate) {
        this.mongoOperations = mongoTemplate;
    }

    @Override
    public Page<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
//        Criteria queryCriteria = PrideMongoUtils.buildCriteria(filters);
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
//        if(queryCriteria!=null){
//            queryMongo.addCriteria(queryCriteria);
//        }
        queryMongo.with(page);
        List<MongoPrideFile> files = mongoTemplate.find(queryMongo, MongoPrideFile.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, MongoPrideFile.class));
    }

    @Override
    public List<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters) {
        // Criteria queryCriteria = PrideMongoUtils.buildCriteria(filters);
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
        return mongoTemplate.find(queryMongo, MongoPrideFile.class);
    }

    @Override
    public List<MongoPrideFile> findByProjectAccessions(List<String> accessions) {
        Criteria criteria = new Criteria(PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS).in(accessions);
        Query queryMongo = new Query().addCriteria(criteria);
        return mongoTemplate.find(queryMongo, MongoPrideFile.class);
    }

    @Override
    public List<String> findProjectAccessionsWhereChecksumIsNull() {

        return mongoTemplate.getCollection(PrideArchiveField.PRIDE_FILE_COLLECTION_NAME)
                .distinct(PrideArchiveField.ACCESSION, Filters.exists(PrideArchiveField.CHECKSUM, false), String.class).into(new ArrayList<>());
    }
}

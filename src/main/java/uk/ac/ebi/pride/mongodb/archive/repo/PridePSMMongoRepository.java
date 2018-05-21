package uk.ac.ebi.pride.mongodb.archive.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideMongoPSM;

/**
 *
 * @author ypriverol
 */
public interface PridePSMMongoRepository extends MongoRepository<PrideMongoPSM, ObjectId>, PridePSMMongoRepositoryCustom {

}

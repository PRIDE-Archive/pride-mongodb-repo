package uk.ac.ebi.pride.mongodb.spectral.repo.protein;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.pride.mongodb.spectral.model.protein.PrideMongoProtein;


public interface PrideProteinMongoRepository extends MongoRepository<PrideMongoProtein, ObjectId>, PrideProteinMongoRepositoryCustom {

}

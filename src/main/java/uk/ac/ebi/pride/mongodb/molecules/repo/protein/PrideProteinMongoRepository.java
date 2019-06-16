package uk.ac.ebi.pride.mongodb.molecules.repo.protein;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.molecules.model.protein.PrideMongoProteinEvidence;

@Repository
public interface PrideProteinMongoRepository extends MongoRepository<PrideMongoProteinEvidence, ObjectId>, PrideProteinMongoRepositoryCustom {

}

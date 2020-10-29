package uk.ac.ebi.pride.mongodb.archive.repo.sdrf;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.sdrf.MongoPrideSdrf;

@Repository
public interface PrideSdrfMongoRepository extends MongoRepository<MongoPrideSdrf, ObjectId> {

    MongoPrideSdrf findMongoPrideSdrfByProjectAccession(String accession);

}

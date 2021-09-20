package uk.ac.ebi.pride.mongodb.archive.repo.stats;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.stats.MongoPeptidomeStats;


@Repository
public interface PeptidomeStatsMongoRepository extends MongoRepository<MongoPeptidomeStats, ObjectId> {

}

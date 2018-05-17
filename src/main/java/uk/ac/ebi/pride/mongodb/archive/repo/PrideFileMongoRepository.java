package uk.ac.ebi.pride.mongodb.archive.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Optional;

/**
 * This repository allows to perform CRUD operations in File Collection.
 *
 * @author ypriverol
 */
@Repository
public interface PrideFileMongoRepository extends MongoRepository<MongoPrideFile, ObjectId> , PrideFileMongoRepositoryCustom {

    @Override
    <S extends MongoPrideFile> S save(S s);

    @Query("{'"+ PrideArchiveField.ACCESSION + "' : ?0}")
    Optional<MongoPrideFile> findPrideFileByAccession(String accession);
}

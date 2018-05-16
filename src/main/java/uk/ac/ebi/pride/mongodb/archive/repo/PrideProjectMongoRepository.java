package uk.ac.ebi.pride.mongodb.archive.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchive;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.List;
import java.util.Optional;

/**
 * This class handle the Repository of PRIDE Projects in Mongo. This included functions to Save/Update/Delete/Find Pride Archive Projects.
 *
 * @author ypriverol
 */

@Repository
public interface PrideProjectMongoRepository extends MongoRepository<PrideArchive, ObjectId>{

    @Override
    <S extends PrideArchive> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends PrideArchive> S save(S s);

    @Override
    Optional<PrideArchive> findById(ObjectId objectId);

    @Query("{'"+ PrideArchiveField.ACCESSION + "' : ?0}")
    Optional<PrideArchive> findByAccession(String accession);
}

package uk.ac.ebi.pride.mongodb.archive.repo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uk.ac.ebi.pride.mongodb.archive.model.PrideProject;
import uk.ac.ebi.pride.mongodb.archive.model.PrideProjectField;

import java.util.List;
import java.util.Optional;

/**
 * This class handle the Repository of PRIDE Projects in Mongo. This included functions to Save/Update/Delete/Find Pride Archive Projects.
 *
 * @author ypriverol
 */
public interface PrideProjectMongoRepository extends MongoRepository<PrideProject, ObjectId>{

    @Override
    <S extends PrideProject> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends PrideProject> S save(S s);

    @Override
    Optional<PrideProject> findById(ObjectId objectId);

    @Query(PrideProjectField.ACCESSION + ":?0")
    Optional<PrideProject> findByAccession(String accession);
}

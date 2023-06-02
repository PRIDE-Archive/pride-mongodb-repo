package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoImportedProject;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;

import java.util.List;
import java.util.Optional;

/**
 * This class handle the Repository of PRIDE Projects in Mongo. This included functions to Save/Update/Delete/Find Pride Archive Projects.
 *
 * @author ypriverol
 */

@Repository
public interface ImportedProjectMongoRepository extends MongoRepository<MongoImportedProject, ObjectId>, ImportedProjectMongoRepositoryCustom {

    @Override
    <S extends MongoImportedProject> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends MongoImportedProject> S save(S s);

    @Override
    Optional<MongoImportedProject> findById(ObjectId objectId);

    @Query("{'" + PrideArchiveField.ACCESSION + "' : ?0}")
    Optional<MongoImportedProject> findByAccession(String accession);

    @Query("{'" + PrideArchiveField.ACCESSION + "': {'$in' : ?0}}")
    List<MongoImportedProject> findByMultipleAccessions(List<String> accessions);
}

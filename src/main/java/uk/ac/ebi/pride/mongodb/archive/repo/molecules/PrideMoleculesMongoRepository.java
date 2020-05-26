package uk.ac.ebi.pride.mongodb.archive.repo.molecules;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.molecules.MongoPrideMolecules;

import java.util.Optional;

/**
 * This repository allows to perform CRUD operations in Molecules Collection.
 *
 */
@Repository
public interface PrideMoleculesMongoRepository extends MongoRepository<MongoPrideMolecules, ObjectId> {

    @Override
    <S extends MongoPrideMolecules> S save(S s);

    @Query("{'" + PrideArchiveField.EXTERNAL_PROJECT_ACCESSION + "' : ?0}")
    Optional<MongoPrideMolecules> findMoleculesByProjectAccession(String projectAccession);
}

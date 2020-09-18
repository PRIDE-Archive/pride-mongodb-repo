package uk.ac.ebi.pride.mongodb.archive.service.molecules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.pride.mongodb.archive.model.molecules.MongoPrideMolecules;
import uk.ac.ebi.pride.mongodb.archive.repo.molecules.PrideMoleculesMongoRepository;

import java.util.Optional;

@Slf4j
public class PrideMoleculesMongoService {

    final PrideMoleculesMongoRepository repository;

    @Autowired
    public PrideMoleculesMongoService(PrideMoleculesMongoRepository repository) {
        this.repository = repository;
    }

    /**
     * Insert an {@link MongoPrideMolecules} in to the PRIDE MongoDB
     *
     * @param mongoPrideMolecules {@link MongoPrideMolecules}
     * @return MongoPrideMolecules
     */
    public MongoPrideMolecules insert(MongoPrideMolecules mongoPrideMolecules) {
        return repository.save(mongoPrideMolecules);
    }

    public MongoPrideMolecules upsert(MongoPrideMolecules mongoPrideMolecules) {
        Optional<MongoPrideMolecules> optionalMongoPrideMolecules = repository.findMoleculesByProjectAccession(mongoPrideMolecules.getProjectAccession());
        optionalMongoPrideMolecules.ifPresent(prideMolecules -> mongoPrideMolecules.setId(prideMolecules.getId()));
        MongoPrideMolecules save = repository.save(mongoPrideMolecules);
        log.info("MongoPrideMolecules has been Inserted or updated in MongoDB with projectAccession -- " + mongoPrideMolecules.getProjectAccession());
        return save;
    }

    /**
     * Find MongoPrideMolecules by Project Accession.
     *
     * @param projectAccession Project Accession
     * @return Return MongoPrideMolecules
     */
    public Optional<MongoPrideMolecules> findMoleculesByProjectAccession(String projectAccession) {
        return repository.findMoleculesByProjectAccession(projectAccession);
    }
}

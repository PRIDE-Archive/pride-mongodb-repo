package uk.ac.ebi.pride.mongodb.archive.service.sdrf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.sdrf.MongoPrideSdrf;
import uk.ac.ebi.pride.mongodb.archive.repo.sdrf.PrideSdrfMongoRepository;

/**
 * The {@link PrideSdrfMongoService} is used to perform operations into Mongo Database for Pride Archive Projects.
 *
 * @author ypriverol
 */
@Service
@Slf4j
public class PrideSdrfMongoService {

    final PrideSdrfMongoRepository repository;

    public PrideSdrfMongoService(PrideSdrfMongoRepository repository) {
        this.repository = repository;
    }

    public MongoPrideSdrf saveSdrf(MongoPrideSdrf mongoPrideSdrf){
        return repository.save(mongoPrideSdrf);
    }

    public MongoPrideSdrf findByProjectAccession(String projectAccession){
        return repository.findMongoPrideSdrfByProjectAccession(projectAccession);
    }
}

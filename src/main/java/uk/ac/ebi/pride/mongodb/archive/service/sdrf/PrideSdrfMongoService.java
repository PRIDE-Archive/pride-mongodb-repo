package uk.ac.ebi.pride.mongodb.archive.service.sdrf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.sdrf.MongoPrideSdrf;
import uk.ac.ebi.pride.mongodb.archive.repo.sdrf.PrideSdrfMongoRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public MongoPrideSdrf saveSdrf(MongoPrideSdrf mongoPrideSdrf) {
        return repository.save(mongoPrideSdrf);
    }

    public void saveSdrfList(List<MongoPrideSdrf> mongoPrideSdrfList) {
        repository.saveAll(mongoPrideSdrfList);
    }

    public List<MongoPrideSdrf> findByProjectAccession(String projectAccession) {
        return repository.findByProjectAccession(projectAccession);
    }

    public Set<String> getUniqueFileChecksumsOfProject(String projectAccession) {
        List<MongoPrideSdrf> mongoPrideSdrfList = repository.findByProjectAccession(projectAccession);
        return mongoPrideSdrfList.stream()
                .map(mongoPrideSdrf -> mongoPrideSdrf.getFilechecksum()).collect(Collectors.toSet());
    }
}

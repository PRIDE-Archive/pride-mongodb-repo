package uk.ac.ebi.pride.mongodb.archive.service.stats;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.stats.MongoPeptidomeStats;
import uk.ac.ebi.pride.mongodb.archive.repo.stats.PeptidomeStatsMongoRepository;

import java.util.List;

@Service
@Slf4j
public class PeptidomeStatsMongoService {

    final PeptidomeStatsMongoRepository repository;

    public PeptidomeStatsMongoService(PeptidomeStatsMongoRepository repository) {
        this.repository = repository;
    }

    public List<MongoPeptidomeStats> findall() {
        List<MongoPeptidomeStats> all = repository.findAll();
        return all;
    }

}

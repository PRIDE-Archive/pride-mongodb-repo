package uk.ac.ebi.pride.mongodb.archive.service;

import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import uk.ac.ebi.pride.mongodb.archive.model.PrideMongoPSM;
import uk.ac.ebi.pride.mongodb.archive.repo.PridePSMMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.List;

/**
 *
 * @author ypriverol
 */
public class PridePSMMongoService {

    final PridePSMMongoRepository psmMongoRepository;

    @Autowired
    private MongoOperations mongo;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PridePSMMongoService.class);

    @Autowired
    public PridePSMMongoService(PridePSMMongoRepository fileRepository) {
        this.psmMongoRepository = fileRepository;
    }

    /**
     * This functions allows to find all the PSMs for an specific project Accession
     * @param projectAccession Project Accession
     * @param page Page to be retrirve
     * @return List of PSMs
     */
    public Page<PrideMongoPSM> findPSMsByProjectAccession(String projectAccession, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("externalProjectAccession=in=" + projectAccession);
        return psmMongoRepository.filterByAttributes(filters, page);
    }

    /**
     * This functions allows to find all the PSMs for an specific project Accession
     * @param analysisAccession Analysis Accession
     * @param page Page to be retrirve
     * @return List of PSMs
     */
    public Page<PrideMongoPSM> findPSMsByAnalysisAccession(String analysisAccession, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("externalAnalysisAccession=in=" + analysisAccession);
        return psmMongoRepository.filterByAttributes(filters, page);
    }
}

package uk.ac.ebi.pride.mongodb.spectral.service.protein;

import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.spectral.model.protein.PrideMongoProtein;
import uk.ac.ebi.pride.mongodb.spectral.repo.protein.PrideProteinMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.List;

/**
 * This class {@link Service} control how we read/update/store {@link uk.ac.ebi.pride.archive.dataprovider.data.protein.ProteinDetailProvider} into Archive Mongo
 * database.
 *
 * @author ypriverol
 */

@Service
public class PrideProteinMongoService {

    final PrideProteinMongoRepository proteinMongoRepository;

    @Autowired
    private MongoOperations mongo;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideProteinMongoService.class);

    @Autowired
    public PrideProteinMongoService(PrideProteinMongoRepository proteinRepository) {
        this.proteinMongoRepository = proteinRepository;
    }

    /**
     * This functions allows to find all the PSMs for an specific project Accession
     * @param projectAccession Project Accession
     * @param page Page to be retrirve
     * @return List of PSMs
     */
    public Page<PrideMongoProtein> findProteinsByProjectAccession(String projectAccession, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccession=in=" + projectAccession);
        Page<PrideMongoProtein> proteins =  proteinMongoRepository.filterByAttributes(filters, page);
        LOGGER.debug("The number of Proteins for the Project Accession -- " + projectAccession + " -- "+ proteins.getTotalElements());
        return proteins;
    }

    /**
     * Save an specific Protein in MongoDB
     * @param protein {@link PrideMongoProtein}
     */
    public void save(PrideMongoProtein protein){
        proteinMongoRepository.save(protein);
    }

    /**
     * Delete all the PSMs in the Mongo Database
     */
    public void deleteAll() {
        proteinMongoRepository.deleteAll();
    }

    /**
     * Search Proteins by specific properties in the filter Query.
     * @param filterQuery Query properties
     * @param page Page to be retrieved
     * @return Page containing the {@link PrideMongoProtein}.
     */
    public Page<PrideMongoProtein> searchProteins(String filterQuery, Pageable page) {
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filterQuery);
        return proteinMongoRepository.filterByAttributes(filters, page);

    }
}

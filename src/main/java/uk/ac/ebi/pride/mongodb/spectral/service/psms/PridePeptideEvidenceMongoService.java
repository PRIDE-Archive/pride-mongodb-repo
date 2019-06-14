package uk.ac.ebi.pride.mongodb.spectral.service.psms;

import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.spectral.model.peptide.PrideMongoPeptideEvidence;
import uk.ac.ebi.pride.mongodb.spectral.repo.peptide.PridePeptideEvidenceMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author ypriverol
 */

@Service
public class PridePeptideEvidenceMongoService {

    final PridePeptideEvidenceMongoRepository psmMongoRepository;

    @Autowired
    private MongoOperations mongo;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PridePeptideEvidenceMongoService.class);

    @Autowired
    public PridePeptideEvidenceMongoService(PridePeptideEvidenceMongoRepository fileRepository) {
        this.psmMongoRepository = fileRepository;
    }

    /**
     * This functions allows to find all the PSMs for an specific project Accession
     * @param projectAccession Project Accession
     * @param page Page to be retrirve
     * @return List of PSMs
     */
    public Page<PrideMongoPeptideEvidence> findPSMsByProjectAccession(String projectAccession, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccession=in=" + projectAccession);
        Page<PrideMongoPeptideEvidence> psms =  psmMongoRepository.filterByAttributes(filters, page);
        LOGGER.debug("The number of PSMs for the Project Accession -- " + projectAccession + " -- "+ psms.getTotalElements());
        return psms;
    }

    /**
     * This functions allows to find all the PSMs for an specific project Accession
     * @param analysisAccession Analysis Accession
     * @param page Page to be retrieve
     * @return List of PSMs
     */
    public Page<PrideMongoPeptideEvidence> findPSMsByAnalysisAccession(String analysisAccession, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("analysisAccession=in=" + analysisAccession);
        Page<PrideMongoPeptideEvidence> psms = psmMongoRepository.filterByAttributes(filters, page);
        LOGGER.debug("The number of PSMs for the Analysis Accession -- " + analysisAccession + " -- "+ psms.getTotalElements());
        return psms;
    }

    /**
     * Finds a PSM by an Accession .
     *
     * @param accession Accession
     * @return a PSM corresponding to the provided ID.
     */
    public PrideMongoPeptideEvidence findByAccession(String accession) {
        return psmMongoRepository.findByAccession(accession).orElse(null);
    }

    /**
     * Finds a list of PSMs in a collection of IDs.
     *
     * @param accessions a collection of ID to search for
     * @return a list of PSMs corresponding to the provided IDs.
     */
    public List<PrideMongoPeptideEvidence> findByIdIn(Collection<String> accessions) {
        return psmMongoRepository.findByIdAccessions(accessions, new Sort(Sort.Direction.DESC, PrideArchiveField.PEPTIDE_SEQUENCE));
    }

    /**
     * A sorted list of PSMs in a collection of IDs.
     *
     * @param ids a collection of ID to search for
     * @param sort how the result should be sorted
     * @return a list of PSMs corresponding to the provided IDs.
     */
    public List<PrideMongoPeptideEvidence> findByIdIn(Collection<String> ids, Sort sort) {
        return psmMongoRepository.findByIdAccessions(ids, sort);
    }


    /**
     * Counts how many PSMs are for a project accession.
     *
     * @param projectAccession the project accession to search for
     * @return the number of PSMs corresponding to the provided project accession
     */
    public long countByProjectAccession(String projectAccession) {
        return findPSMsByProjectAccession(projectAccession, PageRequest.of(0,10)).getTotalElements();
    }

    /**
     * Counts how many PSMs are for a assay accession and assay accession.
     *
     * @param assayAccession the assay accession to search for
     * @return the number of PSMs corresponding to the provided project accession
     */
    public long countByAssayAccession(String assayAccession) {
        return findPSMsByProjectAccession(assayAccession, PageRequest.of(0,10)).getTotalElements();
    }

    /**
     * Save an specific PSM in MongoDB
     * @param psm {@link PrideMongoPeptideEvidence}
     */
    public void save(PrideMongoPeptideEvidence psm){
        psmMongoRepository.save(psm);
    }

    /**
     * Delete all the PSMs in the Mongo Database
     */
    public void deleteAll() {
        psmMongoRepository.deleteAll();
    }

    public Page<PrideMongoPeptideEvidence> searchPSMs(String filterQuery, Pageable page) {
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filterQuery);
        return psmMongoRepository.filterByAttributes(filters, page);

    }
}

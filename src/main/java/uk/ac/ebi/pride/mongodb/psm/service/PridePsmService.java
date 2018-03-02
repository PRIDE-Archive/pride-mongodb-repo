package uk.ac.ebi.pride.mongodb.psm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Indexes PSMs into Mongo.
 */
@Service
public class PridePsmService {

  private static Logger logger = LoggerFactory.getLogger(PridePsmService.class.getName());

  @Resource
  private MongoPsmRepository mongoPsmRepository;

  /**
   * Initializes the service.
   */
  public PridePsmService() {
  }

  /**
   * Sets the Mongo repository.
   * @param mongoPsmRepository the Mongo PSM repository.
   */
  public void setMongoPsmRepository(MongoPsmRepository mongoPsmRepository) {
    this.mongoPsmRepository = mongoPsmRepository;
  }

  /**
   * Saves a PSM to Mongo.
   * @param psm the PSM to save.
   */
  @Transactional
  public void save(MongoPsm psm) {
    mongoPsmRepository.save(psm);
  }

  /**
   * Saves PSMs to Mongo.
   * @param psms the PSMs to save.
   */
  @Transactional
  public void save(Iterable<MongoPsm> psms) {
    if (psms==null || !psms.iterator().hasNext())
      logger.debug("No PSMs to save");
    else {
      if (logger.isDebugEnabled()) {
        debugSavePsm(psms);
      }
      mongoPsmRepository.save(psms);
    }
  }

  /**
   * Output debug information related to PSMs.
   * @param psms PSMs to debug
   */
  private void debugSavePsm(Iterable<MongoPsm> psms) {
    int i = 0;
    for (MongoPsm psm : psms) {
      logger.debug("Saving PSM " + i + " with ID: " + psm.getId());
      logger.debug("Project accession: " + psm.getProjectAccession());
      logger.debug("Assay accession: " + psm.getAssayAccession());
      logger.debug("Peptide sequence: " + psm.getPeptideSequence());
      logger.debug("Spectrum id: " + psm.getSpectrumId());
      i++;
    }
  }

  /**
   * Deletes a PSM from Mongo.
   * @param psm the PSM to delete
   */
  @Transactional
  public void delete(MongoPsm psm){
    mongoPsmRepository.delete(psm);
  }

  /**
   * Deletes PSMs from Mongo.
   * @param psms the PSMs to delete
   */
  @Transactional
  public void delete(Iterable<MongoPsm> psms){
    if (psms==null || !psms.iterator().hasNext())
      logger.info("No PSMs to delete");
    else {
      mongoPsmRepository.delete(psms);
    }
  }

  /**
   * Deletes all PSMs in Mongo.
   */
  @Transactional
  public void deleteAll() {
    mongoPsmRepository.deleteAll();
  }

  /**
   * Deletes all PSMs in Mongo for a project.
   * @param projectAccession the project's accession number to delete PSMs
   */
  @Transactional
  public void deleteByProjectAccession(String projectAccession) {
    // todo Possible improvement - retrieve the ids to be deleted instead of the objects
    mongoPsmRepository.delete(mongoPsmRepository.findByProjectAccession(projectAccession));
  }

  /**
   * Saves PSMs to Mongo.
   * @param psms the PSMs to save
   * @return PSMs that were successfully saved.
   */
  @Transactional
  public Iterable<MongoPsm> save(Collection<MongoPsm> psms) {
    return mongoPsmRepository.save(psms);
  }

    @Resource
    private MongoPsmRepository mongoPsmRepository;

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * Initializes the service.
     */
    public PridePsmService() {
    }

    /**
     * Sets the Mongo repository.
     * @param mongoPsmRepository the Mongo PSM repository.
     */
    public void setMongoPsmRepository(MongoPsmRepository mongoPsmRepository) {
        this.mongoPsmRepository = mongoPsmRepository;
    }

    /**
     * Finds a PSM by an ID.
     * @param id the ID to search for
     * @return a PSM corresponding to the provided ID.
     */
    public MongoPsm findById(String id) {
        return mongoPsmRepository.findOne(id);
    }

    /**
     * Finds a list of PSMs in a collection of IDs.
     * @param ids a collection of ID to search for
     * @return a list of PSMs corresponding to the provided IDs.
     */
    public List<MongoPsm> findByIdIn(Collection<String> ids) {
        return mongoPsmRepository.findByIdIn(ids);
    }

    /**
     * A sorted list of PSMs in a collection of IDs.
     * @param ids a collection of ID to search for
     * @param sort how the result should be sorted
     * @return a list of PSMs corresponding to the provided IDs.
     */
    public List<MongoPsm> findByIdIn(Collection<String> ids, Sort sort) {
        return mongoPsmRepository.findByIdIn(ids, sort);
    }

    /**
     * Finds a PSM by a project accession.
     * @param projectAccession the project accession to search for
     * @return  a list of PSMs corresponding to the provided project accession
     */
    public List<MongoPsm> findByProjectAccession(String projectAccession) {
        return mongoPsmRepository.findByProjectAccession(projectAccession);
    }

    /**
     * Counts how many PSMs are for a project accession.
     * @param projectAccession the project accession to search for
     * @return  the number of PSMs corresponding to the provided project accession
     */
    public long countByProjectAccession(String projectAccession) {
        return mongoPsmRepository.countByProjectAccession(projectAccession);
    }

}


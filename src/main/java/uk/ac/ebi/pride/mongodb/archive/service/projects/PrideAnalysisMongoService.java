package uk.ac.ebi.pride.mongodb.archive.service.projects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.repo.projects.PrideAnalysisMongoRepository;

/**
 * @author ypriverol
 *
 */
@Service
@Slf4j
public class PrideAnalysisMongoService {

    final PrideAnalysisMongoRepository repository;

//    @Autowired
//    private MongoOperations mongo;

    public PrideAnalysisMongoService(PrideAnalysisMongoRepository repository) {
        this.repository = repository;
    }

//    /**
//     * Insert is allowing using to create a Accession for the File and insert the actual File into MongoDB.
//     * @param prideAnalysis {@link MongoPrideAnalysis}
//     * @return MongoPrideAnalysis
//     */
//    public MongoPrideAnalysis insert(MongoPrideAnalysis prideAnalysis) {
//        NumberFormat formatter = new DecimalFormat("000000");
//        if (prideAnalysis.getAccession() == null) {
//            String accession = "PXDA" + formatter.format(PrideMongoUtils.getNextSizedSequence(mongo, PrideArchiveField.PRIDE_ANALYSIS_COLLECTION, 1));
//            prideAnalysis.setAccession(accession);
//            prideAnalysis = repository.save(prideAnalysis);
//            log.debug("A new project has been saved into MongoDB database with Accession -- " + prideAnalysis.getAccession());
//        } else
//            log.error("A project with similar accession has been found in the MongoDB database, please use update function -- " + prideAnalysis.getAccession());
//        return prideAnalysis;
//    }





}

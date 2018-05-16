package uk.ac.ebi.pride.mongodb.archive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.CounterCollection;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFile;
import uk.ac.ebi.pride.mongodb.archive.repo.PrideFileMongoRepository;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * This Service allows to handle the Project Repositories.
 *
 * @author ypriverol
 */
@Service
public class PrideFileMongoService {

    @Autowired
    PrideFileMongoRepository fileRepository;

    @Autowired
    private MongoOperations mongo;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideFileMongoService.class);

    /**
     * Save a new Pride File into MongoDB
     * @param prideFile PrideFile
     * @return PrideFile
     */
    public PrideFile save(PrideFile prideFile){
        NumberFormat formatter = new DecimalFormat("0000000000");
        if(!fileRepository.findPrideFileByAccession(prideFile.getAccession()).isPresent()){
            String accession = "PXF"+ formatter.format(getNextSequence("PrideFile"));
            prideFile.setAccession(accession);
            prideFile = fileRepository.save(prideFile);
            LOGGER.info("A new project has been saved into MongoDB database with Accession -- " + prideFile.getAccession());
        }else
            LOGGER.info("A project with similar accession has been found in the MongoDB database, please use update function -- " + prideFile.getAccession());
        return prideFile;
    }



    public int getNextSequence(String seqName) {
        CounterCollection counter = mongo.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq",1), FindAndModifyOptions.options().returnNew(true).upsert(true), CounterCollection.class);
        return counter.getSeq();
    }



}

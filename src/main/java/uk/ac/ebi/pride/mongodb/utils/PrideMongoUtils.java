package uk.ac.ebi.pride.mongodb.utils;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.ac.ebi.pride.mongodb.archive.model.CounterCollection;

/**
 * @author ypriverol
 */
public class PrideMongoUtils {

    /**
     * This function generates a File accession with the following structure PXF 00000000000.
     * @param seqName
     * @return
     */
    public static int getNextSequence(MongoOperations mongo, String seqName) {
        CounterCollection counter = mongo.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq",1), FindAndModifyOptions.options().returnNew(true).upsert(true), CounterCollection.class);
        return counter.getSeq();
    }
}

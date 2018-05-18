package uk.ac.ebi.pride.mongodb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.mongodb.archive.model.CounterCollection;
import uk.ac.ebi.pride.mongodb.archive.service.PrideProjectMongoService;

import java.util.Arrays;

/**
 * @author ypriverol
 */
public class PrideMongoUtils {

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideMongoUtils.class);

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

    /**
     * This function is also replicated in other PRIDE libraries for Query purpose. The query Filter has the structure:
     * field1==value1, field2==value2, ...
     *
     * @param filterQuery filterQuery
     * @return LinkedMultiValueMap with the key and the values.
     */
    public static MultiValueMap<String, String> parseFilterParameters(String filterQuery){
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
        if(filterQuery != null && !filterQuery.trim().isEmpty()){
            String[] filtersString = (filterQuery + ",").split(",");
            if(filtersString.length > 0){
                Arrays.asList(filtersString).forEach(filter ->{
                    String[] filterString = filter.split("==");
                    if(filterString.length == 2)
                        filters.add(filterString[0], filterString[1]);
                    else
                        LOGGER.debug("The filter provided is not well-formatted, please format the filter in field:value -- " + filter);

                });
            }
        }
        return filters;
    }

    /**
     * Filter Criteria for Collection
     * @param filters
     * @return
     */
    public static Criteria buildQuery(MultiValueMap<String, String> filters) {
        Criteria filterCriteria = null;
        if(!filters.isEmpty()){
            for(String filter: filters.keySet()){
                filterCriteria = convertStringToCriteria(filterCriteria, filter, filters.getFirst(filter));
            }
        }

        return filterCriteria;
    }

    private static Criteria convertStringToCriteria(Criteria filterCriteria, String filterField, String valueFilter) {
        if(filterCriteria == null){
            filterCriteria = new Criteria(filterField).all(valueFilter);
        }else{
            filterCriteria = filterCriteria.andOperator(new Criteria(filterField).all(valueFilter));
        }
        return filterCriteria;
    }
}

package uk.ac.ebi.pride.mongodb.utils;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;
import uk.ac.ebi.pride.mongodb.archive.model.CounterCollection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * field1==value1, field2==value2, field=all=value2...
     *
     * @param filterQuery filterQuery
     * @return LinkedMultiValueMap with the key and the values.
     */
    public static List<Triple<String, String, String>> parseFilterParameters(String filterQuery){
        List<Triple<String, String, String>> filters = new ArrayList<>();
        Pattern composite = Pattern.compile("(.*)=(.*)=(.*)");
        if(filterQuery != null && !filterQuery.trim().isEmpty()){
            String[] filtersString = (filterQuery + ",").split(",");
            if(filtersString.length > 0){
                Arrays.asList(filtersString).forEach(filter ->{
                    String[] filterString = filter.split("==");
                    Matcher matcher = composite.matcher(filter);
                    if(filterString.length == 2)
                        filters.add(new ImmutableTriple<>(filterString[0], "in",filterString[1]));
                    else if(matcher.find()){
                        filters.add(new ImmutableTriple<>(matcher.group(1), matcher.group(2),matcher.group(3)));
                    } else
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
    public static Criteria buildQuery(List<Triple<String,String, String>> filters) {
        Criteria filterCriteria = null;
        if(!filters.isEmpty()){
            for(Triple filter: filters){
                filterCriteria = convertStringToCriteria(filterCriteria, (String)filter.getLeft(), (String)filter.getMiddle(), (String)filter.getRight());
            }
        }

        return filterCriteria;
    }

    private static Criteria convertStringToCriteria(Criteria filterCriteria, String filterField, String operator, String valueFilter) {
        if(filterCriteria == null){
            if(operator.equalsIgnoreCase("in"))
                filterCriteria = new Criteria(filterField).in(valueFilter);
            if(operator.equalsIgnoreCase("all"))
                filterCriteria = new Criteria(filterField).all(valueFilter);
            if(operator.equalsIgnoreCase("between")){
                Tuple<String, String> betweenClass = parseFilterBetween(valueFilter);
                filterCriteria = Criteria.where(filterField).gte(betweenClass.getKey()).and(filterField).lt(betweenClass.getValue());
            }
        }else{
            if(operator.equalsIgnoreCase("in"))
                filterCriteria = filterCriteria.andOperator(new Criteria(filterField).in(valueFilter));
            if(operator.equalsIgnoreCase("all"))
                filterCriteria = filterCriteria.andOperator(new Criteria(filterField).all(valueFilter));
            if(operator.equalsIgnoreCase("between")){
                Tuple<String, String> betweenClass = parseFilterBetween(valueFilter);
                filterCriteria = Criteria.where(filterField).gte(betweenClass.getKey()).and(filterField).lt(betweenClass.getValue());
            }

        }
        return filterCriteria;
    }

    private static Tuple<String, String> parseFilterBetween(String valueFilter) {
        Pattern composite = Pattern.compile("\\[(.*)TO(.*)\\]");
        Matcher matcher = composite.matcher(valueFilter);
        Tuple<String, String> queries = null;
        if(matcher.find())
             queries = new Tuple<>(matcher.group(1), matcher.group(2));
        return queries;
    }
}

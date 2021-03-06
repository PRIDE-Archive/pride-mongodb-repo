package uk.ac.ebi.pride.mongodb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFieldEnum;
import uk.ac.ebi.pride.mongodb.archive.model.projects.CounterCollection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ypriverol
 */
public class PrideMongoUtils {

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideMongoUtils.class);

    /**
     * This function generates an accession by incremented by the parameter size
     * @param seqName The collection in the MongoDB
     * @return Incremental Accession
     */
    public static int getNextSizedSequence(MongoOperations mongo, String seqName, int size) {
        CounterCollection counter = mongo.findAndModify(Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", size), FindAndModifyOptions.options().returnNew(true).upsert(true), CounterCollection.class);
        return counter.getSeq();
    }

    /**
     * This function is also replicated in other PRIDE libraries for Query purpose. The query Filter has the structure:
     * field1==value1, field2==value2, field=all=value2...
     *
     * @param filterQueryList Lists of Queries and filters
     * @return LinkedMultiValueMap with the key and the values.
     */
    public static List<Triple<String, String, String>> parseFilterParameters(String ... filterQueryList){
        List<Triple<String, String, String>> filters = new ArrayList<>();
        Pattern composite = Pattern.compile("(.*)=(.*)=(.*)");
        for(String filterQuery: filterQueryList){
            if(filterQuery != null && !filterQuery.trim().isEmpty()){
                String[] filtersString = (filterQuery).split(",");
                if(filtersString.length > 0){
                    Arrays.asList(filtersString).forEach(filter ->{
                        String[] filterString = filter.split("==");
                        Matcher matcher = composite.matcher(filter);
                        if(filterString.length == 2)
                            filters.add(new Triple<>(filterString[0], "is", filterString[1]));
                        else if(matcher.find()){
                            if(matcher.group(2).equalsIgnoreCase("in")) {
//                                filters.add(new ImmutableTriple<>(matcher.group(1), matcher.group(2), matcher.group(3).replace("|", ",")));
                                filters.add(new Triple<>(matcher.group(1), matcher.group(2), matcher.group(3)));
                            }else{
                                filters.add(new Triple<>(matcher.group(1), matcher.group(2), matcher.group(3)));
                            }
                        } else
                            LOGGER.debug("The filter provided is not well-formatted, please format the filter in field:value -- " + filter);

                    });
                }
            }
        }
        return filters;
    }

    /**
     * Filter Criteria for Collection
     * @param filters Filters to Create Criteria
     * @return Final Criteria to Filter
     */
    public static Criteria buildCriteria(List<Triple<String,String, String>> filters) {
        Criteria filterCriteria = null;
        if(!filters.isEmpty()){
            for(Triple filter: filters){
                filterCriteria = convertStringToCriteria(filterCriteria, (String)filter.getFirst(), (String)filter.getSecond(), (String)filter.getThird());
            }
        }
        return filterCriteria;
    }

    /**
     * Filter Criteria for Collection
     * @param filters Filters to Create Criteria
     * @return Final Criteria to Filter
     */
    public static Query buildQuery(List<Triple<String,String, String>> filters) {
        List<Criteria> filterCriteria = new ArrayList<>();
        Query query = new Query();
        if(!filters.isEmpty()){
            for(Triple filter: filters){
                filterCriteria.add(convertStringToCriteria((String)filter.getFirst(), (String)filter.getSecond(), (String)filter.getThird()));
            }
        }
        if (filterCriteria.size() > 0){
            query.addCriteria(new Criteria().andOperator(filterCriteria.toArray(new Criteria[filterCriteria.size()])));
        }
        return query;
    }

    private static Criteria convertStringToCriteria(Criteria filterCriteria, String filterField, String operator, String valueFilter) {
        if(filterCriteria == null){
            if (operator.equalsIgnoreCase("in"))
                filterCriteria = new Criteria(filterField).in(valueFilter.split(","));
            else if (operator.equalsIgnoreCase("is"))
                filterCriteria = new Criteria(filterField).is(valueFilter);
            else if (operator.equalsIgnoreCase("all"))
                filterCriteria = new Criteria(filterField).all(valueFilter);
            else if (operator.equalsIgnoreCase("range")) {
                Tuple<Object, Object> betweenClass = parseBetweenObjects(parseFilterBetween(valueFilter), filterField);
                filterCriteria = Criteria.where(filterField).gte(betweenClass.getKey()).lt(betweenClass.getValue());
            } else if (operator.equalsIgnoreCase("regex")) {
                filterCriteria = Criteria.where(filterField).regex(valueFilter);
            }
        }else{
            if (operator.equalsIgnoreCase("in"))
                filterCriteria = filterCriteria.andOperator(new Criteria(filterField).in(valueFilter.split(",")));
            else if (operator.equalsIgnoreCase("is"))
                filterCriteria = filterCriteria.andOperator(new Criteria(filterField).is(valueFilter));
            else if (operator.equalsIgnoreCase("all"))
                filterCriteria = filterCriteria.andOperator(new Criteria(filterField).all(valueFilter));
            else if (operator.equalsIgnoreCase("range")) {
                Tuple<Object, Object> betweenClass = parseBetweenObjects(parseFilterBetween(valueFilter), filterField);
                filterCriteria = Criteria.where(filterField).gte(betweenClass.getKey()).lt(betweenClass.getValue());
            } else if (operator.equalsIgnoreCase("regex")) {
                filterCriteria = filterCriteria.andOperator(new Criteria(filterField).regex(valueFilter));
            }

        }
        return filterCriteria;
    }

    private static Criteria convertStringToCriteria(String filterField, String operator, String valueFilter) {
        Criteria filterCriteria = null;
        if (operator.equalsIgnoreCase("in"))
            filterCriteria = new Criteria(filterField).in(valueFilter.split(","));
        else if (operator.equalsIgnoreCase("is"))
            filterCriteria = new Criteria(filterField).is(valueFilter);
        else if (operator.equalsIgnoreCase("all"))
            filterCriteria = new Criteria(filterField).all(valueFilter);
        else if (operator.equalsIgnoreCase("range")) {
            Tuple<Object, Object> betweenClass = parseBetweenObjects(parseFilterBetween(valueFilter), filterField);
            filterCriteria = Criteria.where(filterField).gte(betweenClass.getKey()).lt(betweenClass.getValue());
        } else if (operator.equalsIgnoreCase("regex")) {
            filterCriteria = Criteria.where(filterField).regex(valueFilter);
        }
        return filterCriteria;
    }

    /**
     * PArse the values between a Range.
     * @param stringTuple The Tuple containing the first Value of the range and the Second value.
     * @param filterField The filterField.
     * @return The Obejcts to filter,
     */
    private static Tuple<Object, Object> parseBetweenObjects(Tuple<String, String> stringTuple, String filterField) {
        Class classType = String.class;
        Tuple<Object, Object> resultTuple = new Tuple<>(stringTuple.getKey(), stringTuple.getValue());
        for(PrideFieldEnum field: PrideFieldEnum.values()){
            if(field.getFieldName().equalsIgnoreCase(filterField)){
                classType = field.getClassType();
            }
        }
        try{
            if(classType == Date.class){
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(stringTuple.getKey());
                Date startDate = uk.ac.ebi.pride.utilities.util.DateUtils.atStartOfDay(date);

                date = new SimpleDateFormat("yyyy-MM-dd").parse(stringTuple.getValue());
                Date endDate = uk.ac.ebi.pride.utilities.util.DateUtils.atEndOfDay(date);
                resultTuple = new Tuple<>(startDate, endDate);


            }
        }catch(ParseException ex){
            LOGGER.error(ex.getMessage());
        }
        return resultTuple;

    }

    /**
     * Parse a Range String
     * @param valueFilter Value String
     * @return a Tuple of Start and End Values.
     */
    private static Tuple<String, String> parseFilterBetween(String valueFilter) {
        Pattern composite = Pattern.compile("\\[(.*)TO(.*)\\]");
        Matcher matcher = composite.matcher(valueFilter);
        Tuple<String, String> queries = null;
        if(matcher.find())
             queries = new Tuple<>(matcher.group(1).trim(), matcher.group(2).trim());
        return queries;
    }

    /**
     * Search by Accessions if any accession contains one of the values in the List.
     * @param accessions List of Accessions
     * @return accessions.
     */
    public static Criteria builQueryByAccessions(Collection<String> accessions) {
        return new Criteria(PrideArchiveField.ACCESSION).in(accessions);
    }
}

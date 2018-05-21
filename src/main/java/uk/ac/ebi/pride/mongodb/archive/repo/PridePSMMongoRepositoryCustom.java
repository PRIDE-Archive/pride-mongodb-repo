package uk.ac.ebi.pride.mongodb.archive.repo;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.PrideMongoPSM;


import java.util.List;

/**
 * @author ypriverol
 */
public interface PridePSMMongoRepositoryCustom {

    Page<PrideMongoPSM> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) ;
}

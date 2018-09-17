package uk.ac.ebi.pride.mongodb.archive.model.stats;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.utils.Tuple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 27/06/2018.
 */

@Document(collection = PrideArchiveField.PRIDE_STATS_COLLECTION)
@Data
@Builder
public class MongoPrideStats implements PrideArchiveField {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /**Submission Counts */
    @Field(value = PrideArchiveField.STATS_SUBMISSION_COUNTS)
    Map<String, List<Tuple<String, Integer>>> submissionsCount;


    @Indexed(unique = true, name = PrideArchiveField.STATS_ESTIMATION_DATE)
    Date estimationDate;

    @Field(value = PrideArchiveField.STATS_COMPLEX_COUNTS)
    Map<String, Object> complexStats;

}

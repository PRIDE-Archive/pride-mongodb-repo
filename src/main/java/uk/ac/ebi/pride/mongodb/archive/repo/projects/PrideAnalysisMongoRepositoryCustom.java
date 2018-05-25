package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideAnalysis;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideFile;

import java.util.List;

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
 * Created by ypriverol (ypriverol@gmail.com) on 25/05/2018.
 */
public interface PrideAnalysisMongoRepositoryCustom {

    Page<MongoPrideAnalysis> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page);
}

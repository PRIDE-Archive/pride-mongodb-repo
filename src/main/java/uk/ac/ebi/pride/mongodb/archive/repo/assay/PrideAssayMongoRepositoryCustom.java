package uk.ac.ebi.pride.mongodb.archive.repo.assay;


import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.archive.model.assay.MongoPrideAssay;

import java.util.List;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 01/11/2018.
 */
public interface PrideAssayMongoRepositoryCustom {

    List<MongoPrideAssay> filterByAttributes(List<Triple<String, String, String>> filters);
}

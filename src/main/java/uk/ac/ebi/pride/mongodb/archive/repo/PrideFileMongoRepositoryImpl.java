package uk.ac.ebi.pride.mongodb.archive.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFile;

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
 * Created by ypriverol (ypriverol@gmail.com) on 17/05/2018.
 */
public class PrideFileMongoRepositoryImpl implements PrideFileMongoRepositoryCustom{


    @Override
    public Page<PrideFile> filterByAttributes(MultiValueMap<String, String> filters, Pageable page) {
        return null;
    }
}

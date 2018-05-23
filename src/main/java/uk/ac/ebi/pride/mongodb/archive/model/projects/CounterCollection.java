package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
 * Created by ypriverol (ypriverol@gmail.com) on 16/05/2018.
 */
@Document(collection = "counter_collections")
@Data
@Builder
public class CounterCollection {

    @Id
    private String id;
    private String type;
    private int seq;

}

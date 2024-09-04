package uk.ac.ebi.pride.mongodb.archive.model.projects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 31/08/2018.
 */
public class MongoPrideProjectTest {

    MongoPrideProject project;

    @BeforeEach
    public void setUp() {

        Collection<String> tags = Collections.singleton("Metaproteomics");

        project = MongoPrideProject.builder().accession("PXD0001")
                .projectTags(tags)
                .build();
    }

    @Test
    public void tesPrideMongoProject(){
        System.out.println(project.toString());
    }
}
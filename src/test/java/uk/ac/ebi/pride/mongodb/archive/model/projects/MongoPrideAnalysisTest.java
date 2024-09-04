package uk.ac.ebi.pride.mongodb.archive.model.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 03/09/2018.
 */
public class MongoPrideAnalysisTest {

    MongoPrideAnalysis project;

    @BeforeEach
    public void setUp() {

        project = MongoPrideAnalysis.builder().accession("RPX000001").build();
    }

    @Test
    public void mongoAnalysisTest(){
        System.out.println(project.toString());
        Assertions.assertTrue(project.accession.equalsIgnoreCase("RPX000001"));
    }
}
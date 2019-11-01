package uk.ac.ebi.pride.mongodb.archive.model.files;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.utils.ProjectFileCategoryConstants;

import java.util.HashSet;
import java.util.Set;

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

public class MongoPrideFileTest {

    MongoPrideFile file;
    @Before
    public void setUp() {

        CvParamProvider cvCategory = ProjectFileCategoryConstants.RAW.getCv();
        Set<String> projects = new HashSet<>();
        projects.add("PX00000001");

        Set<String> analysisAccessions = new HashSet<>();
        analysisAccessions.add("RPXD000000111");

        file = MongoPrideFile
                .builder()
                .accession("PFD11111111")
                .fileCategory(new CvParam(cvCategory.getCvLabel(),cvCategory.getAccession(),cvCategory.getName(), cvCategory.getValue()))
                .fileName("example_file.raw")
                .projectAccessions(projects)
                .analysisAccessions(analysisAccessions)
                .build();
    }

    @Test
    public void tesPrideMongoFile(){
        System.out.println(file.toString());
    }
}
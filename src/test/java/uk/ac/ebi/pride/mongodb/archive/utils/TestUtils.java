package uk.ac.ebi.pride.mongodb.archive.utils;

import uk.ac.ebi.pride.data.model.ProjectMetaData;
import uk.ac.ebi.pride.data.model.Submission;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;

import java.util.Arrays;
import java.util.stream.Collectors;

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
public class TestUtils {

    /**
     * Parse the submission file and convert it into MongoPrideProject
     * @param submission
     * @return
     */
    public static MongoPrideProject parseProject(Submission submission){
        ProjectMetaData metadata = submission.getProjectMetaData();
        return MongoPrideProject.builder()
                .accession(metadata.getResubmissionPxAccession())
                .instruments(metadata.getInstruments().stream().map( x-> new MongoCvParam(x.getCvLabel(),x.getAccession(),x.getName(),x.getValue())).collect(Collectors.toList()))
                .projectTags(metadata.getProjectTags())
                .keywords(Arrays.asList(metadata.getKeywords().split(",")))
                .ptmList(metadata.getModifications().stream().map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(),x.getName(), x.getValue())).collect(Collectors.toList()))
                .build();
    }
}

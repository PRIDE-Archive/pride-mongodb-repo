package uk.ac.ebi.pride.mongodb.archive.model.param;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;

import java.io.Serializable;

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
 * Created by ypriverol (ypriverol@gmail.com) on 15/06/2018.
 */
public class MongoCvParam implements CvParamProvider, Serializable {

    /** CvLabel is used to name the Ontology for the Ontology Term **/
    private String CvLabel;

    /** Accession of the Ontology Term **/
    private String accession;

    /** Ontology Term Name **/
    private String name;

    /** Value of the Term. For example, the scores in CvTerm, the value of the score is in the value of the CVTerm **/
    private String value;

    /*
     * Default constructor
     */
    public MongoCvParam(){ }

    /**
     * Constructor with all the attributes
     * @param cvLabel Cv Label
     * @param accession Accession
     * @param name Name of the CV
     * @param value Value
     */
    public MongoCvParam(String cvLabel, String accession, String name, String value) {
        CvLabel = cvLabel;
        this.accession = accession;
        this.name = name;
        this.value = value;
    }

    @Override
    public String getCvLabel() {
        return CvLabel;
    }

    @Override
    public String getAccession() {
        return accession;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Comparable getId() {
        return accession;
    }
}

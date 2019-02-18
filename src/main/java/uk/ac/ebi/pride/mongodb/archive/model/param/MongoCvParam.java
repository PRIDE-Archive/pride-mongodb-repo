package uk.ac.ebi.pride.mongodb.archive.model.param;

import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;

import java.util.Objects;

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
public class MongoCvParam implements CvParamProvider {

    /** CvLabel is used to name the Ontology for the Ontology Term **/
    private String cvLabel;

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
        this.cvLabel = cvLabel;
        this.accession = accession;
        this.name = name;
        this.value = value;
    }

    /**
     * Create a MongoCvParam from an interface
     * @param cv {@link CvParamProvider}
     */
    public MongoCvParam(CvParamProvider cv) {
        this.cvLabel = cv.getCvLabel();
        this.accession = cv.getAccession();
        this.name = cv.getName();
        this.value = cv.getValue();
    }

    @Override
    public String getCvLabel() {
        return cvLabel;
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
    public String toString() {
        return "MongoCvParam{" +
                "CvLabel='" + cvLabel + '\'' +
                ", accession='" + accession + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MongoCvParam that = (MongoCvParam) o;
        return Objects.equals(cvLabel, that.cvLabel) &&
                Objects.equals(accession, that.accession) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cvLabel, accession, name, value);
    }

}

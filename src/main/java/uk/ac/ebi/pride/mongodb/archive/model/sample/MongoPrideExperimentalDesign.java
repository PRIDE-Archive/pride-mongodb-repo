package uk.ac.ebi.pride.mongodb.archive.model.sample;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.project.ExperimentalDesignProvider;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleMSRunTuple;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Collection;
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
 * @author ypriverol on 29/10/2018.
 */
@Document(collection = PrideArchiveField.PRIDE_EXPERIMENTAL_DESIGN_COLLECTION_NAME)
@Data
@Builder
public class MongoPrideExperimentalDesign implements ExperimentalDesignProvider {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = PrideArchiveField.ACCESSION)
    String accession;

    /** PRIDE Project short description **/
    @Field(value = PrideArchiveField.SAMPLES)
    public List<MongoPrideSample> samples;

    /** PRIDE Project short description **/
    @Field(value = PrideArchiveField.SAMPLES_MSRUN)
    public List<SampleMSRunTuple> sampleMSRunTuples;

    @Override
    public String getAccession() {
        return accession;
    }

    @Override
    public Collection<? extends SampleProvider> getSampleTableMetadata() {
        return samples;
    }

    @Override
    public Collection<? extends SampleMSRunTuple> getSampleMSrun() {
        return sampleMSRunTuples;
    }
}

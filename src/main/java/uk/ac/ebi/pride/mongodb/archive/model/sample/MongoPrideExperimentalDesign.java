package uk.ac.ebi.pride.mongodb.archive.model.sample;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.project.ExperimentalDesignProvider;
import uk.ac.ebi.pride.archive.dataprovider.sample.Sample;
import uk.ac.ebi.pride.archive.dataprovider.sample.ISampleMSRunRow;
import uk.ac.ebi.pride.archive.dataprovider.sample.SampleProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
@TypeAlias("MongoPrideExperimentalDesign")
public class MongoPrideExperimentalDesign implements ExperimentalDesignProvider {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    @Indexed(name = PrideArchiveField.ACCESSION)
    String projectAccession;

    /** Project Accession in PRIDE**/
    @Field(value = PrideArchiveField.SAMPLES_MSRUN)

    @Setter(AccessLevel.PRIVATE)
    List<MongoISampleMSRunRow> sampleMSRunTuples;

    @Override
    public String getProjectAccession() {

        if(sampleMSRunTuples != null && !sampleMSRunTuples.isEmpty()){
            Optional<String> projectId = sampleMSRunTuples.stream().map(MongoISampleMSRunRow::getProjectAccession).findFirst();
            if(projectId.isPresent())
                return projectId.get();
        }
        return null;
    }

    @Override
    public Collection<? extends SampleProvider> getSamples() {
        if(sampleMSRunTuples != null && !sampleMSRunTuples.isEmpty()){
            return sampleMSRunTuples.stream()
                    .map(x -> new Sample(x.getSampleAccession(), x.getSampleProperties()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public void setSampleMSRunTuples(List<? extends ISampleMSRunRow> sampleMSRunTuples) {
        this.sampleMSRunTuples = sampleMSRunTuples.stream().map( x-> new MongoISampleMSRunRow(x.getSampleAccession(), x.getProjectAccession(),
                ((ISampleMSRunRow) x).getMsRunAccession(), x.getFractionIdentifierCvParam(),
                x.getSampleLabel(), x.getLabelReagent(), x.getSampleProperties(), ((ISampleMSRunRow) x).getMsRunProperties()))
                .collect(Collectors.toList());
    }

    public List<MongoISampleMSRunRow> getSampleMSRunTuples() {
        return sampleMSRunTuples;
    }


    public Collection<? extends ISampleMSRunRow> getSampleMSrun() {
        return sampleMSRunTuples;
    }
}

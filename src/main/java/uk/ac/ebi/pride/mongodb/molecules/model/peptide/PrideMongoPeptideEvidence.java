package uk.ac.ebi.pride.mongodb.molecules.model.peptide;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.data.database.DatabaseProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.peptide.PeptideSequenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PrideMongoPSMS models the PRIDE PSMs provided to PRIDE by each experiment.
 * If the PSM was discovered as a combination of several spectra, we will simplify the case by choosing only the first spectrum and the first retention time.
 *
 * @author ypriverol
 *
 **/

@Data
@Builder
@Document(collection = PrideArchiveField.PRIDE_PEPTIDE_COLLECTION_NAME)
@CompoundIndexes({@CompoundIndex(name = "compound_peptide_accession", def = "{'assayAccession' : 1, 'proteinAccession': 1, 'peptideAccession':1}", unique = true)})
public class PrideMongoPeptideEvidence implements PrideArchiveField, PeptideSequenceProvider {

    /** Generated accession **/
    @Id
    @Indexed(name = PrideArchiveField.ID)
    private ObjectId id;

    /** Accession Provided by PRIDE Pipelines **/
    @Indexed(name = PrideArchiveField.PEPTIDE_ACCESSION)
    String peptideAccession;

    /** Reported File ID is the Identifier of the File mzTab in PRIDE **/
    @Field(value = PrideArchiveField.PROTEIN_ACCESSION)
    private String proteinAccession;

    /** Accession in Reported File **/
    @Indexed(name = PROTEIN_ASSAY_ACCESSION)
    private String assayAccession;

    /** External Project that contains the PSM **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSION)
    private String projectAccession;

    /** Peptide Sequence **/
    @Indexed(name = PrideArchiveField.PEPTIDE_SEQUENCE)
    private String peptideSequence;

    /** Database information used to perform the identification/quantification **/
    @Field(value = IDENTIFICATION_DATABASE)
    private DatabaseProvider database;

    /** PTMs Identified in the PEptide Sequence **/
    @Field(value = PROJECT_IDENTIFIED_PTM)
    private Collection<? extends IdentifiedModificationProvider> ptmList;

    /** Best Search engine scores **/
    @Field(value = BEST_PSM_SCORE)
    CvParamProvider bestPSMScore;

    /** Additional Attributes **/
    @Field(value = PrideArchiveField.ADDITIONAL_ATTRIBUTES)
    private List<MongoCvParam> additionalAttributes;

    @Indexed(name = PSM_SPECTRUM_ACCESSIONS)
    private List<PeptideSpectrumOverview> psmAccessions;

    @Field( value = IS_DECOY)
    private Boolean isDecoy;

    @Field (value = START_POSITION)
    private Integer startPosition;

    @Field( value = END_POSITION)
    private Integer endPosition;

    @Field( value = MISSED_CLEAVAGES)
    Integer missedCleavages;

    @Field(PrideArchiveField.QUALITY_ESTIMATION_METHOD)
    private List<MongoCvParam> qualityEstimationMethods;

    @Indexed(name = PrideArchiveField.IS_VALIDATED)
    private Boolean isValid;

    @Override
    public Collection<? extends IdentifiedModificationProvider> getModifications() {
        return null;
    }

    @Override
    public Collection<String> getModificationNames() {
        List<String> ptms = Collections.EMPTY_LIST;
        if(this.ptmList != null && !this.ptmList.isEmpty())
            ptms = ptmList.stream().map(x-> x.getModificationCvTerm().getName()).collect(Collectors.toList());
        return ptms;
    }

    @Override
    public Integer getNumberModifiedSites() {
        final int[] sites = {0};
        if(this.ptmList != null && !this.ptmList.isEmpty()){
            this.ptmList.forEach(x -> sites[0] += x.getPositionMap().size());
            return sites[0];
        }
        return 0;
    }

    @Override
    public Integer getMissedCleavages() {
        return missedCleavages;
    }

    @Override
    public Boolean isDecoy() {
        return isDecoy;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        List<String> attributes = Collections.EMPTY_LIST;
        if(this.additionalAttributes != null )
            return additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        return attributes;
    }




}

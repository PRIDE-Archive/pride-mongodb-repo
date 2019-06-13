package uk.ac.ebi.pride.mongodb.spectral.model.psms;

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
import uk.ac.ebi.pride.archive.dataprovider.common.Tuple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
@Document(collection = PrideArchiveField.PRIDE_PSM_COLLECTION_NAME)
@CompoundIndexes({@CompoundIndex(name = "compound_psm_accession", def = "{'reportedFileID' : 1, 'reportedProteinAccession': 1, 'projectAccession':1}", unique = true)})
public class PrideMongoPSM implements PrideArchiveField, PeptideSequenceProvider {

    /** Generated accession **/
    @Id
    @Indexed(name = PrideArchiveField.ID)
    private ObjectId id;

    /** Accession Provided by PRIDE Pipelines **/
    @Indexed(name = ACCESSION, unique = true)
    String accession;

    /** Reported File ID is the Identifier of the File mzTab in PRIDE **/
    @Field(value = PrideArchiveField.REPORTED_FILE_ID)
    private String reportedFileID;

    /** Accession in Reported File **/
    @Indexed(name = ACCESSION_IN_REPORTED_FILE)
    private String accessionInReportedFile;

    /** External Project that contains the PSM **/
    @Indexed(name = EXTERNAL_PROJECT_ACCESSION)
    private String projectAccession;

    /** Peptide Sequence **/
    @Indexed(name = PrideArchiveField.PEPTIDE_SEQUENCE)
    private String peptideSequence;

    /** Spectrum Accession **/
    @Field(value = PrideArchiveField.SPECTRUM_ACCESSION)
    private String spectrumAccession;

    /** Protein Accession **/
    @Indexed(name = PrideArchiveField.REPORTED_PROTEIN_ACCESSION)
    private String reportedProteinAccession;

    /** Database information used to perform the identification/quantification **/
    @Field(value = IDENTIFICATION_DATABASE)
    private DatabaseProvider database;

    /** PTMs Identified in the PEptide Sequence **/
    @Field(value = PROJECT_IDENTIFIED_PTM)
    private List<IdentifiedModificationProvider> ptmList;

    /** Best Search engine scores **/
    @Field(value = BEST_PSM_SCORE)
    Tuple<CvParamProvider, CvParamProvider> bestPSMScore;

    /** Additional Attributes **/
    @Indexed(name = PrideArchiveField.ADDITIONAL_ATTRIBUTES)
    private List<CvParamProvider> additionalAttributes;

    @Indexed(name = PrideArchiveField.SEARCH_ENGINE_SCORES)
    private Map<CvParamProvider, List<CvParamProvider>> searchEngineScores;

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
    public int getNumberModifiedSites() {
        final int[] sites = {0};
        if(this.ptmList != null && !this.ptmList.isEmpty()){
            this.ptmList.forEach(x -> sites[0] += x.getPositionMap().size());
            return sites[0];
        }
        return 0;
    }

    @Override
    public double getTheoreticalMass() {
        return 0;
    }

    @Override
    public int getMissedCleavages() {
        return 0;
    }

    @Override
    public double getDeltaMass() {
        return 0;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributesStrings() {
        List<String> attributes = Collections.EMPTY_LIST;
        if(this.additionalAttributes != null )
            return additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList());
        return attributes;
    }
}

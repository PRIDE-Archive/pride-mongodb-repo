package uk.ac.ebi.pride.mongodb.archive.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.data.database.DatabaseProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.peptide.PeptideSequenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;


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
@Document(collection = PrideArchiveField.PRIDE_PSM_COLLECTION_NAME)
@CompoundIndexes({@CompoundIndex(name = "compound_psm_accession", def = "{'reportedFileID' : 1, 'reportedProteinAccession': 1, 'externalProjectAccession':1}", unique = true)})
public class PrideMongoPSM implements PrideArchiveField, PeptideSequenceProvider {
  /*  */

  /** Generated accession **/
  @Id
  @Indexed(name = PrideArchiveField.ID)
  private ObjectId id;

  /** Accession Provided by PRIDE Pipelines **/
  @Indexed(name = ACCESSION, unique = true)
  String accession;

  /** reported ID in the file **/
  @Indexed(name = PrideArchiveField.REPORTED_FILE_ID)
  private String reportedFileID;

  @Indexed(name = PrideArchiveField.PEPTIDE_SEQUENCE)
  private String peptideSequence;

  /** Spectrum Accession **/
  @Indexed(name = PrideArchiveField.SPECTRUM_ACCESSION)
  private String spectrumAccession;

  @Indexed(name = PrideArchiveField.REPORTED_PROTEIN_ACCESSSION)
  private String reportedProteinAccession;

  /** External Project that contains the PSM **/
  @Indexed(name = EXTERNAL_PROJECT_ACCESSION)
  private String externalProjectAccession;

  /** External Analysis that contains the PSM **/
  @Indexed(name = EXTERNAL_ANALYSIS_ACCESSION)
  private String externalAnalysisAccession;

  /** Database information used to perform the idnetification/quantification **/
  @Indexed(name = IDENTIFICATION_DATABASE)
  private DatabaseProvider database;

  /** PTMs Identified in the PEptide Sequence **/
  @Indexed(name = PROJECT_IDENTIFIED_PTM)
  private List<IdentifiedModificationProvider> ptmList;

  /** UNIQUE in the Analysis **/
  @Indexed(name = PEPTIDE_UNIQUE)
  private Boolean unique;

  /** Best Search engine scores **/
  @Indexed(name = BEST_PSM_SCORE)
  CvParamProvider bestPSMScore;

  @Indexed(name = RETENTION_TIME)
  private Double retentionTime;

  @Indexed(name = CHARGE)
  private Integer charge;

  @Indexed(name = EXPERIMENTAL_MASS_TO_CHARGE)
  private Double expMassToCharge;

  @Indexed(name = CALCULATED_MASS_TO_CHARGE)
  private Double calculatedMassToCharge;

  @Indexed(name = PRE_AMINO_ACID)
  private String preAminoAcid;

  @Indexed(name = POST_AMINO_ACID)
  private String postAminoAcid;

  @Indexed(name = START_POSITION)
  private Integer startPosition;

  @Indexed(name = END_POSITION)
  private Integer endPosition;

  /** Additional Attributes including Scores **/
  @Indexed(name = PrideArchiveField.ADDITIONAL_ATTRIBUTES)
  private List<CvParamProvider> additionalAttributes;

  @Override
  public Collection<? extends IdentifiedModificationProvider> getPTMs() {
    return ptmList;
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
  public Collection<? extends String> getAdditionalAttributesStrings() {
      List<String> attributes = Collections.EMPTY_LIST;
      if(this.additionalAttributes != null )
          return additionalAttributes.stream().map(CvParamProvider::getName).collect(Collectors.toList());
      return attributes;
  }
}

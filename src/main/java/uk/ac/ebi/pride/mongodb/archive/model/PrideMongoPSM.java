package uk.ac.ebi.pride.mongodb.archive.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.data.database.DatabaseProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.peptide.PeptideSequenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;


import java.util.Collection;
import java.util.List;

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

  /** External RESULT FILE that contains the PSM **/
  @Indexed(name = EXTERNAL_RESULT_FILE_ACCESSION)
  private String externalResultFileAccession;

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


  @Override
  public Collection<? extends IdentifiedModificationProvider> getPTMs() {
    return null;
  }

  @Override
  public Collection<String> getModificationNames() {
    return null;
  }

  @Override
  public int getUniqueModificationCount() {
    return 0;
  }

  @Override
  public int getModifiedResiduesCount() {
    return 0;
  }

  public void setPeptideSequence(String peptideSequence) {
    this.peptideSequence = peptideSequence;
  }

  @Override
  public Collection<? extends String> getAdditionalAttributesStrings() {
    return null;
  }
}

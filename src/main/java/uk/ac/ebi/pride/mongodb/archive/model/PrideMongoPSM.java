package uk.ac.ebi.pride.mongodb.archive.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.data.peptide.PeptideSequenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;


import java.util.ArrayList;
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

  private String reportedId;

  @Indexed(name = PrideArchiveField.PEPTIDE_SEQUENCE)
  private String peptideSequence;

  /** Spectrum Accession **/
  @Indexed(name = PrideArchiveField.SPECTRUM_ACCESSION)
  private String spectrumAccession;

  @Indexed(name = PrideArchiveField.REPORTED_PROTEIN_ACCESISION)
  private String reportedProteinAccession;


  private String database;
  private String databaseVersion;
  private String projectAccession;
  private String assayAccession;
  private List<String> modificationsAsString;
  private List<String> modificationNames;
  private List<String> modificationAccessions;
  private Boolean unique;
  private List<String> searchEngineAsString;
  private List<String> searchEngineScoreAsString;
  private Double retentionTime;
  private Integer charge;
  private Double expMassToCharge;
  private Double calculatedMassToCharge;
  private String preAminoAcid;
  private String postAminoAcid;
  private Integer startPosition;
  private Integer endPosition;

  public PrideMongoPSM() {
    modificationsAsString = new ArrayList<>();
    modificationNames = new ArrayList<>();
    modificationAccessions = new ArrayList<>();
    searchEngineAsString = new ArrayList<>();
    searchEngineScoreAsString = new ArrayList<>();
  }

  public String getReportedId() {
    return reportedId;
  }

  public void setReportedId(String reportedId) {
    this.reportedId = reportedId;
  }

  public String getPeptideSequence() {
    return peptideSequence;
  }

  @Override
  public Collection<? extends IdentifiedModificationProvider> getPTMs() {
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
  public Collection<? extends String> getAdditionalAttributes() {
    return null;
  }
}

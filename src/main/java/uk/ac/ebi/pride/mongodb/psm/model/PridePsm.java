package uk.ac.ebi.pride.mongodb.psm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.pride.archive.dataprovider.data.peptide.PeptideSequenceProvider;
import uk.ac.ebi.pride.archive.dataprovider.data.ptm.IdentifiedModificationProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.ParamProvider;


import java.util.Collection;
import java.util.List;

/**
 * The PSMs is a table with all Peptides reported in PRIDE Archive with the corresponding sequence and Modifications.
 * In addition we have added the scores at PSM level for each PSM. This testdata model is based on the mzTab file format model
 * - https://github.com/HUPO-PSI/mzTab
 *
 *
 */
@Document(collection = "psm")
public class PridePsm implements PeptideSequenceProvider {

    @Id
    private String id;

    /** Reported Id in the original mzTab File **/
    private String reportedId;

    /** Peptide sequence without modification information**/
    private String peptideSequence;

    /** Todo: This needs to be reviwed in details, rigth now we are only referencing one spectrum **/
    private String spectrumId;

    /** Original protein accession as reported in the original experiment **/
    private String proteinAccession;

    /** DataBase Name used to identified the PSM **/
    private String database;

    /** Database Version **/
    private String databaseVersion;

    /** Project Accession reported in PRIDE **/
    private String projectAccession;

    /** Accession Original reported for the dataset **/
    private String assayAccession;

    /** Modification presented in the Peptide as String List */
    private List<String> modificationsAsString;

    /** Modification Names List **/
    private List<String> modificationNames;


    /** Peptide Uniqueness **/
    private Boolean unique;

    /** List of SearchEngines **/
    private List<String> searchEngineAsString;

    /** List of Scores from Search Engines **/
    private List<String> searchEngineScoreAsString;

    // If the psm was discovered as a combination of several spectra, we will simplify the case choosing only the first spectrum and the first retention time
    /** Todo: This needs to be reviewed because is important for the Spectrum Ref  **/
    private Double retentionTime;

    /** Charge is based on the Spectrum used to identified **/
    private Integer charge;

    /** This is based on the Spectrum **/
    private Double expMassToCharge;

    /** Calculated Mass to Charge, This is based on the Peptide Sequence **/
    private Double calculatedMassToCharge;

    /** Pre Aminoacid **/
    private String preAminoAcid;

    /** Post Aminoacid **/
    private String postAminoAcid;

    /** Start Position in the Protein Sequence **/
    private Integer startPosition;

    /** End position in the Protein sequence **/
    private Integer endPosition;


    @Override
    public Comparable getId() {
    return id;
  }

    public String getReportedId() {
        return reportedId;
    }

    public void setReportedId(String reportedId) {
        this.reportedId = reportedId;
    }

    @Override
    public String getPeptideSequence() {
        return peptideSequence;
    }

    @Override
    public Collection<? extends IdentifiedModificationProvider> getPTMs() {
        return null;
    }

    public void setPeptideSequence(String peptideSequence) {
        this.peptideSequence = peptideSequence;
    }

    public String getSpectrumId() {
        return spectrumId;
    }

    public void setSpectrumId(String spectrumId) {
        this.spectrumId = spectrumId;
    }

    public String getProteinAccession() {
        return proteinAccession;
    }

    public void setProteinAccession(String proteinAccession) {
        this.proteinAccession = proteinAccession;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public void setDatabaseVersion(String databaseVersion) {
        this.databaseVersion = databaseVersion;
    }

    public String getProjectAccession() {
        return projectAccession;
    }

    public void setProjectAccession(String projectAccession) {
        this.projectAccession = projectAccession;
    }

    public String getAssayAccession() {
        return assayAccession;
    }

    public void setAssayAccession(String assayAccession) {
        this.assayAccession = assayAccession;
    }

    public List<String> getModificationsAsString() {
        return modificationsAsString;
    }

    public void setModificationsAsString(List<String> modificationsAsString) {
        this.modificationsAsString = modificationsAsString;
    }

    @Override
    public List<String> getModificationNames() {
        return modificationNames;
    }

    @Override
    public int getUniqueModificationCount() {
        return 0;
    }

    @Override
    public int getModifiedResiduesCount() {
        return 0;
    }

    public void setModificationNames(List<String> modificationNames) {
        this.modificationNames = modificationNames;
    }



    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public List<String> getSearchEngineAsString() {
        return searchEngineAsString;
    }

    public void setSearchEngineAsString(List<String> searchEngineAsString) {
        this.searchEngineAsString = searchEngineAsString;
    }

    public List<String> getSearchEngineScoreAsString() {
        return searchEngineScoreAsString;
    }

    public void setSearchEngineScoreAsString(List<String> searchEngineScoreAsString) {
        this.searchEngineScoreAsString = searchEngineScoreAsString;
    }

    public Double getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Double retentionTime) {
        this.retentionTime = retentionTime;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Double getExpMassToCharge() {
        return expMassToCharge;
    }

    public void setExpMassToCharge(Double expMassToCharge) {
        this.expMassToCharge = expMassToCharge;
    }

    public Double getCalculatedMassToCharge() {
        return calculatedMassToCharge;
    }

    public void setCalculatedMassToCharge(Double calculatedMassToCharge) {
        this.calculatedMassToCharge = calculatedMassToCharge;
    }

    public String getPreAminoAcid() {
        return preAminoAcid;
    }

    public void setPreAminoAcid(String preAminoAcid) {
        this.preAminoAcid = preAminoAcid;
    }

    public String getPostAminoAcid() {
        return postAminoAcid;
    }

    public void setPostAminoAcid(String postAminoAcid) {
        this.postAminoAcid = postAminoAcid;
    }

    public Integer getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Integer startPosition) {
        this.startPosition = startPosition;
    }

    public Integer getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Integer endPosition) {
        this.endPosition = endPosition;
    }

    @Override
    public Collection<? extends String> getAdditionalAttributes() {
        return null;
    }

    //  public Iterable<CvParamProvider> getSearchEngines() {
//    List<CvParamProvider> searchEngines = new ArrayList<>();
//    if (searchEngineAsString != null) {
//      for (String se : searchEngineAsString) {
//        searchEngines.add(CvParamHelper.convertFromString(se));
//      }
//    }
//    return searchEngines;
//  }
//
//  public void setSearchEngines(List<CvParamProvider> searchEngines) {
//    if (searchEngines != null) {
//      List<String> searchEngineAsString = new ArrayList<>();
//      for (CvParamProvider searchEngine : searchEngines) {
//        searchEngineScoreAsString.add(CvParamHelper.convertToString(searchEngine));
//      }
//      this.searchEngineAsString = searchEngineAsString;
//    }
//  }

//  public void addSearchEngine(CvParamProvider searchEngine) {
//    if (searchEngineAsString == null) {
//      searchEngineAsString = new ArrayList<>();
//    }
//    searchEngineAsString.add(CvParamHelper.convertToString(searchEngine));
//  }
//
//  public Iterable<CvParamProvider> getSearchEngineScores() {
//    List<CvParamProvider> searchEngineScores = new ArrayList<>();
//    if (searchEngineScoreAsString != null) {
//      for (String ses : searchEngineScoreAsString) {
//        searchEngineScores.add(CvParamHelper.convertFromString(ses));
//      }
//    }
//    return searchEngineScores;
//  }
//
//  public void setSearchEngineScores(List<CvParamProvider> searchEngineScores) {
//    if (searchEngineScores != null) {
//      List<String> searchEngineScoreAsString = new ArrayList<>();
//      for (CvParamProvider searchEngineScore : searchEngineScores) {
//        searchEngineScoreAsString.add(CvParamHelper.convertToString(searchEngineScore));
//      }
//      this.searchEngineScoreAsString = searchEngineScoreAsString;
//    }
//  }
//
//  public void addSearchEngineScore(CvParamProvider searchEngineScore) {
//    if (searchEngineScoreAsString == null) {
//      searchEngineScoreAsString = new ArrayList<>();
//    }
//    searchEngineScoreAsString.add(CvParamHelper.convertToString(searchEngineScore));
//  }
//
//  public Iterable<IdentifiedModificationProvider> getModifications() {
//    List<IdentifiedModificationProvider> modifications = new ArrayList<>();
//    if (modificationsAsString != null) {
//      for (String mod : modificationsAsString) {
//        if(!mod.isEmpty()) {
//          modifications.add(ModificationHelper.convertFromString(mod));
//        }
//      }
//    }
//    return modifications;
//  }

//  public void setModifications(List<IdentifiedModificationProvider> modifications) {
//    if (modifications != null) {
//      List<String> modificationsAsString = new ArrayList<>();
//      List<String> modificationNames = new ArrayList<>();
//      List<String> modificationAccessions = new ArrayList<>();
//      for (IdentifiedModificationProvider modification : modifications) {
//        modificationsAsString.add(ModificationHelper.convertToString(modification));
//        modificationAccessions.add(modification.getAccession());
//        modificationNames.add(modification.getName());
//      }
//      this.modificationsAsString = modificationsAsString;
//      this.modificationAccessions = modificationAccessions;
//      this.modificationNames = modificationNames;
//    }
//  }
//
//  public void addModification(IdentifiedModificationProvider modification) {
//    if (modificationsAsString == null) {
//      modificationsAsString = new ArrayList<>();
//    }
//    if (modificationAccessions == null) {
//      modificationAccessions = new ArrayList<>();
//    }
//    if (modificationNames == null) {
//      modificationNames = new ArrayList<>();
//    }
//    modificationsAsString.add(ModificationHelper.convertToString(modification));
//    modificationAccessions.add(modification.getAccession());
//    modificationNames.add(modification.getName());
//  }
//
//  public Double getRetentionTime() {
//    return retentionTime;
//  }
//
//  public void setRetentionTime(Double retentionTime) {
//    this.retentionTime = retentionTime;
//  }
//
//  public Integer getCharge() {
//    return charge;
//  }
//
//  public void setCharge(Integer charge) {
//    this.charge = charge;
//  }
//
//  public Double getExpMassToCharge() {
//    return expMassToCharge;
//  }
//
//  public void setExpMassToCharge(Double expMassToCharge) {
//    this.expMassToCharge = expMassToCharge;
//  }
//
//  public Double getCalculatedMassToCharge() {
//    return calculatedMassToCharge;
//  }
//
//  public void setCalculatedMassToCharge(Double calculatedMassToCharge) {
//    this.calculatedMassToCharge = calculatedMassToCharge;
//  }
//
//  public String getPreAminoAcid() {
//    return preAminoAcid;
//  }
//
//  public void setPreAminoAcid(String preAminoAcid) {
//    this.preAminoAcid = preAminoAcid;
//  }
//
//  public String getPostAminoAcid() {
//    return postAminoAcid;
//  }
//
//  public void setPostAminoAcid(String postAminoAcid) {
//    this.postAminoAcid = postAminoAcid;
//  }
//
//  public Integer getStartPosition() {
//    return startPosition;
//  }
//
//  public void setStartPosition(Integer startPosition) {
//    this.startPosition = startPosition;
//  }
//
//  public Integer getEndPosition() {
//    return endPosition;
//  }
//
//  public void setEndPosition(Integer endPosition) {
//    this.endPosition = endPosition;
//  }

}

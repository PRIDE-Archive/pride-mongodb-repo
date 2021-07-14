package uk.ac.ebi.pride.mongodb.molecules.model.peptide;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Map;

@Data
@Builder
@Document(collection = PrideArchiveField.PEPTIDE_SUMMARY_COLLECTION_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrideMongoPeptideSummary implements PrideArchiveField {

    @Id
    @Field(PrideArchiveField.ID)
    @JsonIgnore
    private ObjectId id;

    @Field(PrideArchiveField.PEPTIDE_SEQUENCE)
    private String peptideSequence;

    @Field(PrideArchiveField.PROTEIN_ACCESSION)
    private String proteinAccession;

    @Field(EXTERNAL_PROJECT_ACCESSIONS)
    private String[] projectAccessions;

    @Field(PrideArchiveField.BEST_SEARCH_ENGINE_SCORE)
    private Double bestSearchEngineScore;

    @Field(PSMS_COUNT)
    private Integer psmsCount;

    @Field(BEST_USIS)
    private String[] bestUsis;

    @Field(PTMS_MAP)
    Map<String, String[]> ptmsMap;

}

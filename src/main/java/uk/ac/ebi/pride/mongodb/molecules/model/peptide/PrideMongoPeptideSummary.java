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
    @Indexed(name = PrideArchiveField.ID)
    @JsonIgnore
    private ObjectId id;

    @Indexed(name = PrideArchiveField.PEPTIDE_SEQUENCE)
    private String peptideSequence;

    @Indexed(name = PrideArchiveField.PROTEIN_ACCESSION)
    private String proteinAccession;

    @Indexed(name = EXTERNAL_PROJECT_ACCESSIONS)
    private String[] projectAccessions;

    @Indexed(name = PrideArchiveField.BEST_SEARCH_ENGINE_SCORE)
    private String bestSearchEngineScore;

    @Field(value = PSMS_COUNT)
    private Integer psmsCount;

    @Field(value = BEST_USIS)
    private String[] bestUsis;

    @Field(value = PTMS_MAP)
    Map<String, String[]> ptmsMap;

}

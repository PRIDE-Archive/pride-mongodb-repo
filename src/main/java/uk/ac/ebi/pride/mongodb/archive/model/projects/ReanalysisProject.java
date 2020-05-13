package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Set;

/**
 * @author Suresh Hewapathirana
 */
@Document(collection = PrideArchiveField.PRIDE_REANALYSIS_COLLECTION_NAME)
@Data
@Builder
public class ReanalysisProject {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = PrideArchiveField.ACCESSION)
    String accession;

    /** re-analysed Pubmed references **/
    @Field(value = PrideArchiveField.PRIDE_REFERENCES)
    Set<String> references;
}

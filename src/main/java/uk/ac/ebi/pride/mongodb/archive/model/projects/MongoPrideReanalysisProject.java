package uk.ac.ebi.pride.mongodb.archive.model.projects;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.archive.dataprovider.reference.Reference;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Collection;

/**
 * @author Suresh Hewapathirana
 */
@Document(collection = PrideArchiveField.PRIDE_REANALYSIS_COLLECTION_NAME)
@Data
@Builder
public class MongoPrideReanalysisProject implements PrideArchiveField {

    @Id
    @Indexed(name = ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = ACCESSION)
    String accession;

    /** References related with the re-analysed dataset in manuscript and papers **/
    @Field(value = PROJECT_REFERENCES)
    private Collection<Reference> references;
}

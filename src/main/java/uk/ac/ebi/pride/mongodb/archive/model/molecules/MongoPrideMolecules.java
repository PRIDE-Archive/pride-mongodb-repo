package uk.ac.ebi.pride.mongodb.archive.model.molecules;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.Set;


@Document(collection = PrideArchiveField.PRIDE_MOLECULES_COLLECTION_NAME)
@Data
@Builder
public class MongoPrideMolecules {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    /** Project Accession in PRIDE**/
    @Indexed(unique = true, name = PrideArchiveField.EXTERNAL_PROJECT_ACCESSION)
    String projectAccession;

    @Field(value = PrideArchiveField.PROTEIN_ACCESSIONS)
    Set<String> proteinAccessions;

    @Field(value = PrideArchiveField.PEPTIDE_ACCESSIONS)
    Set<String> peptideAccessions;
}

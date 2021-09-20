package uk.ac.ebi.pride.mongodb.archive.model.stats;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;

import java.util.List;
import java.util.Map;

@Document(collection = PrideArchiveField.PEPTIDOME_COLLECTION)
@Data
@Builder
public class MongoPeptidomeStats implements PrideArchiveField {

    @Id
    @Indexed(name = PrideArchiveField.ID)
    ObjectId id;

    @Field(value = "organism-stats")
    List<Map<String, String>> organismStats;

    @Field(value = "peptides-per-proteins-stats")
    List<Map<String, String>> peptidesPerProteinsStats;

    @Field(value = "unique-peptides-per-proteins-stats")
    List<Map<String, String>> uniquePeptidesPerProteinsStats;
}

package carlos.farra.unrn.blog.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IdOut {

    @JsonProperty("$oid")
    String id;

    public IdOut(ObjectId id) {
        this.id = id.toHexString();
    }
}

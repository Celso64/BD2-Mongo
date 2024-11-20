package carlos.farra.unrn.blog.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @BsonId
    ObjectId id;

    String title;

    String text;

    List<String> tags;

    String resume;

    List<String> relatedLinks;

    String author;

    String date;

}


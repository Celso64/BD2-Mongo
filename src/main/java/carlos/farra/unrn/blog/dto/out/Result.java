package carlos.farra.unrn.blog.dto.out;

import carlos.farra.unrn.blog.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Result {

    @JsonProperty("_id")
    IdOut id;
    String author;
    String title;
    String resume;

    public Result(Post post) {
        this.id = new IdOut(post.getId());
        this.author = post.getAuthor();
        this.title = post.getTitle();
        this.resume = post.getResume();
    }
}

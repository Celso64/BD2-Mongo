package carlos.farra.unrn.blog.dto.out;

import carlos.farra.unrn.blog.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostOut {

    @JsonProperty("_id")
    IdOut id;

    String title;
    String text;
    List<String> tags;
    String resume;
    List<String> relatedlinks;
    String author;
    String date;

    public PostOut(Post post) {
        this.id = new IdOut(post.getId());
        this.title = post.getTitle();
        this.text = post.getText();
        this.tags = (Objects.isNull(post.getTags())) ? List.of() : post.getTags();
        this.resume = post.getResume();
        this.relatedlinks = post.getRelatedLinks();
        this.author = post.getAuthor();
        this.date = post.getDate();
    }
}

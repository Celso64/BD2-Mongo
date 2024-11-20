package carlos.farra.unrn.blog.dto.out;

import carlos.farra.unrn.blog.model.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageOut {

    @JsonProperty("_id")
    IdOut id;

    String title;
    String text;
    String author;
    String date;

    public PageOut(Page page) {
        this.id = new IdOut(page.getId());
        this.title = page.getTitle();
        this.text = page.getText();
        this.author = page.getAuthor();
        this.date = page.getDate();
    }
}

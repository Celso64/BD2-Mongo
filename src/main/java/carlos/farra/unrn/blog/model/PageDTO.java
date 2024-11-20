package carlos.farra.unrn.blog.model;

import java.time.LocalDate;

public record PageDTO(String _id, String title, String text, String author, LocalDate date) {
}

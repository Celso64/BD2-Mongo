package carlos.farra.unrn.blog.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class ID {
    String $oid;

    public ID(String $oid) {
        this.$oid = $oid;
    }
}

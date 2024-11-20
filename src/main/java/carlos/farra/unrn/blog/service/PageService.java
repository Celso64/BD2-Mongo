package carlos.farra.unrn.blog.service;

import carlos.farra.unrn.blog.dto.out.PageOut;
import carlos.farra.unrn.blog.model.Page;

import java.util.Optional;
import java.util.Set;

public interface PageService {
    Optional<PageOut> findById(String idPage);

    Set<PageOut> pages();

    PageOut subirPage(Page page);
}

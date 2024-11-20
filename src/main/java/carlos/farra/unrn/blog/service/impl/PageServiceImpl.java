package carlos.farra.unrn.blog.service.impl;

import carlos.farra.unrn.blog.dto.out.PageOut;
import carlos.farra.unrn.blog.model.Page;
import carlos.farra.unrn.blog.repository.PageRepository;
import carlos.farra.unrn.blog.service.PageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {

    PageRepository pageRepository;

    @Override
    public Optional<PageOut> findById(String idPage) {
        return pageRepository.buscarPage(idPage);
    }

    @Override
    public Set<PageOut> pages() {
        return pageRepository.listarPage();
    }

    @Override
    public PageOut subirPage(Page page) {
        return pageRepository.subirPost(page);
    }
}

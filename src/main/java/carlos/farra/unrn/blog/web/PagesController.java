package carlos.farra.unrn.blog.web;

import carlos.farra.unrn.blog.dto.out.PageOut;
import carlos.farra.unrn.blog.model.Page;
import carlos.farra.unrn.blog.service.PageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/pages")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PagesController {

    PageService pageService;

    @GetMapping("/{id}")
    public ResponseEntity<Set<PageOut>> buscarPorID(@PathVariable("id") String id) {
        var resBody = pageService.findById(id);
        return resBody.map(page -> new ResponseEntity<>(Set.of(page), HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Set<PageOut>> listarPages() {
        return ResponseEntity.ok(pageService.pages());
    }

    @PostMapping
    public ResponseEntity<PageOut> subirPage(@RequestBody Page page) {
        return ResponseEntity.ok(pageService.subirPage(page));
    }
}

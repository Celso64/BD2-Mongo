package carlos.farra.unrn.blog.web;

import carlos.farra.unrn.blog.dto.out.PostOut;
import carlos.farra.unrn.blog.dto.out.Result;
import carlos.farra.unrn.blog.model.Author;
import carlos.farra.unrn.blog.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class Controller {

    PostService postService;

    @GetMapping("/latest")
    public ResponseEntity<Set<PostOut>> listarUltimosPost() {
        return new ResponseEntity<>(postService.getUltimosPost(), HttpStatus.OK);
    }

    @GetMapping("/byauthor")
    public ResponseEntity<Set<Author>> listarAutores() {
        return ResponseEntity.ok(postService.getAutores());
    }

    @GetMapping("/search/{text}")
    public ResponseEntity<Set<Result>> buscarPorContenido(@PathVariable("text") String contenido) {
        return ResponseEntity.ok(postService.buscarTexto(contenido));
    }
}

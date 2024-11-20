package carlos.farra.unrn.blog.web;

import carlos.farra.unrn.blog.dto.out.PostOut;
import carlos.farra.unrn.blog.model.Post;
import carlos.farra.unrn.blog.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/posts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostController {

    PostService postService;

    @GetMapping
    public ResponseEntity<Set<PostOut>> listarPost() {
        return new ResponseEntity<>(postService.getUltimosPost(), HttpStatus.OK);
    }

    @GetMapping("/latest")
    public ResponseEntity<Set<PostOut>> listarUltimosPost() {
        return new ResponseEntity<>(postService.getUltimosPost(), HttpStatus.OK);
    }

    @GetMapping("/author/{nombre}")
    public ResponseEntity<Set<PostOut>> buscarPorAutor(@PathVariable("nombre") String nombreAutor) {
        return ResponseEntity.ok(postService.getPostPorAutor(nombreAutor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Set<PostOut>> buscarPorId(@PathVariable("id") String idPost) {
        Optional<PostOut> body = postService.buscarPorID(idPost);
        if (body.isPresent()) {
            return ResponseEntity.ok(Set.of(body.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PostOut> subirPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.subir(post));
    }
}

package carlos.farra.unrn.blog.service;

import carlos.farra.unrn.blog.dto.out.PostOut;
import carlos.farra.unrn.blog.dto.out.Result;
import carlos.farra.unrn.blog.model.Author;
import carlos.farra.unrn.blog.model.Post;

import java.util.Optional;
import java.util.Set;

public interface PostService {
    Set<PostOut> getUltimosPost();

    Optional<PostOut> buscarPorID(String idPost);

    PostOut subir(Post post);

    Set<Author> getAutores();

    Set<PostOut> getPostPorAutor(String autor);

    Set<Result> buscarTexto(String texto);
}

package carlos.farra.unrn.blog.service.impl;

import carlos.farra.unrn.blog.dto.out.PostOut;
import carlos.farra.unrn.blog.dto.out.Result;
import carlos.farra.unrn.blog.model.Author;
import carlos.farra.unrn.blog.model.Post;
import carlos.farra.unrn.blog.repository.PostRepository;
import carlos.farra.unrn.blog.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    @Override
    public Set<PostOut> getUltimosPost() {
        return postRepository.listarPost();
    }

    @Override
    public Optional<PostOut> buscarPorID(String idPost) {
        return postRepository.buscarPost(idPost);
    }

    @Override
    public PostOut subir(Post post) {
        return postRepository.subirPost(post);
    }

    @Override
    public Set<Author> getAutores() {
        return postRepository.listarAutores();
    }

    @Override
    public Set<PostOut> getPostPorAutor(String autor) {
        return postRepository.buscarPostPorAutor(autor);
    }

    @Override
    public Set<Result> buscarTexto(String texto) {
        return postRepository.buscarPostPorContenido(texto);
    }


}

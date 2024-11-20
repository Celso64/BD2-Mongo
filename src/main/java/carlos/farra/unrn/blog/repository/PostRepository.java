package carlos.farra.unrn.blog.repository;

import carlos.farra.unrn.blog.dto.out.PostOut;
import carlos.farra.unrn.blog.dto.out.Result;
import carlos.farra.unrn.blog.model.Author;
import carlos.farra.unrn.blog.model.Post;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PostRepository {

    @Value("${mongo.url}")
    String url;

    @Value("${mongo.db}")
    String dbName;

    @Value("${mongo.documents.post}")
    String documentName;

    public Set<PostOut> listarPost() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            List<Post> resultado = new LinkedList<>();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Post> collection = mongoDatabase.getCollection(documentName, Post.class);

            try (MongoCursor<Post> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    resultado.add(cursor.next());
                }
            }
            return resultado.stream().map(PostOut::new).collect(Collectors.toSet());
        }
    }

    public Optional<PostOut> buscarPost(String idPost) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Post> collection = mongoDatabase.getCollection(documentName, Post.class);

            Post document = collection.find(Filters.eq("_id", new ObjectId(idPost))).first();
            return (Objects.isNull(document)) ? Optional.empty() : Optional.of(new PostOut(document));
        }
    }

    public Set<PostOut> buscarPostPorAutor(String author) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Post> collection = mongoDatabase.getCollection(documentName, Post.class);
            Set<PostOut> resultado = new HashSet<>();
            collection.find(Filters.eq("author", author)).forEach(p -> resultado.add(new PostOut(p)));

            return resultado;
        }
    }

    public Set<Result> buscarPostPorContenido(String contenido) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Post> collection = mongoDatabase.getCollection(documentName, Post.class);
            Set<Result> resultado = new HashSet<>();

            Document textQuery = new Document("$text", new Document("$search", contenido));
            collection.find(textQuery).forEach(p -> resultado.add(new Result(p)));
            return resultado;
        }
    }

    public Set<Author> listarAutores() {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            Set<Author> authors = new HashSet<>();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Author> collection = mongoDatabase.getCollection(documentName, Author.class);
            AggregateIterable<Author> resultado = collection.aggregate(List.of(
                    new Document("$group", new Document("_id", "$author")
                            .append("count", new Document("$sum", 1))
                    )
            ));
            try (MongoCursor<Author> cursor = resultado.iterator()) {
                while (cursor.hasNext()) {
                    authors.add(cursor.next());
                }
            }
            return authors;
        }
    }

    public PostOut subirPost(Post post) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = database.getCollection(documentName);

            Document document = new Document("title", post.getTitle())
                    .append("text", post.getText())
                    .append("tags", post.getTags())
                    .append("resume", post.getResume())
                    .append("relatedLinks", post.getRelatedLinks())
                    .append("author", post.getAuthor())
                    .append("date", post.getDate());

            InsertOneResult r = collection.insertOne(document);
            var id = Objects.requireNonNull(r.getInsertedId()).asObjectId();
            post.setId(id.getValue());
            return new PostOut(post);
        }
    }
}

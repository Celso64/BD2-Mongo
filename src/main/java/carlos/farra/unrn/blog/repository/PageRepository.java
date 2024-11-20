package carlos.farra.unrn.blog.repository;

import carlos.farra.unrn.blog.dto.out.PageOut;
import carlos.farra.unrn.blog.model.Page;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import lombok.AccessLevel;
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
public class PageRepository {

    @Value("${mongo.url}")
    String url;
    @Value("${mongo.db}")
    String dbName;
    @Value("${mongo.documents.page}")
    String documentName;


    public Optional<PageOut> buscarPage(String idPage) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Page> collection = mongoDatabase.getCollection(documentName, Page.class);
            Page document = collection.find(Filters.eq("_id", new ObjectId(idPage))).first();
            return (Objects.isNull(document)) ? Optional.empty() : Optional.of(new PageOut(document));
        }
    }

    public Set<PageOut> listarPage() {

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("carlos.farra.unrn.blog.model").build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings ms = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(url))
                .codecRegistry(pojoCodecRegistry)
                .build();

        try (MongoClient mongoClient = MongoClients.create(ms)) {
            List<Page> resultado = new LinkedList<>();
            MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
            MongoCollection<Page> collection = mongoDatabase.getCollection(documentName, Page.class);

            try (MongoCursor<Page> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    resultado.add(cursor.next());
                }
            }
            return resultado.stream().map(PageOut::new).collect(Collectors.toSet());
        }
    }

    public PageOut subirPost(Page page) {
        try (MongoClient mongoClient = MongoClients.create(url)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);

            MongoCollection<Document> collection = database.getCollection(documentName);

            Document document = new Document("title", page.getTitle())
                    .append("text", page.getText())
                    .append("author", page.getAuthor())
                    .append("date", page.getDate());

            InsertOneResult r = collection.insertOne(document);
            var id = Objects.requireNonNull(r.getInsertedId()).asObjectId();
            page.setId(id.getValue());
            mongoClient.close();
            return new PageOut(page);
        }
    }
}

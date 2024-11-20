package carlos.farra.unrn.blog.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PostIndexConfiguration {

    final String campo = "text";
    @Value("${mongo.url}")
    String url;
    @Value("${mongo.db}")
    String dbName;
    @Value("${mongo.documents.post}")
    String documentName;

    @PostConstruct
    public void crearIndexSiNoExiste() {

        MongoClient mongoClient = MongoClients.create(url);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(documentName);

        Boolean existeIndice = mongoCollection.listIndexes()
                .into(new java.util.ArrayList<>())
                .stream()
                .anyMatch(index -> {
                    Document weights = (Document) index.get("weights");
                    return weights != null && weights.containsKey(campo);
                });
        if (!existeIndice)
            mongoCollection.createIndex(new Document(campo, "text"));
    }
}

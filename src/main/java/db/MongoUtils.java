package db;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

import java.util.Collections;

import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoUtils {

    public static void deleteAll(MongoCollection<Document> collection) {
        if (collection == null) {
            throw new IllegalStateException("Mongo not initialized");
        }

        collection.deleteMany(new Document()); 
    }
    
    public static UpdateResult  updateArrayFieldToEmpty(MongoCollection<Document> collection,String id, String arrayFieldName) {

        if (collection == null) {
            throw new IllegalStateException("Mongo not initialized");
        }

        Document filter = new Document("_id", new ObjectId(id));

        Document update = new Document("$set",new Document(arrayFieldName, Collections.emptyList()));

        return collection.updateOne(filter, update);
    }
    
    public static Object findOne(MongoCollection<Document> collection,String id) {
    	return collection.find(new Document("_id", new ObjectId(id)));
    }  
}
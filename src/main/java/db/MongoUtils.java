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
    
    public static Document findOne(MongoCollection<Document> collection,String parmName,String value) {
    	return collection.find(new Document(parmName, new ObjectId(value))).first();
    }  
    
    public static void deleteOne(MongoCollection<Document> collection,String parmName,String value) {
        if (collection == null) {
            throw new IllegalStateException("Mongo not initialized");
        }

        Document filter = new Document(parmName, value);

        collection.deleteOne(filter); 
    }

}
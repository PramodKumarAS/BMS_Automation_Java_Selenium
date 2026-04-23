package data;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import config.ConfigReader;

//Singleton Pattern
public class MongoConnection {

	private static MongoClient mongoClient;
	
	private MongoConnection() 
	{
		//This stops someone from doing: new MongoConnection();
	}

	public static MongoCollection<Document> connect(String dbName,String collectionName){
	
		if(mongoClient==null) {
			synchronized(MongoConnection.class) { //Thread1 acquires a LOCK on class MongoConnection and Ensure only one creation even in race conditions
				if(mongoClient==null) {
					mongoClient=MongoClients.create(ConfigReader.get("mongo.uri"));					
				}
			}
		}
		
		MongoDatabase database=mongoClient.getDatabase(dbName);
		
		return database.getCollection(collectionName);
	}
}
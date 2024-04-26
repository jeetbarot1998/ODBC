package org.ODBC;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import java.util.ArrayList;
import java.util.List;


public class MongoConnection {

    static ConfigReader myprops = new ConfigReader();

    public static void main(String[] args) {
        String connectionString = myprops.getProperty("mongo.db.url");
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            System.out.println("=> Connection successful: " + preFlightChecks(mongoClient));
            System.out.println("=> Print list of databases:");
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
        }
        getAllEntriesFromDB(connectionString);
    }

    static void getAllEntriesFromDB(String connectionString){

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            System.out.println("=> Connection successful: " + preFlightChecks(mongoClient));

            // Retrieve all documents from "mycollection"
            MongoCollection<Document> collection = mongoClient.getDatabase("MyJavaDB").getCollection("JavaTable");
            List<Document> documents = collection.find().into(new ArrayList<>());

            System.out.println("=> All documents in 'MyJavaDB' Database and \n" +
                    "Table 'JavaTable':");
            documents.forEach(doc -> System.out.println(doc.toJson()));
        } catch (Exception e) {
            System.err.println("Error accessing MongoDB: " + e.getMessage());
        }
    }

    static boolean preFlightChecks(MongoClient mongoClient) {
        Document pingCommand = new Document("ping", 1);
        Document response = mongoClient.getDatabase("admin").runCommand(pingCommand);
        System.out.println("=> Print result of the '{ping: 1}' command.");
        System.out.println(response.toJson(JsonWriterSettings.builder().indent(true).build()));
        return response.get("ok", Number.class).intValue() == 1;
    }
}

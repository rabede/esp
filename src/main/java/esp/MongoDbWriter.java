package esp;

import java.time.LocalDateTime;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Diese Klasse schreibt Informationen in die MongoDB (auditlog) in die Collection auditentries
 * <br>
 * Achting: Aufgrund des Beispiels hier EXPLIZIT ohne Ressourcenverwaltung und Freigabe!
 * 
 * @author Michael Inden
 * 
 * Copyright 2016 by Michael Inden 
 */
public class MongoDbWriter
{
    // Missing for simplicity: clean up
    private final MongoClient               mongo      = new MongoClient("localhost", 27017);
    private final MongoDatabase             db         = mongo.getDatabase("auditlog");
    private final MongoCollection<Document> collection = db.getCollection("auditentries");

    private static final MongoDbWriter      INSTANCE   = new MongoDbWriter();

    private MongoDbWriter()
    {
    }

    public static MongoDbWriter getInstance()
    {
        return INSTANCE;
    }

    public void writeAuditLogEntry(final String className, final String methodName, final LocalDateTime timestamp)
    {
        final Document document = Document.parse("{ " + "\"class\": \"" + className + "\", " + "\"method\" : \""
                                                 + methodName + "\", " + "\"timestamp\": \"" + timestamp + "\"}");

        collection.insertOne(document);
    }
}
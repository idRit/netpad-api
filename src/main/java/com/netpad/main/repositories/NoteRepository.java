package com.netpad.main.repositories;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import com.netpad.main.entities.Note;

import org.bson.Document;
import org.bson.conversions.Bson;

public class NoteRepository {
	private MongoClient client = null;
	private MongoDatabase database = null;
	private MongoCollection<Document> collection = null;
	
    private static NoteRepository instance = null;
    public static NoteRepository getInstance(){
         if(instance == null){
             instance = new NoteRepository();
         }
         return instance;
    }
    
    private NoteRepository() {
    	MongoClientURI uri = new MongoClientURI(
    		"MONGO STR");

    	client = new MongoClient(uri);
    	database = client.getDatabase("notes");
    	collection = database.getCollection("notes");
    	System.out.println("connected");
    }
    
    public boolean insertNote(Note note) {
    	long count = collection.estimatedDocumentCount();
    	Bson filter = Filters.eq("Subject", note.subject);

        Bson update =  new Document("$set",
                      new Document()
                            .append("Subject", note.subject)
                            .append("Content", note.content));
        UpdateOptions options = new UpdateOptions().upsert(true);
  
    	collection.updateOne(filter, update, options);
    	
    	if (count == collection.estimatedDocumentCount()) {
    		return false;
    	}
    	return true;
    }
    
    public Note getNoteBySubject(String Subject) {
    	Bson filter = Filters.eq("Subject", Subject);
    	Document note = collection.find(filter).first();
    	if (note == null) {
    		return new Note();
    	}
    	return new Note(note.getString("Subject"), note.getString("Content"));
    }
    
    public boolean deleteNote(String subject) {
    	long count = collection.estimatedDocumentCount();
    	
    	collection.deleteOne(Filters.eq("Subject", subject));
    	
    	if (count == collection.estimatedDocumentCount()) {
    		return false;
    	}
    	return true;
    }
}

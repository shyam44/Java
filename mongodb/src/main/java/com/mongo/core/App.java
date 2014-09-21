package com.mongo.core;

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

/**
 * Java + MongoDB Hello world Example
 * 
 */
public class App {
	public static void main(String[] args) {

		try {

			/**** Connect to MongoDB ****/
			// Since 2.10.0, uses MongoClient
			MongoClient mongo = new MongoClient("localhost", 27017);

			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("mydb");

			/**** Get collection / table from 'mydb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection userTable = db.getCollection("user");

			/**** Insert ****/
			// create a document to store key and value
			BasicDBObject userDocument = new BasicDBObject();
			userDocument.put("name", "Amit Pawar");
			userDocument.put("age", 29);
			userDocument.put("createdDate", new Date());
			userTable.insert(userDocument);

			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "Amit Pawar");

			DBCursor cursor = userTable.find(searchQuery);

			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}

			/**** Update ****/
			// search document where name="Shyam" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "Shyam");

			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "Shyam-updated");

			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);

			userTable.update(query, updateObj);

			/**** Find and display ****/
			BasicDBObject searchQuery2 
				= new BasicDBObject().append("name", "Shyam-updated");

			DBCursor cursor2 = userTable.find(searchQuery2);

			while (cursor2.hasNext()) {
				System.out.println(cursor2.next());
			}

			BasicDBObject deleteQuery = new BasicDBObject();
			deleteQuery.put("name", "mkyong");
			userTable.remove(deleteQuery);
			/**** Done ****/
			System.out.println("Done");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}

package com.mhealthgenie.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

public class MongoDbUtil {

	public static DBCollection getConnection() {
		DBCollection collection = null;
		//MongoClient mongo;
		try {
			Mongo mongo = new Mongo("localhost");
			
			DB db = mongo.getDB("UserAction");
			collection = db.getCollection("userDatabase");
			
		} catch (UnknownHostException e) {

			e.printStackTrace();
		}
		return collection;
	}

}

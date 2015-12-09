package com.mhealthgenie.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.mhealthgenie.util.Constants;
import com.mhealthgenie.util.MongoDbUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

@Path("test")
public class TestMongoDB 
{
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("UserTracking")
	public JSONObject UserAction(String rcvdJSON) throws JSONException

	{
		long userId = 0;
		JSONObject json1 = new JSONObject();

		System.out.println("##################  UserAction  entered  ################" + rcvdJSON);

		Constants.LOGGER.info("##################   UserAction  entered  ################");

		if (rcvdJSON != null) {
			try {
				JSONObject receivedJSON = null;
				receivedJSON = new JSONObject(rcvdJSON);
				userId = Long.parseLong(receivedJSON.getString("userId") + "");
				String location = receivedJSON.getString("location");
				String itemType = receivedJSON.getString("item_type");
				float latitude = Float.parseFloat(receivedJSON.getString("latitude"));
				float longitude = Float.parseFloat(receivedJSON.getString("longitude"));
				String searchParam = receivedJSON.getString("search_param");
				String Receiveddate = (receivedJSON.getString("date") + "");
				String actionType = receivedJSON.getString("action_type");
				// String actionData=receivedJSON.getString("action_data");
				JSONObject actionData = receivedJSON.getJSONObject("action_data");
				// JSONArray actionDataArr =
				// receivedJSON.getJSONArray("action_data");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = null;

				date = df.parse(Receiveddate);
				// DBObject dbObject = (DBObject) JSON.parse(rcvdJSON);
				DateFormat outputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formattedDate = outputformat.format(date);
				// Get the Connection Object
				DBCollection collection = MongoDbUtil.getConnection();
				DBObject query = new BasicDBObject("action_type", actionType);
				DBCursor cursor = collection.find(query);
				BasicDBObject obj = null;
				String action = null;
				if (cursor.size() > 0) {

					while (cursor.hasNext()) {

						obj = (BasicDBObject) cursor.next();

						action = obj.getString("action_type");

						System.out.println("value" + action.equalsIgnoreCase("Restaurantsearch"));
						if (action.equalsIgnoreCase("Restaurantsearch")) {

							
						
							BasicDBObject document = new BasicDBObject();
							document.put("username", "user");
							collection.insert(document);
							
							BasicDBObject document1 = new BasicDBObject();
							document1.put("title", "test1");

							BasicDBObject document2 = new BasicDBObject();
							document2.put("title", "test2");
							BasicDBObject updateCommand1 = new BasicDBObject();
							updateCommand1.put("$addToSet", new BasicDBObject("test", document2));
							collection.update( document1, updateCommand1);

							BasicDBObject updateCommand = new BasicDBObject();
							updateCommand.put("$addToSet", new BasicDBObject("test", document1));
							collection.update( document, updateCommand);
					    
					    

						} else if (action.equalsIgnoreCase("ItemSearch")) {
                                                  //same code with different action data

						} else if (action.equalsIgnoreCase("Packaged Goods")) {

						} else if (action.equalsIgnoreCase("Common Foods")) {

						} else if (action.equalsIgnoreCase("School Menus")) {

						}

					}
				

				}

				else {
					BasicDBObject document = new BasicDBObject();
					document.put("userId", userId);
					document.put("location", location);
					document.put("item_type", itemType);
					document.put("latitude", latitude);
					document.put("longitude", longitude);
					document.put("search_param", searchParam);
					document.put("date", formattedDate);
					document.put("action_type", actionType);
					document.put("action_data", actionData);
					// DBObject dbObject = (DBObject) JSON.parse(actionDataArr);

					DBObject dbObject = (DBObject) JSON.parse((rcvdJSON));
					collection.insert(dbObject);
					System.out.println("inserted successfully");
		      
		     

				}

				

				json1.put("result", "success");
				json1.put("userId", userId);
				

			}

			catch (JSONException | ParseException e) {
				try {

					json1.put("result", "failure");
					json1.put("userId", userId);
				} catch (JSONException e1) {

					json1.put("result", "failure");
					json1.put("userId", userId);
					e1.printStackTrace();
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		} else {
			json1.put("result", "failure");
			json1.put("userId", userId);
		}
		// Step.5 (Flush the session)

		// Step.6 (Close the Session)

		// factory.close();

		Constants.LOGGER.info(json1.toString());
		System.out.println(json1.toString());
		return json1;

	}

}

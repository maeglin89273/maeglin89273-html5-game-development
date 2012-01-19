/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.asset;

import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * @author Maeglin Liao
 *
 */
public class JsonFile extends Asset {
	private JSONValue content=JSONNull.getInstance();
	/**
	 * @param fullPath
	 */
	public JsonFile(String url) {
		super(url);
		
	}
	public JsonFile(){
		this("");
	}
	public JsonFile(String url,String contentText){
		this(url);
		set(contentText);
	}
	public void set(String jsonContentText){
		content=JSONParser.parseStrict(jsonContentText);
	}
	public JSONValue getJsonValue(){
		return content;
	}
	@Override 
	public int hashCode(){
		return 9;
	}
}

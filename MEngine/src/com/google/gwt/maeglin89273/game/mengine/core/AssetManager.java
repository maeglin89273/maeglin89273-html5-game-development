package com.google.gwt.maeglin89273.game.mengine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.maeglin89273.game.mengine.asset.Asset;
import com.google.gwt.maeglin89273.game.mengine.asset.AssetsBundleWithLookup;
import com.google.gwt.maeglin89273.game.mengine.asset.JsonFile;
import com.google.gwt.maeglin89273.game.mengine.asset.sprite.SpriteSheet;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

public class AssetManager {
	
	private Map<String,Asset> assets=new HashMap<String,Asset>();
	
	private boolean loaded=true;
	
	private int loadedCount=0;
	private int errorsCount=0;
	private int assetsCount=0;
	
	private List<DataLoadedListener> dataLoadedListeners=new ArrayList<DataLoadedListener>();
	
	private String prefix;
	
	AssetManager(String prefix){
		if(!prefix.endsWith("/")){
			prefix+="/";
		}
		this.prefix=prefix;
	}
	public String getAssetsPrefix(){
		return prefix;
	}
	public int getLoadedPercentage(){
		return Math.round((((float)loadedCount)/assetsCount)*100);
	}
	private void checkDoesLoadDone(){
		if(loadedCount==assetsCount){
			loaded=true;
			for(int i=dataLoadedListeners.size()-1;i>=0;i--){
				dataLoadedListeners.get(i).done();
				dataLoadedListeners.remove(i);
			}
			dataLoadedListeners=null;
		}
	}
	public int getErrorsCount(){
		return this.errorsCount;
	}
	public boolean isDataLoaded(){
		return loaded;
	}
	public void addDataLoadedListener(DataLoadedListener d){
		dataLoadedListeners.add(d);
	}
	
	public void loadAssetsBundle(AssetsBundleWithLookup bundle){
		
		if(bundle!=null){
			loaded=false;
			ResourcePrototype[] types=bundle.getResources();
			assetsCount+=types.length;
			LoadingAssetsCommand command=new LoadingAssetsCommand(types);
			Scheduler.get().scheduleIncremental(command);
		}else{
			checkDoesLoadDone();
		}
		
	}
	private void assetOnLoad(Asset asset){
		asset.onLoad();
		this.loadedCount++;
		checkDoesLoadDone();
	}
	private void assetOnError(Asset asset,Throwable e) {
		asset.onError(e);
		this.errorsCount++;
	}
	
	
	public SpriteSheet getSpriteSheet(String path){
		return (SpriteSheet)getAsset(path);
		
	}
	public JsonFile getJson(String path){
		return (JsonFile)getAsset(path);
	}
	public Asset getAsset(String path){
		path=prefix+path;
		Asset toReturn=assets.get(path);
		if(toReturn==null){
			String key=path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('.'));
			toReturn=assets.get(key);
			if(toReturn==null){
				throw new IllegalArgumentException("The file \""+path+"\" is not found. " +
					"Please assured that it's under the\""+prefix+"\" directory. Otherwise," +
							"the file hasn't been loaded.");
			}
			assets.remove(key);
			assets.put(path, toReturn);
		}
		return toReturn;
	}
	
	public interface DataLoadedListener{
		public void done();
	}
	
	private class LoadingAssetsCommand implements Scheduler.RepeatingCommand {
		
		private ResourcePrototype[] prototypes;
		private int index=0;
		private LoadingAssetsCommand(ResourcePrototype[] prototypes){
			this.prototypes=prototypes;
			
		}
		@Override
		public boolean execute() {
			ResourcePrototype prototype=prototypes[index];
			Asset asset;
			if(prototype instanceof ImageResource){
				asset=generateSpriteSheet((ImageResource)prototype);
				
			}else if(prototype instanceof DataResource){
				String url=((DataResource) prototype).getSafeUri().asString();
				if(url.endsWith(".json")){
					asset=generateJsonFile((DataResource)prototype);
				}else{
					return willThisContinue();
				}
			}else{
				return willThisContinue();
			}
			
			assets.put(prototype.getName(),asset);
			return willThisContinue();
			
			
		}
		private JsonFile generateJsonFile(DataResource dataRes){
			final JsonFile json=new JsonFile(dataRes.getSafeUri().asString());
			RequestBuilder reqBuilder = new RequestBuilder(RequestBuilder.GET, json.getUrl());
			try {
				reqBuilder.sendRequest(null, new RequestCallback(){

					@Override
					public void onResponseReceived(Request request,Response response) {
						json.set(response.getText());
						assetOnLoad(json);
					}

					@Override
					public void onError(Request request, Throwable exception) {
						assetOnError(json,exception);
						
					}
					
				});
			} catch (RequestException e) {
				assetOnError(json,e);
			}
			return json;
		}
		private SpriteSheet generateSpriteSheet(final ImageResource imgRes){
			ImageElement imageE=Document.get().createImageElement();
			imageE.setSrc(imgRes.getSafeUri().asString());
			final SpriteSheet toReturn=new SpriteSheet(imageE);
			
			Event.sinkEvents(imageE, Event.ONLOAD|Event.ONERROR);
			Event.setEventListener(imageE, new EventListener(){

				@Override
				public void onBrowserEvent(Event event) {
					if(event.getTypeInt()==Event.ONLOAD){
						assetOnLoad(toReturn);
					}else if(event.getTypeInt()==Event.ONERROR){
						assetOnError(toReturn,new ResourceException(imgRes,"Error:can not fetch the file."));
					}else{
						return;
					}
				}
			});
			
			return toReturn;
		}
		
		private boolean willThisContinue(){
			if(++index==prototypes.length){
				prototypes=null;
				return false;
			}else{
				return true;
			}
		}
		
	}
}

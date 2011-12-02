/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.core;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.ConfigurationProperty;
import com.google.gwt.core.ext.linker.EmittedArtifact;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.Shardable;
import com.google.gwt.core.linker.CrossSiteIframeLinker;




/**
 * @author Maeglin Liao
 *
 */
@Shardable
@LinkerOrder(LinkerOrder.Order.POST)
public class AppCacheLinker extends AbstractLinker{
	
	private static final String MANIFEST = "appcache.manifest";
    private static final String CONFIG_PROPERTY = MANIFEST;
    private static final HashSet<String> VALID_EXTENSIONS=new HashSet<String>(Arrays.asList(
    		new String[]{ "html","js","jpg", "jpeg", "png", "gif", "mp3", "ogg", "mov", "avi", "wmv","webm", "css", "json", "flv", "swf"}));
    
        
        
	/* (non-Javadoc)
	 * @see com.google.gwt.core.ext.Linker#getDescription()
	 */
	@Override
	public String getDescription() {
		return "MEngine AppCacheLinker";
	}
	@Override
	  public ArtifactSet link(TreeLogger logger, LinkerContext context, ArtifactSet artifacts,boolean onePurmutation)
	      throws UnableToCompleteException {
		if(onePurmutation)
			return artifacts;
	    //ArtifactSet toReturn = super.link(logger, context, artifacts);
		ArtifactSet toReturn = new ArtifactSet(artifacts);
	    StringBuilder sb=new StringBuilder("CACHE MANIFEST\n");
	    
	    sb.append("# Unique id #" + (new Date()).getTime() +"."+ Math.random() + "\n");
	    sb.append("CACHE:\n");
	    sb.append("# Note: must change this every time for cache to invalidate\n");
	    sb.append("\n");
	    sb.append("#Generated app files\n");
	    sb.append(getPublicSourceFiles(logger, context, toReturn));
	    sb.append("# Static app files\n");
	    sb.append(getPropertiesExtraFiles(logger, context));
	    sb.append("\n\n");
	    sb.append("#All other resources require the user to be online.\n");
	    sb.append("NETWORK:\n");
	    sb.append("*\n");
	    
	    logger.log(TreeLogger.DEBUG, "Make sure you have the following"
	            + " attribute added to your landing page's <html> tag: <html manifest=\""
	            + context.getModuleFunctionName() + "/" + MANIFEST + "\">");
	    // Create the general cache-manifest resource artifact
	    toReturn.add(this.emitString(logger, sb.toString(), MANIFEST));
	    
	    return toReturn;
	  }
	private String getPublicSourceFiles(TreeLogger logger,LinkerContext context,ArtifactSet artifacts){
		StringBuilder sb=new StringBuilder();
		String path;
		for(@SuppressWarnings("rawtypes")Artifact artifact:artifacts){
			if(artifact instanceof EmittedArtifact){
				path="/" + context.getModuleFunctionName() + "/" + ((EmittedArtifact)artifact).getPartialPath();
				if(isValidPath(path)){
					sb.append(path+"\n");
				}
			}
		}
		return sb.toString();
	}
	private boolean isValidPath(String path){
		// GWT Development Mode files
	    if (path.equals("hosted.html") || path.endsWith(".devmode.js")) {
	      return false;
	    }

	    // Default or welcome file
	    if (path.equals("/")) {
	      return true;
	    }

	    // Whitelisted file extension
	    int pos = path.lastIndexOf('.');
	    if (pos != -1) {
	      String extension = path.substring(pos + 1);
	      if (VALID_EXTENSIONS.contains(extension)) {
	        return true;
	      }
	    }

	    // Not included by default
	    return false;
	}
	private String getPropertiesExtraFiles(TreeLogger logger,LinkerContext context){
		StringBuilder sb=new StringBuilder();
		if(context.getConfigurationProperties().isEmpty()){
			logger.log(Type.INFO, "Info: No external static options have been configured," +
									" configuration-property cache.manifestis empty!");
        }else{
			for(ConfigurationProperty cp:context.getConfigurationProperties()){
				if(cp.getName().equalsIgnoreCase(CONFIG_PROPERTY)){
					for(String values:cp.getValues()){
						sb.append(values+"\n");
					}
					break;
				}
			}
		}
        return sb.toString();
		
	}
}

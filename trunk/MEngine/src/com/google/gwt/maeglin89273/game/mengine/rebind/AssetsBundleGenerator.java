/**
 * 
 */
package com.google.gwt.maeglin89273.game.mengine.rebind;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.resources.client.ClientBundleWithLookup;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ResourcePrototype;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * @author Maeglin Liao
 *
 */
public class AssetsBundleGenerator extends Generator {
	private static final String WEB_INF_CLASSES="WEB-INF/classes/";
	private static final Map<String,Class<? extends ResourcePrototype>> EXTENSION_TYPE_MAP=new HashMap<String,Class<? extends ResourcePrototype>>();
	private static FileFilter dirFilter=new FileFilter(){

		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
		
	};
	private static FileFilter fileFilter=new FileFilter(){

		@Override
		public boolean accept(File pathname) {
			return pathname.isFile();
		}
		
	};
	static {
		EXTENSION_TYPE_MAP.put(".png", ImageResource.class);
		EXTENSION_TYPE_MAP.put(".jpg", ImageResource.class);
		EXTENSION_TYPE_MAP.put(".jpeg", ImageResource.class);
		EXTENSION_TYPE_MAP.put(".gif", ImageResource.class);
		EXTENSION_TYPE_MAP.put(".json", DataResource.class);
	}
	

	/* (non-Javadoc)
	 * @see com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger, com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		JClassType userType;
		try {
			userType=context.getTypeOracle().getType(typeName);
		} catch (NotFoundException e) {
			logger.log(Type.ERROR, "cannot find the type:"+typeName, e);
			throw new UnableToCompleteException();
		}
		if(userType.isInterface()==null){
			logger.log(Type.ERROR, typeName+"is not an interface.");
			throw new UnableToCompleteException();
		}
		
		String packageName=userType.getPackage().getName();
		String implClassName=userType.getName()+"Impl"; 
		File baseDir=getClassesFile(logger);
		
		Set<File> files=getInnerFiles(new File(baseDir,packageName.replace('.', '/')));
		Set<String> methodNames=new HashSet<String>();
		
		ClassSourceFileComposerFactory cf=new ClassSourceFileComposerFactory(packageName,implClassName);
		
		cf.addImport(ClientBundleWithLookup.class.getCanonicalName());
		cf.addImport(GWT.class.getCanonicalName());
		cf.addImport(ResourcePrototype.class.getCanonicalName());
		cf.addImport(ImageResource.class.getCanonicalName());
		cf.addImport(TextResource.class.getCanonicalName());
		cf.addImport(DataResource.class.getCanonicalName());
		
		cf.addImplementedInterface(userType.getQualifiedSourceName());
		
		PrintWriter pw=context.tryCreate(logger, packageName, implClassName);
		if(pw!=null){
			SourceWriter sw=cf.createSourceWriter(context, pw);
			
			logger.log(Type.INFO, "Start generating assets bundle...");
			sw.println("@Override");
			sw.println("public ResourcePrototype[] getResources() {");
			sw.indent();
			sw.print("return MyClientBundleWithLookup.INSTANCE.getResources();");
			sw.outdent();
			sw.println("}");
			
			sw.println("static interface MyClientBundleWithLookup extends ClientBundleWithLookup{");
			sw.indent();
			sw.println("MyClientBundleWithLookup INSTANCE=GWT.create(MyClientBundleWithLookup.class);");
			
			for(File file:files){
				String sourcePath=file.getPath().replace(baseDir.getPath()+"\\","").replace('\\', '/');
				Class<? extends ResourcePrototype> returnType=matchReturnType(file.getName());
				if(returnType==null){
					continue;
				}
				String methodName=stripExtension(file.getName());
				
				if(!isValidMethodName(methodName)){
					logger.log(TreeLogger.WARN, "Skipping invalid method name (" + methodName + ") due to: "
				              + sourcePath);
				          continue;
				}else if(!methodNames.add(methodName)){
					logger.log(TreeLogger.WARN, "Skipping duplicate method name :"+methodName+
							". Maybe you have the files with the same name in different directories.");
				}
				logger.log(TreeLogger.DEBUG, "Generating method for: " + sourcePath+", and method name is: "+methodName);
				sw.println("@Source(\""+sourcePath+"\")");
				sw.println("public "+returnType.getName()+" "+methodName+"();");
			}
			
			sw.outdent();
			sw.println("}");
			
			sw.commit(logger);
			logger.log(Type.INFO, "Generated assets bundle successfully.");
		}
		return cf.getCreatedClassName();
	}
	private Class<? extends ResourcePrototype> matchReturnType(String filename) {
		return EXTENSION_TYPE_MAP.get(getExtension(filename));
		
	}

	
	private String stripExtension(String filename){
		return filename.substring(0, filename.lastIndexOf('.'));
	}
	private String getExtension(String filename) {
		return filename.substring(filename.lastIndexOf('.')).toLowerCase();
	}
	private boolean isValidMethodName(String methodName){
		return methodName.matches("^[a-zA-Z_$][a-zA-Z0-9_$]*$");
	}
	private File getClassesFile(TreeLogger logger) throws UnableToCompleteException{
		File warDir=new File(".");
		try {
			if(warDir.getCanonicalPath().endsWith("war")){
				return new File(warDir,WEB_INF_CLASSES);
			}else{
				return new File("war/"+WEB_INF_CLASSES);
			}
			
		} catch (IOException e) {
			logger.log(Type.ERROR, "Failed to get canonical path.",e);
			throw new UnableToCompleteException();
		}
	}
	private Set<File> getInnerFiles(File dir){
		if(!dir.isDirectory()){
			return null;
		}
		Set<File> innerFiles=new HashSet<File>();
		File[] dirs=dir.listFiles(dirFilter);
		innerFiles.addAll(Arrays.asList(dir.listFiles(fileFilter)));
		for(File innerDir:dirs){
			innerFiles.addAll(getInnerFiles(innerDir));
		}
		return innerFiles; 
	}
	
}

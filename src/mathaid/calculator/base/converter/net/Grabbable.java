/**
 * 
 */
package mathaid.calculator.base.converter.net;

import java.util.List;
import java.util.Map;

import mathaid.calculator.base.converter.net.HttpRequest.HeaderField;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 02:45:40---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: Grabbable.java------------------------------------------------------ 
 * Class name: Grabbable------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Grabbable {
	String getBase();
	boolean canLeaveBase();
	void setLeaveBase(boolean can);
	List<Resource<?>> getResources();
	void exempt(String fileExtension);
	
	interface WebGrabbable extends Grabbable {
		void setAuth(java.net.URL url, String ...params);
		void setField(java.net.URL url, String key, String attr);
		Map<String, String> getAuth(java.net.URL url);
		HeaderField getHeaderField(java.net.URL url);
		void exempt(java.net.URL url);
	}
	
	interface FileGrabbable extends Grabbable {
		void exempt(java.io.File file);
	}
}

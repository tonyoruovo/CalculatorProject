/**
 * 
 */
package mathaid.calculator.base.converter.net;

import java.util.HashMap;
import java.util.Map;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 20:13:19---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: HttpRequest.java------------------------------------------------------ 
 * Class name: HttpRequest------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public final class HttpRequest {
	
	public static final String CRLF = "\r\n";
	public static final String ILLEGALCHARS = "*/ ?&" + CRLF;
	
	private static void varArgsAsMap(Map<String, String> map, String ...args) {
		for(int i = 0; i < args.length; i += 2) {
			map.put(validateRequestLineField(args[i]),
					validateRequestLineField(args[i + 1]));
		}
	}
	
	private static String validateRequestLineField(String value) {
		for(char character : CRLF.toCharArray())
			if(value.contains(String.valueOf(character)))
				throw new IllegalArgumentException();
		return value;
	}
	
	public final class HeaderLine {
		
		HeaderLine(String method, String version, java.net.URI path, String... queryKeysAndParams) {
			this.method = validateRequestLineField(method);
			this.path = path.toASCIIString();
			this.version = validateRequestLineField(version);
			this.query = new HashMap<>();
			varArgsAsMap(query, queryKeysAndParams);
		}
		
		public String getMethod() {
			return method;
		}
		
		public java.net.URI getPath() throws java.net.URISyntaxException {
			return new java.net.URI(path);
		}
		
		public String getVersion() {
			return version;
		}
		
		public Map<String, String> getQuery() {
			return query;
		}
		
		private String getHttpQueryParamAsString() {
			if(query.isEmpty())
				return "";
			StringBuilder sb = new StringBuilder("?");
			for(String key : query.keySet()) {
				sb.append(key + "=");
				sb.append(query.get(key));
				sb.append("&");
			}
			sb.replace(sb.length() - 1, sb.length(), "");//remove the last &
			
			return sb.toString();
		}
		
		@Override
		public String toString() {
			return method.toUpperCase() + " " + path + getHttpQueryParamAsString() + " " + version + CRLF;
		}
		
		final String method;//Case-sensitive
		final String path;
		final String version;
		final Map<String, String> query;
	}
	
	public final class HeaderField {
		
		@Override
		public String toString() {
			return null;
		}
		
		String accept;
		String acceptCharset;
		String acceptEncoding;
		String acceptLanguage;
		String authourisation;
		String expect;
		String from;
		String host;
		String ifMatch;
		String ifModifiedSince;
		String ifNoneMatch;
		String ifRange;
		String ifUnmodifiedSince;
		String maxForwards;
		String proxyAuthorisation;
		String range;
		String referer;
		String tE;
		String userAgent;
		Map<String, String> custom;
	}

}

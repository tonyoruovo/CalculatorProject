/**
 * 
 */
package mathaid.calculator.base.converter.net;

import java.util.List;
import java.util.Map;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 02:39:01---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: Crawlable.java------------------------------------------------------ 
 * Class name: Crawlable------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface Crawlable<T> {

	T crawl(String path);
	
	List<T> visited();
	
	int getLevel();

	interface WebCrawlable extends Crawlable<java.net.URL> {
		Map<String, Integer> getBadLinks();
		
		Map<String, Integer> getDeadEndLinks();
	}
	
	interface FileCrawlable extends Crawlable<java.io.File> {
	}
}

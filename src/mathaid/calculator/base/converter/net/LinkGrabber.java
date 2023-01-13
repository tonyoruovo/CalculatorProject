/**
 * 
 */
package mathaid.calculator.base.converter.net;

import java.util.ArrayList;
import java.util.List;

import mathaid.calculator.base.converter.net.Crawlable.WebCrawlable;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 11:35:54---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: LinkGrabber.java------------------------------------------------------ 
 * Class name: LinkGrabber------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class LinkGrabber implements Grabbable {

	public LinkGrabber(WebCrawlable crawler, java.net.URL baseUrl) {
		this.crawler = crawler;
		this.resources = new ArrayList<>();
		this.baseUrl = baseUrl;
	}
	
	protected WebCrawlable getCrawler() {
		return crawler;
	}
	
	protected List<Resource<?>> resources;
	protected WebCrawlable crawler;
	protected java.net.URL baseUrl;

}

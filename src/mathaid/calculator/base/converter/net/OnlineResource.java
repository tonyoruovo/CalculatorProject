/**
 * 
 */
package mathaid.calculator.base.converter.net;

import java.io.Externalizable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;

/*
 * Date: 2 May 2022----------------------------------------------------------- 
 * Time created: 01:52:55---------------------------------------------------  
 * Package: mathaid.calculator.base.converter.net------------------------------------------------ 
 * Project: LatestProject------------------------------------------------ 
 * File: OnlineResource.java------------------------------------------------------ 
 * Class name: OnlineResource------------------------------------------------ 
 */
/**
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public interface OnlineResource<T extends java.io.Serializable & Comparable<T>> extends Resource<T> {
	java.net.URL getUrl();

	final class HTML implements OnlineResource<String>, Externalizable {

		public HTML(String content) {
			this.content = Jsoup.parse(content);
		}

		public HTML(java.net.URL url) {
			try {
				content = Jsoup.parse(url, 1_000_000);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public HTML(java.io.File file) {
			try {
				content = Jsoup.parse(file, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public String getPath() {
			return content.baseUri();
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public String getFileExtension() {
			return "html";
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 * @throws FileNotFoundException
		 */
		@Override
		public java.io.File toFile() throws FileNotFoundException {
			return new java.io.File(getPath());
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public BigInteger bytes() {
			try {
				return BigInteger.valueOf(toFile().length());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public Data<String> get() {
			return null;// Does not have an inner data
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @param o
		 * @return
		 */
		@Override
		public int compareTo(Resource<?> o) {

			return bytes().compareTo(o.bytes()) == 0 ? getFileExtension().compareTo(o.getFileExtension())
					: bytes().compareTo(o.bytes());
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 20:01:46--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * 
		 * @return
		 */
		@Override
		public java.net.URL getUrl() {
			try {
				return new java.net.URL(content.location());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return null;
		}

		public List<String> getElementsByClassName(String className) {
			org.jsoup.select.Elements elems = content.getElementsByClass(className);
			List<String> elements = new ArrayList<>();
			for (org.jsoup.nodes.Element e : elems)
				elements.add(e.html());
			return elements;
		}

		public List<String> getElementsByTagName(String tagName) {
			org.jsoup.select.Elements elems = content.getElementsByTag(tagName);
			List<String> elements = new ArrayList<>();
			for (org.jsoup.nodes.Element e : elems)
				elements.add(e.html());
			return elements;
		}

		public String getElementById(String id) {
			return content.getElementById(id).html();
		}

		public List<String> getElementByAttribute(String attrName) {
			org.jsoup.select.Elements elems = content.getElementsByAttribute(attrName);
			List<String> elements = new ArrayList<>();
			for (org.jsoup.nodes.Element e : elems)
				elements.add(e.html());
			return elements;
		}

		public String getHead() {
			return content.head().html();
		}

		public String getBody() {
			return content.body().html();
		}

		@Override
		public String getContent() {
			return content.toString();
		}

		private org.jsoup.nodes.Document content;

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 21:19:00--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param out
		 * @throws IOException
		 */
		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			// TODO Auto-generated method stub
			
		}

		/*
		 * Most Recent Date: 5 May 2022-----------------------------------------------
		 * Most recent time created: 21:19:00--------------------------------------
		 */
		/**
		 * {@inheritDoc}
		 * @param in
		 * @throws IOException
		 * @throws ClassNotFoundException
		 */
		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			// TODO Auto-generated method stub
			
		}
	}
}

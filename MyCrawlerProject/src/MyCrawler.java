import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.HttpStatus;
import org.apache.http.impl.EnglishReasonPhraseCatalog;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.exceptions.ContentFetchException;
import edu.uci.ics.crawler4j.crawler.exceptions.PageBiggerThanMaxSizeException;
import edu.uci.ics.crawler4j.crawler.exceptions.ParseException;
import edu.uci.ics.crawler4j.fetcher.PageFetchResult;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.NotAllowedContentException;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.url.WebURL;
/**
 * @author Aurobind Iyer
 */

public class MyCrawler extends WebCrawler {
	
	 private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mp3|mp3|zip|gz|xml|json|ppt|mp4|wav|avi|mov|mpeg|mpg|m4v|swf|rar|wmv|wma|3gp|rm|mpa|vob|xls|xlsx|xlr))$");
	// private static final Pattern imgPatterns = Pattern.compile(".*(\\.(html|pdf|bmp|gif|jpe?g|png))$");
			 /**
			 * This method receives two parameters. The first parameter is the page
			 * in which we have discovered this new url and the second parameter is
			 * the new url. You should implement this function to specify whether
			 * the given url should be crawled or not (based on your crawling logic).
			 * In this case, we didn't need the
			 * referringPage parameter to make the decision.
			 */
	 @Override
	 public boolean shouldVisit(Page referringPage, WebURL url) {
		 
		 String href = url.getURL().toLowerCase();
		 try {
		// Page x = new Page(url);
			 FileWriter file = new FileWriter("C:\\Users\\Auro\\Desktop\\urls_Chicago_Tribune1.csv",true);
					 
			 if(href.startsWith("https://www.chicagotribune.com/") || href.startsWith("http://www.chicagotribune.com/") || href.startsWith("http://chicagotribune.com/"))
			 	{
					String s = href.replace(",","-");
					file.append(s); //amp
					file.append(",");
					file.append("OK");
					file.append('\n');  						 	
				}
			 else
				{
					String isComma = href.replace(",","-");
					file.append(isComma);
					file.append(",");
					file.append("N_OK");
					file.append('\n');
				 }
				 
				 file.flush();
			}
			catch(Exception e) {}
				 //if the url has extension as in filters then matcher will give true and the " ! " in frot of filters will give false
				 //and vice versa
			return !FILTERS.matcher(href).matches()
				 && (href.startsWith("https://www.chicagotribune.com/") || href.startsWith("http://www.chicagotribune.com/") || href.startsWith("http://chicagotribune.com/"));
		}
			 
	 /**
	  * This function is called when a page is fetched and ready
	  * to be processed by your program.
	  */
	 @Override
	 public void visit(Page page) {
		 try {
			  	String url = page.getWebURL().getURL();
			  	FileWriter file = new FileWriter("C:\\Users\\Auro\\Desktop\\visit_Chicago_Tribune.csv",true);
			  	//int status = page.getStatusCode();
			  
			  	String content = page.getContentType();
			  	content = content.substring(0, content.indexOf(";"));
			  	Double num = (double) page.getContentData().length;
			  	Double deno = (double) 1024;
			  	Double ans = num/deno;
			  	String psize = Double.toString(ans)+" kb";

			  if (page.getParseData() instanceof HtmlParseData) {
				  	HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
				  	Set<WebURL> links = htmlParseData.getOutgoingUrls();
				  	String z = url.replace(",", "-");
				  	file.append(z);
				  	file.append(",");
				  	file.append(psize);
				  	file.append(",");
				  	file.append(links.size()+"");
				  	file.append(",");
				  	file.append(content);
				  	file.append('\n');
			  }
			  
			  if(page.getParseData() instanceof BinaryParseData) {
				  	BinaryParseData binary = (BinaryParseData) page.getParseData();
				  	Set<WebURL> links = binary.getOutgoingUrls();
				  	String comma = url.replace(",", "-");
				  	file.append(comma);
				  	file.append(",");
				  	file.append(psize);
				  	file.append(",");
				  	file.append(links.size()+"");
				  	file.append(",");
				  	file.append(content);
				  	file.append('\n');
			  }
			 
			  file.flush();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			}
	}
	
		//Method to handle different page status codes	
		@Override
		protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
			try {
					FileWriter file = new FileWriter("C:\\Users\\Auro\\Desktop\\fetch_Chicago_Tribune.csv",true);
					String url = webUrl.toString();
					url = url.replace(",", "-");
					file.append(url);
					file.append(",");
					file.append(statusCode+"");
					file.append('\n');
					
					file.flush();
				}
				  catch(Exception e) {}
		}
}

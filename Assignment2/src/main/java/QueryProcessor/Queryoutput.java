package QueryProcessor;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.tartarus.snowball.ext.PorterStemmer;


public class Queryoutput {
	
	public static void main(String[] args) throws IOException, ParseException {

		
		Queryoutput cr = new Queryoutput();
		
		/*
		 * This method is used for crawling and scraping. 
		 * The data is already crawled and scraped but if you still want to crawl the 
		 * data again uncomment this method and run the method.
		 * You would not need to change anything.The program takes care of it.
		 */
		//cr.crawler();
		/*
		 * This method is used to index the crawled data.
		 * Apache Lucene is used to crawl the data.
		 * This method should be invoked only after the crawl method has been invoked atleast once.
		 * This method does not need any changes.The program takes care of it.Just put the indexdir
		 * inside /src/main/resources for the web-app to catch the location.
		 */
		//cr.indexer();
		/*
		 * NOTE:Some times the crawler works slow so the Jsoup socket may not connect.
		 * Please Run the crawler and indexer again.
		 */
		
		
		/*
		 * This data is used to query the indexed data and get the output.
		 * There is quite a bit of pre-processing done before actually passing through
		 * the Standard Analyzer.
		 */
		/*
		 * Dont use this from here.Handled by servlet.
		 */
		//cr.queryprocessor();
		
	}

		
	public void crawler() throws IOException{
		
		Document doc = Jsoup.connect("https://en.wikibooks.org/wiki/Java_Programming").get();
		Elements link = doc.select("div#mw-content-text>ul>li>a[href]");
		String[] keywords=new String[link.size()];
		
		
		File corpus = new File("corpus");
		// if the directory does not exist, create it
		if (!corpus.exists()) {
		    boolean result = false;

		    try{
		        corpus.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        System.out.println("The dir could not be created!!Please manually create inside this project");
		    }
		}
		
		
		int i=0;
		for(Element links : link){
			String[] spline=links.attr("abs:href").split("/");
			String last_spline=spline[spline.length-1];
			keywords[i]=last_spline.replace("_"," ");
			i++;
		}
		int k=0;
		for(Element links:link){
			Document doc1=Jsoup.connect(links.attr("abs:href")).get();
			doc1.select("div#mw-content-text>table.wikitable.noprint").remove();
			doc1.select("div#mw-content-text>table.wikitable").remove();
			doc1.select("h2>span.mw-editsection").remove();
			Elements data=doc1.select("div#mw-content-text>*");
			
			List<String> str = Arrays.asList( data.toString().replaceAll("^.*?<h[0-3]>", "")
				    .split("<h[0-9]>.*?h[0-9]>|<p><br></p>"));
			System.out.println(str.size()-1);

			
			for(int j=0;j<str.size()-1;j++){
				
				
				if(str.get(j).split("\\s+").length>200){
					Element tag = Jsoup.parse(str.get(j), "", Parser.xmlParser());
					PrintWriter writer = new PrintWriter(corpus+"/"+keywords[k]+"_part"+j+"_wiki.txt", "UTF-8");
					writer.println(tag);
					writer.close();
				}
			}
			k++;
			
		}
		
		//crawl the oracle pages.
		
		Document doc1 = Jsoup.connect("https://docs.oracle.com/javase/tutorial/java/TOC.html").get();
		Elements link1 = doc1.select("div#PageContent>ul>li>ul>li>a[href],div#PageContent>ul>li>ul>li>ul>li>a[href]");

		System.out.println(link1);
		int x=0;
		String[] keywords1=new String[link1.size()];
		for(Element links : link1){
			String[] spline1=links.attr("abs:href").split("/");
			keywords1[x]=spline1[spline1.length-1];
			keywords1[x]=keywords1[x].replace(".html","");
			x++;
		}
		int y=0;
		for(Element links:link1){
			Document doc2=Jsoup.connect(links.attr("abs:href")).get();
			doc2.select("div#MainFlow>div.PrintHeaders,div#MainFlow>div.BreadCrumbs,div#MainFlow>div.NavBit,div#MainFlow>div.PageTitle").remove();
			Elements data1=doc2.select("div#MainFlow>*");
			List<String> str1 = Arrays.asList( data1.toString().replaceAll("../../","https://docs.oracle.com/javase/tutorial/").split("<h[0-9]>.*?h[0-9]>|<p><br></p>"));
			for(int j=0;j<str1.size();j++){
				if(str1.get(j).split("\\s+").length>200){
					str1.get(j).replaceAll("../../","https://docs.oracle.com/javase/tutorial/");
					Element tag1 = Jsoup.parse(str1.get(j), "", Parser.xmlParser());
					PrintWriter writer = new PrintWriter(corpus+"/"+keywords1[y]+"_part"+j+"_oracle.txt", "UTF-8");
					writer.println(tag1);
					writer.close();
				}
			}
			y++;
		}
		
	}
	
		
		private void indexDirectory(IndexWriter writer, File dir) throws IOException {
			File[] files = dir.listFiles();
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				if (f.isDirectory()) {
					indexDirectory(writer, f); // recurse
				} else if (f.getName().endsWith(".txt")) {
					// call indexFile to add the title of the txt file to your index (you can also index html)
					indexFile(writer, f);
				}
			}
		}
		private void indexFile(IndexWriter writer, File f) throws IOException {
			System.out.println("Indexing " + f.getName());
			org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
			doc.add(new TextField("filename", f.getName(), TextField.Store.YES));
			
			//open each file to index the content
			try{
				
					FileInputStream is = new FileInputStream(f);
			        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			        StringBuffer stringBuffer = new StringBuffer();
			        String line = null;
			        while((line = reader.readLine())!=null){
			          stringBuffer.append(line).append("\n");
			        }
			        reader.close();
					doc.add(new TextField("contents", stringBuffer.toString(), TextField.Store.YES));

			}catch (Exception e) {
	            
				System.out.println("something wrong with indexing content of the files");
	        }    
			writer.addDocument(doc);
			
		}
	
	//sort of main for you
	public void indexer() throws IOException, ParseException{
		
		File dataDir = new File("corpus"); 
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			throw new IOException(
					dataDir + " does not exist or is not a directory");
		}
		
		File indexdir =new File("indexdir");
		if (!indexdir.exists()) {
		    boolean result = false;

		    try{
		    
		        indexdir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        System.out.println("The file for indexing could not be created!!Please manually create inside this project");
		    }
		}
		Directory dir = FSDirectory.open(indexdir.toPath());
		
		// Specify the analyzer for tokenizing text.
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(dir, config);
		
		// call indexDirectory to add to your index
		// the names of the txt files in dataDir
		indexDirectory(writer, dataDir);
		writer.close();
	}
	
	public List<String> queryprocessor(String querystr) throws IOException, ParseException{
		
		System.out.println(querystr);
		//Query string!  
		//String querystr = "contents:Inheritance is when a 'class' derives from an existing 'class'.So if you have a Person class, then you have a Student class that extends Person, Student inherits all the things that Person has.There are some details around the access modifiers you put on the fields/methods in Person, but that's the basic idea.For example, if you have a private field on Person, Student won't see it because its private, and private fields are not visible to subclasses.Polymorphism deals with how the program decides which methods it should use, depending on what type of thing it has.If you have a Person, which has a read method, and you have a Student which extends Person, which has its own implementation of read, which method gets called is determined for you by the runtime, depending if you have a Person or a Student.It gets a bit tricky, but if you do something likePerson p = new Student();p.read();the read method on Student gets called.Thats the polymorphism in action.You can do that assignment because a Student is a Person, but the runtime is smart enough to know that the actual type of p is Student.Note that details differ among languages.You can do inheritance in javascript for example, but its completely different than the way it works in Java.";
		querystr=querystr.replaceAll("[^A-Za-z\\s]","");
		StandardAnalyzer analyzer = new StandardAnalyzer();

		
		
		//performing stemming on the words using PorterStemmer
		int length = querystr.split("\\s+").length;
		if(length>3){
			PorterStemmer stem =new PorterStemmer();
			stem.setCurrent(querystr);
			stem.stem();
			querystr=stem.getCurrent();
		}
		ClassLoader classLoader = getClass().getClassLoader();
		File indexdir = new File(classLoader.getResource("/indexdir").getFile());
		
		Directory dir = FSDirectory.open(indexdir.toPath());
		Query q = new QueryParser( "contents", analyzer).parse(querystr);
		System.out.println(q);
		
		int hitsPerPage = 10;
		IndexReader reader = null;
		 
		 
		 TopScoreDocCollector collector = null;
		 IndexSearcher searcher = null;
		 reader = DirectoryReader.open(dir);
		 searcher = new IndexSearcher(reader);
		 collector = TopScoreDocCollector.create(hitsPerPage);
		 searcher.search(q, collector);
		 
		 ScoreDoc[] hits = collector.topDocs().scoreDocs;
		 System.out.println("Found " + hits.length + " hits.");
		 System.out.println();
		 List<String> finaloutput = new ArrayList<String>();
			
		 for (int i = 0; i < hits.length; ++i) {
			 int docId = hits[i].doc;
			 org.apache.lucene.document.Document d;
			 d = searcher.doc(docId);
			 System.out.println((i + 1) + ". " + d.get("filename"));
			 finaloutput.add(d.get("contents"));
			
		 }
		 reader.close();
		return finaloutput;
	}
	
	
	
}
	
	
	




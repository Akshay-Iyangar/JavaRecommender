###########################################################################
						README
#########################################################################

If you deploying on localhost see that you have tomcat installed.

Please use the link 

http://localhost:8080/Assignment2


To avoid any problems have deployed the war on my server

http://pi.asu.edu:8080/JavaRecommender/

This is already deployed.


*********************************************
Step 1:
Have performed crawling in scraping for wiki and Oracle.
You can find the code for crawling and scraping in method crawler
Class: Queryoutput
method:crawler
The file after crawling in corpus which has all the crawled data.

Step 2:
Performed Indexing 
You can find the code for Indexing inside method indexer using standard analyzer and Porter Stemmer for stemming the query.
Class:Queryoutput
method:indexer
The indexdir directory consist of all the index files.
Note: If you want to run this method please uncomment the methods in Queryoutput and just run them to see the crawling and indexing. Also before the query put the indexdir folder inside /src/main/resources<<IMPT>> Please don’t skip this step.

<<DO THIS ONLY IF YOU WANT TO SEE HOW THE CRAWLING AND INDEXING WORKS>>

Step3:
QueryProcess
This is the step where on input of string or fixed post you get an output.
The method used to do this is queryprocessor.
Class:Queryoutput
method:queryprocessor

The servlet file name is ServletController which calls the ExcelReader to read the post from .xlsx and give it to the query processor.

There are instructions present on code if you face issues.

FYI- If for some reason the code throws an error please check that the maven dependencies are present in by right clicking on properties>Deployment Assembly. the source should have Maven depends present if not go to Add>Java Library>Maven Dependencies and Apply and OK it. Also please create a Maven project with webapp-archetype.




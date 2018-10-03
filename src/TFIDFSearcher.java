//Name: 
//Section: 
//ID: 

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TFIDFSearcher extends Searcher
{
	public TFIDFSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/

		int df = 0, DocSize = documents.size();
        Set<String> term = new HashSet<>();

		for(Document document: documents){
		    term.addAll(document.getTokens());
        }





		}
		/***********************************************/


	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/

		return null;
		/***********************************************/
	}
}

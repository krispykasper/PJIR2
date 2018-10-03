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
		Set<String> terms = new HashSet<>();
		for (Document document: documents){
			terms.addAll(document.getTokens());
		}
		double[][] vsm = new double[terms.size()][documents.size()];
		for(int i = 0; i < terms.size(); i++){
			int count=0;
			for(int k = 0; k < documents.size(); k++){
				if(documents.get(k).getTokens().contains(terms.toArray()[i])){
					count++;
				}
			}
			double idf = Math.log10((1+(documents.size()/count)));
			for(int j = 0; j < documents.size(); j++){
				double tf;
				if(documents.get(j).getTokens().contains(terms.toArray()[i])) {
					tf = 1 + Math.log10(count);
					vsm[i][j] = idf*tf;
				}
				else {
					tf = 0;
					vsm[i][j] = idf*tf;
				}
			}
		}

		int[] d = new int[terms.size()];






		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/

		return null;
		/***********************************************/
	}
}

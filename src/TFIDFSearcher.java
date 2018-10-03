//Name: 
//Section: 
//ID: 

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TFIDFSearcher extends Searcher
{
	public TFIDFSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/

		int  docSize = documents.size();
		double idf[], tf[][];
        Set<String> allTerm = new HashSet<>();

		for(Document document: documents){
		    allTerm.addAll(document.getTokens());
        }
		idf = new double[allTerm.size()];
		tf = new double[allTerm.size()][documents.size()];

        String[] terms = new String[allTerm.size()];


        Iterator<String> iterator = allTerm.iterator();


        int a = 0;
        while (iterator.hasNext()){
            terms[a] = iterator.next() + "";
            a++;
        }

        for(int i = 0; i < terms.length; i++){
		    for(int j = 0; j < documents.size(); j++){
		        if(documents.get(j).getTokens().contains(terms[i])){
		            idf[i]++;
                }
            }

            idf[i] = Math.log10(1 + ((double)docSize / idf[i]));
            System.out.println(i);
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

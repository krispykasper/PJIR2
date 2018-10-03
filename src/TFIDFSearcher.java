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
		System.out.println("HIIIII");
		Set<String> terms = new HashSet<>();
		for (Document document: documents){
			terms.addAll(document.getTokens());
		}
		double[][] vsm = new double[terms.size()][documents.size()];
		for(int i = 0; i < terms.size(); i++){
			int count=0;
			for(int k = 0; k < documents.size(); k++){
				if(documents.get(k).getTokens().contains(terms.toArray()[i])) {
					count++;
				}
			}
//			System.out.println("c"+count);
			double idf = Math.log10((1+(documents.size()/count)));
			for(int j = 0; j < documents.size(); j++){
				double tf;
				int freq = 0;
				if(documents.get(j).getTokens().contains(terms.toArray()[i])) {

					for(int l = 0; l < documents.get(j).getTokens().size();l++){
						if(documents.get(j).getTokens().contains(terms.toArray()[i])){
							freq++;
						}
					}
//					System.out.println(freq);
					tf = 1 + Math.log10(freq);
					vsm[i][j] = idf*tf;
				}
				else {
					tf = 0;
					vsm[i][j] = idf*tf;
				}
			}
		}

		for(int i = 0; i < vsm.length; i++){
			for(int j = 0; j < vsm[0].length; j++){
				System.out.print(vsm[i][j] + "\t");
			}
			System.out.println("\n");
		}






		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/

		return null;
		/***********************************************/
	}
}

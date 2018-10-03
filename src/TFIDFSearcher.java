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
				if(documents.get(k).getTokens().contains(terms.toArray()[i])) {
					count++;
				}
			}
//			System.out.println("c"+count);
			double idf = Math.log10((1+(documents.size()/count)));
            int freq = 0;
            double tf;
			for(int j = 0; j < documents.size(); j++){

				if(documents.get(j).getTokens().contains(terms.toArray()[i])) {

                    //System.out.println("doc "+documents.get(j).getTokens());
					for(int l = 0; l < documents.get(j).getTokens().size();l++){
                        if(documents.get(j).getTokens().get(l).equals(terms.toArray()[i])){
                            freq++;
                        }
                    }
					tf = 1 + Math.log10(freq);
					vsm[i][j] = idf*tf;

				}
				else {
					tf = 0;
					vsm[i][j] = idf*tf;
				}
                //System.out.println("tf"+tf);

			}
		}

        System.out.println("VSM");
            for(int i=0;i<vsm.length;i++){
                for(int j=0;j<vsm[i].length;j++){
                    System.out.println(vsm[i][j]);
                }
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

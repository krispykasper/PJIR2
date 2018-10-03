//Name: 
//Section: 
//ID: 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TFIDFSearcher extends Searcher {

    private Set<String> terms;
    private double[][] vsm;
    private double[] idf;

    public TFIDFSearcher(String docFilename) {
        super(docFilename);
        /************* YOUR CODE HERE ******************/

        terms = new HashSet<>();
        for (Document document : documents) {
            terms.addAll(document.getTokens());
        }
        vsm = new double[terms.size()][documents.size()];
        for (int i = 0; i < terms.size(); i++) {
            int count = 0;
            //System.out.println(terms.toArray()[i]);
            for (int k = 0; k < documents.size(); k++) {
                if (documents.get(k).getTokens().contains(terms.toArray()[i])) {
                    count++;
                }
            }
//			System.out.println("c"+count);
            idf = new double[terms.size()];
            idf[i] = Math.log10((1 + ((double) documents.size() / count)));
            int freq = 0;
            double tf;
            for (int j = 0; j < documents.size(); j++) {

                if (documents.get(j).getTokens().contains(terms.toArray()[i])) {

                    //System.out.println("doc " + documents.get(j).getTokens());
                    for (int l = 0; l < documents.get(j).getTokens().size(); l++) {
                        if (documents.get(j).getTokens().get(l).equals(terms.toArray()[i])) {
                            freq++;
                        }
                    }
                    tf = 1 + Math.log10(freq);
                    vsm[i][j] = idf[i] * tf;

                } else {
                    vsm[i][j] = 0;
                }

            }
        }

    }


    /***********************************************/


    @Override
    public List<SearchResult> search(String queryString, int k) {
        /************* YOUR CODE HERE ******************/
        List<String> qTokens = tokenize(queryString);
        double[] q = new double[terms.size()];
        int freq = 0;

        for (int i = 0; i < terms.size(); i++) {
            if (!qTokens.contains(terms.toArray()[i])) {

                q[i] = 0;

                continue;
            }
            for (int j = 0; j < qTokens.size(); j++) {
                if (qTokens.get(j).equals(terms.toArray()[i])) {

                    freq++;
                }
            }
            q[i] = (1 + Math.log10(freq)) * idf[i];

        }


        List<SearchResult> resultList = new ArrayList<>();
        double sum = 0, qSum = 0, dSum = 0;
        for (int j = 0; j < vsm.length; j++) {
            for (int i = 0; i < vsm[0].length; i++) {
                sum += (q[i] * vsm[i][j]);
                qSum += Math.pow(q[i],2);
                dSum += Math.pow(vsm[i][j],2);
            }

            double cosine = ((sum)/(Math.sqrt(qSum))*Math.sqrt(dSum));
            resultList.add(new SearchResult(documents.get(j), cosine));
        }


        return resultList;
        /***********************************************/
    }
}

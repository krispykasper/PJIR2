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
        String[] termArray = terms.toArray(new String[0]);

        for (int i = 0; i < terms.size(); i++) {
            int count = 0;
            //System.out.println(terms.toArray()[i]);
            for (int k = 0; k < documents.size(); k++) {
                List<String> list = documents.get(k).getTokens();
                if (list.contains(termArray[i])) {
                    count++;
                }
            }
            idf = new double[terms.size()];
            idf[i] = Math.log10((1 + ((double) documents.size() / count)));
            int freq = 0;
            double tf;
            for (int j = 0; j < documents.size(); j++) {
                List<String> list = documents.get(j).getTokens();
                if (list.contains(termArray[i])) {

                    //System.out.println("doc " + documents.get(j).getTokens());
                    for (int l = 0; l < list.size(); l++) {
                        if (list.get(l).equals(termArray[i])) {
                            freq++;
                        }
                    }
                    tf = 1 + Math.log10(freq);
                    vsm[i][j] = idf[i] * tf;

                } else {
                    vsm[i][j] = 0;
                }

            }
            if(i % 100 == 0){
                System.out.println(i);
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
        String[] termArray = terms.toArray(new String[0]);

        for (int i = 0; i < terms.size(); i++) {
            if (!qTokens.contains(termArray[i])) {

                q[i] = 0;

                continue;
            }
            for (int j = 0; j < qTokens.size(); j++) {
                if (qTokens.get(j).equals(termArray[i])) {

                    freq++;
                }
            }
            q[i] = (1 + Math.log10(freq)) * idf[i];

        }


        List<SearchResult> resultList = new ArrayList<>();
        double sum = 0, qSum = 0, dSum = 0;
        for (int j = 0; j < vsm[0].length; j++) {
            for (int i = 0; i < vsm.length; i++) {
                sum += (q[i] * vsm[i][j]);
                qSum += Math.pow(q[i],2);
                dSum += Math.pow(vsm[i][j],2);
            }

            double cosine = ((sum)/(Math.sqrt(qSum))*Math.sqrt(dSum));
            if(!Double.isNaN(cosine)){
                resultList.add(new SearchResult(documents.get(j), cosine));

            }
        }


        return resultList;
        /***********************************************/
    }
}

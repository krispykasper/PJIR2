//Name: 
//Section: 
//ID: 

import java.util.*;

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

        idf = new double[terms.size()];
        vsm = new double[terms.size()][documents.size()];

        String[] termArray = terms.toArray(new String[0]);

        for (int i = 0; i < terms.size(); i++) {
            int count = 0;
            for (int j = 0; j < documents.size(); j++) {
                String[] list = documents.get(j).getTokens().toArray(new String[0]);

                for (int k = 0; k < list.length; k++) {
                    if(list[k].equals(termArray[i])){
                        count++;
                        break;
                    }
                }
            }
            idf[i] = Math.log10(1 + ((double) documents.size() / (double)count));
            double tf;
            for (int j = 0; j < documents.size(); j++) {
                String[] list = documents.get(j).getTokens().toArray(new String[0]);
                double freq = 0;

                for (int l = 0; l < list.length; l++) {
                    if (list[l].equals(termArray[i])) {
                        freq++;
                    }
                }

                tf = 1 + Math.log10(freq);
                if (freq == 0) {
                    tf = 0;
                }
                vsm[i][j] = idf[i] * tf;


            }
            if (i % 1000 == 0) {
                System.out.println(i);
            }


        }

    }


    /***********************************************/


    @Override
    public List<SearchResult> search(String queryString, int k) {
        /************* YOUR CODE HERE ******************/
        String[] qTokens = tokenize(queryString).toArray(new String[0]);
        List<SearchResult> resultList = new ArrayList<>();
        List<SearchResult> choosedResultList = new ArrayList<>();


        double[] q = new double[terms.size()];
        String[] termArray = terms.toArray(new String[0]);


        for (int i = 0; i < terms.size(); i++) {
            double freq = 0;

            for (int j = 0; j < qTokens.length; j++) {
                if (qTokens[j].equals(termArray[i])) {

                    freq++;
                }
            }

            q[i] = (1 + Math.log10(freq)) * idf[i];
            if(freq == 0){
                q[i] = 0;
            }

        }


        for (int j = 0; j < vsm[0].length; j++) {
            double sum = 0, qSum = 0, dSum = 0;

            for (int i = 0; i < vsm.length; i++) {
                sum += (q[i] * vsm[i][j]);
                qSum += Math.pow(q[i], 2);
                dSum += Math.pow(vsm[i][j], 2);
            }

            double cosine = ((sum) / ((Math.sqrt(qSum)) * Math.sqrt(dSum)));
            resultList.add(new SearchResult(documents.get(j), cosine));
        }


        for (int i = 0; i < k; i++) {
            int ind = -1;
            SearchResult temp = resultList.get(i);
            for (int j = i + 1; j < resultList.size(); j++) {
                if (resultList.get(j).getScore() > temp.getScore()) {
                    temp = resultList.get(j);
                    ind = j;
                }
            }

            if (temp.getDocument().getId() != resultList.get(i).getDocument().getId()) {
                resultList.set(ind, resultList.get(i));
                resultList.set(i, temp);
            }

            choosedResultList.add(resultList.get(i));
        }


        return choosedResultList;
        /***********************************************/
    }
}

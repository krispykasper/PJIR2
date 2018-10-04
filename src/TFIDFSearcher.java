//Name: 
//Section: 
//ID: 

import java.util.*;

public class TFIDFSearcher extends Searcher {

    private Set<String> terms = new HashSet<>();
    private double[][] vsm;
    private double[] idf;

    public TFIDFSearcher(String docFilename) {
        super(docFilename);
        /************* YOUR CODE HERE ******************/

        for (Document document : documents) {
            terms.addAll(document.getTokens());
        }
        idf = new double[terms.size()];
        vsm = new double[terms.size()][documents.size()];
        String[] termArray = terms.toArray(new String[0]);

        for (int i = 0; i < terms.size(); i++) {
            int count = 0;
            //System.out.println(terms.toArray()[i]);
            for (int k = 0; k < documents.size(); k++) {
                List<String> list = documents.get(k).getTokens();
                if (list.contains(termArray[i])) {
                    count++;
                    break;
                }
            }
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
            if(i % 1000 == 0){
                System.out.println(i);
            }


        }
        for(int i = 0; i < vsm.length; i++){
            for(int j = 0; j < vsm[0].length; j++){
                System.out.print(String.format("%.2f",vsm[i][j]) + "\t");
            }
            System.out.println();
        }

    }


    /***********************************************/


    @Override
    public List<SearchResult> search(String queryString, int k) {
        /************* YOUR CODE HERE ******************/
//        System.out.println(queryString);
        List<String> qTokens = tokenize(queryString);
//        System.out.println(Arrays.toString(qTokens.toArray()));
        List<SearchResult> choosedResultList = new ArrayList<>();


        double[] q = new double[terms.size()];
        String[] termArray = terms.toArray(new String[0]);

//        System.out.println(Arrays.toString(terms.toArray()));

        for (int i = 0; i < terms.size(); i++) {
            int freq = 0;

            for (int j = 0; j < qTokens.size(); j++) {
                if (qTokens.get(j).equals(termArray[i])) {

                    freq++;
                }
            }
//            System.out.println(termArray[i]);
//            System.out.println("freq: " + freq);
//
//            System.out.println("idf: " + idf[i]);

            q[i] = (1 + Math.log10(freq)) * idf[i];
            if (!qTokens.contains(termArray[i])) {
//                System.out.println(termArray[i]);
                q[i] = 0;
            }

        }
//        System.out.println(Arrays.toString(q));
//        System.exit(0);


        List<SearchResult> resultList = new ArrayList<>();
        for (int j = 0; j < vsm[0].length; j++) {
            double sum = 0, qSum = 0, dSum = 0;
            System.out.println("Start");

            for (int i = 0; i < vsm.length; i++) {
                System.out.println("q: " + q[i] + "\t vsm: " + vsm[i][j]);
                sum += (q[i] * vsm[i][j]);
                qSum += Math.pow(q[i],2);
                dSum += Math.pow(vsm[i][j],2);
            }
            System.out.println("End");

            double cosine = ((sum)/(Math.sqrt(qSum))*Math.sqrt(dSum));
            resultList.add(new SearchResult(documents.get(j), cosine));
//            System.out.println(cosine);
        }


        for(int i = 0; i < k; i++){
            int ind = -1;
            SearchResult temp = resultList.get(i);
            for(int j = i + 1; j < resultList.size(); j++){
                if(resultList.get(j).getScore() > temp.getScore()){
                    temp = resultList.get(j);
                    ind = j;
                }
            }

            if(temp.getDocument().getId() != resultList.get(i).getDocument().getId()){
                resultList.set(ind, resultList.get(i));
                resultList.set(i, temp);
            }

            choosedResultList.add(resultList.get(i));
        }


        return choosedResultList;
        /***********************************************/
    }
}

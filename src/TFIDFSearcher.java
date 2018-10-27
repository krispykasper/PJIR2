//Section: 1
//Name: Anajak Juengsophonvitawas
//ID: 5988119
//Name: Pochara Sangtunchai
//ID: 5988203
//Name: Manisa Satravisut
//ID: 5988209

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TFIDFSearcher extends Searcher
{
    private HashMap<String, Double> idf = new HashMap<>();
    private HashMap<String, HashMap<Integer, Double>> wd = new HashMap<>();
    private HashSet<String> terms = new HashSet<>();


    public TFIDFSearcher(String docFilename) {
        super(docFilename);
        /************* YOUR CODE HERE ******************/
        HashMap<String, HashMap<Integer, Integer>> freq = new HashMap<>();

        for(Document document: documents){
            //add all document's tokens to terms HashSet
            terms.addAll(document.getTokens());
            List<String> token = document.getTokens();
            //Set number of
            for(String term: token){
                if(freq.containsKey(term)){

                    if(freq.get(term).containsKey(document.getId())){
                        freq.get(term).put(document.getId(), freq.get(term).get(document.getId()) + 1);
                    }else {
                        freq.get(term).put(document.getId(), 1);
                    }
                }else {
                    freq.put(term, new HashMap<>());
                    freq.get(term).put(document.getId(), 1);
                }

            }
        }
        for(String term: terms){
            int count = 0;
            for(Document document: documents){
                if(freq.get(term).get(document.getId()) != null){
                    count++;
                }
            }
            idf.put(term, Math.log10(1 + (double) documents.size() / count));
            for (Document document: documents){
                if(freq.get(term).get(document.getId()) != null){
                    if(wd.containsKey(term)){

                        wd.get(term).put(document.getId(), idf.get(term) * (1d + Math.log10(freq.get(term).get(document.getId()))));

                    }else {

                        wd.put(term, new HashMap<>());
                        wd.get(term).put(document.getId(), idf.get(term) * (1d + Math.log10(freq.get(term).get(document.getId()))));

                    }
                }


            }
        }
        System.out.println("First Part");




        /***********************************************/
    }

    @Override
    public List<SearchResult> search(String queryString, int k) {
        /************* YOUR CODE HERE ******************/

        HashMap<String, Integer> freq = new HashMap<>();
        List<String> q = tokenize(queryString);
        HashMap<String, Double> wq = new HashMap<>();
        List<SearchResult> resultList = new ArrayList<>();
        List<SearchResult> choosedResultList = new ArrayList<>();

        for(String term: q){
            if(freq.containsKey(term)){
                freq.put(term, freq.get(term) + 1);
                System.out.println(freq.get(term));
            }else {
                freq.put(term, 1);
            }
        }

        for(String term: terms){
            if(freq.get(term) != null){
                wq.put(term, idf.get(term) * (1d + Math.log10(freq.get(term))));
            }
        }

        for(Document document: documents){
            double sumAB = 0;
            double sumA = 0;
            double sumB = 0;
            for(String term: terms){
                double a, b;
                if(wq.get(term) == null){
                    a = 0;
                }else {
                    a = wq.get(term);
                }

                if(wd.get(term).get(document.getId()) == null){
                    b = 0;
                }else {
                    b = wd.get(term).get(document.getId());
                }
                sumAB += a * b;
                sumA += a * a;
                sumB += b * b;
            }
            resultList.add(new SearchResult(document, sumAB / ((Math.sqrt(sumA)) * (Math.sqrt(sumB)))));
        }
        System.out.println("Second Part");

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
        System.out.println("Third Part");





        return choosedResultList;




        /***********************************************/
    }
}

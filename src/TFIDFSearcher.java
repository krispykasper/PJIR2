//Section: 1
//Name: Anajak Juengsophonvitawas
//ID: 5988119
//Name: Pochara Sangtunchai
//ID: 5988203
//Name: Manisa Satravisut
//ID: 5988209

import java.util.*;

public class TFIDFSearcher extends Searcher
{
    private HashMap<String, Double> idf = new HashMap<>();
    private HashMap<String, HashMap<Integer, Double>> wd = new HashMap<>();


    public TFIDFSearcher(String docFilename) {
        super(docFilename);
        /************* YOUR CODE HERE ******************/
        HashMap<String, HashMap<Integer, Integer>> freq = new HashMap<>();
        HashSet<String> terms = new HashSet<>();
        HashMap<Integer, List<String>> documentToken = new HashMap<>();
        for(Document document: documents){
            terms.addAll(document.getTokens());
            List<String> token = document.getTokens();
            documentToken.put(document.getId(), token);
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
                    wd.put(term, new HashMap<>());
                    wd.get(term).put(document.getId(), idf.get(term) * (1d + Math.log10(freq.get(term).get(document.getId()))));
                }
            }
        }
        System.out.println("First Part");


        /***********************************************/
    }

    @Override
    public List<SearchResult> search(String queryString, int k) {
        /************* YOUR CODE HERE ******************/



        return new ArrayList<>();
        /***********************************************/
    }
}

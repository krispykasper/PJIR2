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

    private HashSet<String> terms = new HashSet<>();





    public TFIDFSearcher(String docFilename) {

        super(docFilename);

        /************* YOUR CODE HERE ******************/

        HashMap<String, HashMap<Integer, Integer>> freq = new HashMap<>();



        for(Document document: documents){

            //add all document's tokens to terms HashSet

            terms.addAll(document.getTokens());

            List<String> token = document.getTokens();

            //find number of terms that occur in each documents

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

        //find number of documents that contain each terms
        for(String term: terms){

            int count = 0;

            for(Document document: documents){

                if(freq.get(term).get(document.getId()) != null){

                    count++;

                }

            }
            //Set idf
            idf.put(term, Math.log10(1 + (double) documents.size() / count));

            //Set weight of document
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


        /***********************************************/

    }



    @Override

    public List<SearchResult> search(String queryString, int k) {

        /************* YOUR CODE HERE ******************/

        HashMap<String, Integer> freq = new HashMap<>();

        List<String> q = tokenize(queryString);

        HashMap<String, Double> wq = new HashMap<>();

        List<SearchResult> resultList = new ArrayList<>();

        //add frequency of each query token(q) to freq HashMap
        for(String term: q){

            if(freq.containsKey(term)){

                freq.put(term, freq.get(term) + 1);

            }else {

                freq.put(term, 1);

            }

        }

        //set weight of query
        for(String term: terms){

            if(freq.get(term) != null){

                wq.put(term, idf.get(term) * (1d + Math.log10(freq.get(term))));

            }

        }


        //find cosine similarity
        for(Document document: documents){

            TreeSet<String> terms = new TreeSet<>();

            terms.addAll(document.getTokens());

            terms.addAll(q);

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
                if(wd.get(term) == null){
                    b = 0;
                }else if(wd.get(term).get(document.getId()) == null){

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


        Collections.sort(resultList);

        return resultList.subList(0,k);









        /***********************************************/

    }

}
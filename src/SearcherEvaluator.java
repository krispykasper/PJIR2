//Name: 
//Section: 
//ID: 

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SearcherEvaluator {
    private List<Document> queries = null;                //List of test queries. Each query can be treated as a Document object.
    private Map<Integer, Set<Integer>> answers = null;    //Mapping between query ID and a set of relevant document IDs

    public List<Document> getQueries() {
        return queries;
    }

    public Map<Integer, Set<Integer>> getAnswers() {
        return answers;
    }

    /**
     * Load queries into "queries"
     * Load corresponding documents into "answers"
     * Other initialization, depending on your design.
     *
     * @param corpus
     */
    public SearcherEvaluator(String corpus) {
        String queryFilename = corpus + "/queries.txt";
        String answerFilename = corpus + "/relevance.txt";

        //load queries. Treat each query as a document.
        this.queries = Searcher.parseDocumentFromFile(queryFilename);
        this.answers = new HashMap<Integer, Set<Integer>>();
        //load answers
        try {
            List<String> lines = FileUtils.readLines(new File(answerFilename), "UTF-8");
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\t");
                Integer qid = Integer.parseInt(parts[0]);
                String[] docIDs = parts[1].trim().split("\\s+");
                Set<Integer> relDocIDs = new HashSet<Integer>();
                for (String docID : docIDs) {
                    relDocIDs.add(Integer.parseInt(docID));
                }
                this.answers.put(qid, relDocIDs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns an array of 3 numbers: precision, recall, F1, computed from the top *k* search results
     * returned from *searcher* for *query*
     *
     * @param query
     * @param searcher
     * @param k
     * @return
     */
    public double[] getQueryPRF(Document query, Searcher searcher, int k) {
        /*********************** YOUR CODE HERE *************************/

        double[] result = new double[3];
        double precision, recall, f1;

        Set<Integer> r = new HashSet<>(), g = new HashSet<>();

        //get search result from searchers and then store it in set "r"
        List<SearchResult> searchResults = searcher.search(query.getRawText(), k);
        for (SearchResult searchResult : searchResults) {
            r.add(searchResult.getDocument().getId());
        }

        //add ground truth relevant document to set "g"
        g.addAll(answers.get(query.getId()));


//        find intersection of "g" and "r"
        Set<Integer> intersect = new HashSet<>();
        intersect.addAll(r);
        intersect.retainAll(g);

//        find precision, recall, and f1

        precision = (double) intersect.size() / (double) r.size();

        recall = (double) intersect.size() / (double) g.size();

        f1 = (2 * precision * recall) / (precision + recall);

        if (Double.isNaN(f1)) {
            f1 = 0;
        }

        result[0] = precision;
        result[1] = recall;
        result[2] = f1;

        return result;
        /****************************************************************/
    }

    /**
     * Test all the queries in *queries*, from the top *k* search results returned by *searcher*
     * and take the average of the precision, recall, and F1.
     *
     * @param searcher
     * @param k
     * @return
     */
    public double[] getAveragePRF(Searcher searcher, int k) {
        /*********************** YOUR CODE HERE *************************/

//        get "getQueryPRF" of all queries and find avg of precision, recall, and f1

        double[][] result = new double[queries.size()][3];
        for(int i=0;i<queries.size();i++){
            result[i] = getQueryPRF(queries.get(i),searcher,k);
        }
        double sumPrecision=0, sumRecall = 0, sumF1 = 0;
        for(int i=0;i<queries.size();i++){
            sumPrecision += result[i][0];
            sumRecall += result[i][1];
            sumF1 += result[i][2];
        }
        double[] avg = new double[3];
        double qSize = queries.size();
        avg[0] = sumPrecision / qSize;
        avg[1] = sumRecall / qSize;
        avg[2] = sumF1 / qSize;

        return avg;

        /****************************************************************/

    }
}

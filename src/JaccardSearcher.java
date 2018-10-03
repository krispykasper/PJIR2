//Name: 
//Section: 
//ID: 

import java.util.*;

public class JaccardSearcher extends Searcher{

	private Map<Integer, Set<String>> d = new TreeMap<>();

	public JaccardSearcher(String docFilename) {
		super(docFilename);
		/************* YOUR CODE HERE ******************/

		for (Document document: documents){
			Set<String> subD = new HashSet<>();
			subD.addAll(document.getTokens());
			d.put(document.getId(), subD);
		}



		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		Set<String> q = new HashSet<>();
		List<SearchResult> resultList = new ArrayList<>();
		List<SearchResult> choosedResultList = new ArrayList<>();
		List<String> qTokens = tokenize(queryString);

		q.addAll(qTokens);
		SearchResult searchResult;

		for (int docId: d.keySet()){
			Set<String> intersect = new HashSet<>();
			Set<String> union = new HashSet<>();
			intersect.addAll(q);
			intersect.retainAll(d.get(docId));
			union.addAll(q);
			union.addAll(d.get(docId));
			double score = (double) intersect.size() / (double)union.size();

			searchResult = new SearchResult(documents.get(docId - 1), score);
			resultList.add(searchResult);

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

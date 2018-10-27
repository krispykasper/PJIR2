//Section: 1
//Name: Anajak Juengsophonvitawas
//ID: 5988119
//Name: Pochara Sangtunchai
//ID: 5988203
//Name: Manisa Satravisut
//ID: 5988209

import java.util.*;

public class JaccardSearcher extends Searcher{

	public JaccardSearcher(String docFilename) {
		super(docFilename);



		/************* YOUR CODE HERE ******************/

		//put document's tokens to subD set and put subD set to d Map



		/***********************************************/
	}

	@Override
	public List<SearchResult> search(String queryString, int k) {
		/************* YOUR CODE HERE ******************/
		Set<String> q = new HashSet<>();
		List<SearchResult> resultList = new ArrayList<>();
		List<String> qTokens = tokenize(queryString);

		//add all qTokens that are tokenized from queryString to Set q
		q.addAll(qTokens);
		SearchResult searchResult;

		//find intersect between q and d and find union between q and d then find score
		for (Document document: documents){
			Set<String> intersect = new HashSet<>();
			Set<String> union = new HashSet<>();
			intersect.addAll(q);

			intersect.retainAll(document.getTokens());
			union.addAll(q);
			union.addAll(document.getTokens());
			double score = (double) intersect.size() / (double)union.size();

			searchResult = new SearchResult(document, score);
			resultList.add(searchResult);

		}

		//find the top k values and set to choosedResultList

		Collections.sort(resultList);
		return resultList.subList(0,k);


		/***********************************************/
	}

}

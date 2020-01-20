/** 
*
* A class used to calculate the similarity between text file A, and text file B.
* Uses cosine similarity for calculation, and so it is slightly more exact than
* class Jaccard.java which uses Jaccard index.
*
* This class contains 1 method: score.
*/

public class Cosine implements Similarity {

	/**
	* Takes in two WordMap objects, obj and obj2, which are just
	* representations of different text files.
	* Then it returns the ratio of similarity as a double.
	* Calculates the ratio of similarity using cosine similarity.
	*
	* @param obj a WordMap of a text file (either as a doubly linked list, or a binary tree)
	* @param obj2 a WordMap of a text file (either as a doubly linked list, or a binary tree)
	* @return double Returns the ratio of similarity as a double.
	*
	**/

	public double score (WordMap obj, WordMap obj2) {
		//ratio of simlarity
		double result;
		//number of n-grams that co-exist in both obj and obj2
		int countIntersection = 0;

		//String list representation of obj
		String[] stringLinked = obj.keys();
		//String list representation of obj2
		String[] stringTree = obj2.keys();

		//for each element in obj, check if obj2 contains it too
		for (int i=0; i<obj.size(); i++) {
			if (obj2.contains(stringLinked[i])) {
				//if obj2 does contain it, find it in obj2
				for (int j=0; j<obj2.size(); j++) {
					//if you find it
					if (stringLinked[i].equals(stringTree[j])) {
						//compare values, we will add the lowest count of the key to countIntersection
						if(obj.get(stringLinked[i]) == obj2.get(stringTree[j])) {
							countIntersection = countIntersection + obj.get(stringLinked[i]);
						}

						if(obj.get(stringLinked[i]) < obj2.get(stringTree[j])) {
							countIntersection = countIntersection + obj.get(stringLinked[i]);
						}

						if(obj.get(stringLinked[i]) > obj2.get(stringTree[j])) {
							countIntersection = countIntersection + obj2.get(stringTree[j]);
						}
					}
				}
			}
		}

		//total number of n-grams that exist in obj added
		//to the total number of n-grams that exist in obj2
		int countTotal = 0;
		//Integer list representation of obj's counts
		Integer[] countLinked = obj.counts();
		//Integer list representation of obj2's counts
		Integer[] countTree = obj2.counts();

		//Go through every count in the list of obj and add them to countTotal
		for (int i=0; i<obj.size(); i++) {
			countTotal = countTotal + countLinked[i];
		}
		//Go through every count in the list of obj2 and add them to countTotal
		for (int i=0; i<obj2.size(); i++) {
			countTotal = countTotal + countTree[i];
		}

		//change countIntersection and countTotal as absoloute values.
		//Acutally not really needed, but doing it just in case.
		int absCountIntersection = Math.abs(countIntersection);
		int absCountTotal = Math.abs(countTotal);

		//change absCountIntersection and absCountToal into doubles,
		//so we can run double division on them.
		double doubleAbsIntersection = absCountIntersection;
		double doubleAbsTotal = absCountTotal;

		//calculate result using equation provided in the assignment document
		result = doubleAbsIntersection/(doubleAbsTotal-doubleAbsIntersection);
		return result;
	}
}
//previous version of the code, did not remove just in case i need to look at it again,
//or in case i get into plagiarism issues.
/*
		double result;
		int countIntersection = 0;
		int countUnique = 0;

		String[] stringLinked = obj.keys();
		String[] stringTree = obj2.keys();

		for (int i=0; i<obj.size(); i++) {
			for (int j=0; j<obj2.size(); j++) {
				if (stringLinked[i].equals(stringTree[j])) {
					if(obj.get(stringLinked[i]) == obj2.get(stringTree[j])) {
						countIntersection = countIntersection + obj.get(stringLinked[i]);
					}

					if(obj.get(stringLinked[i]) < obj2.get(stringTree[j])) {
						countIntersection = countIntersection + obj.get(stringLinked[i]);
					}

					if(obj.get(stringLinked[i]) > obj2.get(stringTree[j])) {
						countIntersection = countIntersection + obj2.get(stringTree[i]);
					}

				}
				else{
					countUnique = countUnique + obj.get(stringLinked[i]) + obj2.get(stringTree[j]);
				}
			}
		}

		result = countIntersection/(countUnique+countIntersection);
		return result;
	}
*/

//previous version of the code, did not remove just in case i need to look at it again,
//or in case i get into plagiarism issues.
/*		double result;
		int length;
		LinkedWordMap intersection = new LinkedWordMap();

		String[] stringLinked = obj.keys();
		String[] stringTree = obj2.keys();

		if (obj.size() == obj2.size()) {
			length = obj.size();
		}

		if (obj.size() > obj2.size()) {
			length = obj.size();
		}

		if (obj.size() < obj2.size()) {
			length = obj2.size();
		}


		for (int i=0; i<length; i++) {
			if (obj2.contains(stringLinked[i])) {

			}

*/


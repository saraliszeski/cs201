import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String, ArrayList<String>> myMap;
	/**
	 * sets default order to 3
	 */
	public EfficientMarkov() {
		this(3);
	}

	public EfficientMarkov(int order) {
		/**
		 * supers order, creates myMap to store variables
		 */
		super(order);
		myMap = new HashMap<String, ArrayList<String>>();
	}


	@Override
	public void setTraining(String text) {
		/**
		 * set trains the text
		 * clears the map
		 * if the text is shorter than the order, simply return the text with a corresponding end of string in its arraylist
		 */
		super.setTraining(text);
//		System.out.println(myOrder);
		myMap.clear();
		if (text.length()< myOrder) {
			String sub = text;
			myMap.putIfAbsent(sub, new ArrayList());
			myMap.get(sub).add(PSEUDO_EOS);
		}
		/**
		 * works through all substrings of string via
		 * a) generating a substring of length order
		 * b) mymap.putifabsent the substring with corresponding arraylist
		 * c) if the substring is the last possible substring, add an end of phrase
		 */
		for (int k = 0; k < text.length() - myOrder + 1; k ++) {
			String sub = text.substring(k, k + myOrder);
			myMap.putIfAbsent(sub, new ArrayList());
			if (k + myOrder == text.length()) {
				myMap.get(sub).add(PSEUDO_EOS);
			}
			if (k + myOrder < text.length()) {
				myMap.get(sub).add(String.valueOf(text.charAt(k+myOrder)));
			}

		}
		System.out.println(myMap);
	}
	@Override
	public ArrayList<String> getFollows(String key) {
		/**
		 * if mymap doesn't contain the key, give a telling error
		 * otherwise, return the value had by that key
		 */
		if ( ! myMap.containsKey(key)) {
			throw new NoSuchElementException(key+" not in map");
		}
		ArrayList<String> newh = myMap.get(key);

		return newh;
	}
}

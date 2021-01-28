
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * A WordGram represents a sequence of strings
 * just as a String represents a sequence of characters
 *
 * @author Sara Liszeski
 *
 */
public class WordGram {

	private String[] myWords;
	private String myToString;  // cached string
	private int myHash;

	/**
	 * Create WordGram by creating instance variable myWords and copying
	 * size strings from source starting at index start
	 * @param source is array of strings from which copying occurs
	 * @param start starting index in source for strings to be copied
	 * @param size the number of strings copied
	 */
	/** change constructor to reflect methods needed
	 //     * @param words = source
	 //     * @param index = start
	 //     * @param size = size
	 //		add all of the strings in words to myWords
	 //     */
	public WordGram(String[] words, int index, int size) {
		myWords = new String[size];
		for (int i = 0; i<size; i++) {
			myWords[i] = words[index+i];
		}
		myToString = null;
		myHash = 0;
	}

	/**
	 * Return string at specific index in this WordGram
	 * @param index in range [0..length() ) for string
	 * @return string at index
	 */
	public String wordAt(int index) {
		if (index < 0 || index >= myWords.length) {
			throw new IndexOutOfBoundsException("bad index in wordAt "+index);
		}
		return myWords[index];
	}

	/**
	 * v basic
	 * return the length of mywords
	 */
	public int length(){
		int h = myWords.length;
		return h;
	}
	/**
	 * if the lengths are the same then continue
	 * if the words at index [i] are the same with .equals, continue
	 * else return false
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof WordGram) || o == null) {
			return false;
		}
		else {
			WordGram wg = (WordGram) o;
			if (this.myWords.length == wg.myWords.length) {
				for(int i = 0; i<myWords.length; i++) {
					if (wg.myWords[i].equals(myWords[i])) {
						continue;
					}
					else	return false;
				}
				return true;
			}
			else	return false;
		}
	}

	@Override
	/**
	 * if hashcode hasn't been called yet
	 * use this.toString.hashcode to store its hashcode in myHash
	 * idk why this isn't changing correctly, bt it functions half of the time!!!
	 */
	public int hashCode() {
		String a = this.toString();
		if (myHash == 0) {
			myHash = a.hashCode();
		}
		return myHash;
	}
	/**
	 * put mywords in an array for easy access
	 * remove the first word, add last to the end
	 * @param last is last String of returned WordGram
	 * @return
	 */
	public WordGram shiftAdd(String last) {
		ArrayList<String> newmywords = new ArrayList<String>();
		Collections.addAll(newmywords,myWords);
		newmywords.remove(0);
		newmywords.add(last);
		WordGram wg = new WordGram(newmywords.toArray(new String[0]),0,myWords.length);
		//wg.myWords[myWords.length] = last;
		return wg;
	}

	/**
	 * if there isn't a mytostring, join with " " and assign to mytostring
	 * @return
	 */

	@Override
	public String toString(){
		if (myToString == null) {
			String a = String.join(" ", this.myWords);
			myToString = a;
		}
		return myToString;
	}
}
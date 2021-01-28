import java.util.*;

public class EfficientWordMarkov extends BaseWordMarkov {
    /**
     * uses an array instead of a string!!
     */
    private String[] myWords;
    private Map<WordGram, ArrayList<String>> myMap;
    /**
     * automatically sets order to 3
     */
    public EfficientWordMarkov() {
        this(3);
    }
    /**
     * inherits order, creates myMap to store variables
     */
    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<WordGram, ArrayList<String>>();
    }
    @Override
    /**
     * overrides set training from the non-efficient baseword
     */
    public void setTraining(String mywords) {
        /**
         * set trains every word in mywords
         * splits mywords at the space character into an array
         * uses a for loop to a) create a wordgram of each word in myWords
         * b) stores each substring as a key then places the corresponding following word into the connected arraylist
         * c) if k gets the last substring in the array, add a pseudo end of string
         */
        super.setTraining(mywords);
        myWords = mywords.split("\\s+");
        for (int k = 0; k < myWords.length - myOrder+1; k += 1) {
            WordGram b = new WordGram(myWords, k, myOrder);
            myMap.putIfAbsent(b, new ArrayList());
            if (k + myOrder == myWords.length ) {
                myMap.get(b).add(PSEUDO_EOS);
            }
            if (k+ myOrder < myWords.length ) {
                myMap.get(b).add(myWords[k + myOrder]);
            }
        }
    }
    @Override
    public ArrayList<String> getFollows(WordGram kgram) {
        /**
         * if mymap doesnt contai nthe key, give a telling error
         * otherwise, return the value had by that key
         */
        if ( ! myMap.containsKey(kgram)) {
            throw new NoSuchElementException(kgram+" not in map");
        }
        return myMap.get(kgram);

    }

    }

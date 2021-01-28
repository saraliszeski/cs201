import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;
    private Term[] myTerms;
    /**
   Create object, test for null parameters, initialize terms and weights
     * @param terms= words
     * @param weights= weights
     */
    public HashListAutocomplete(String[] terms, double[] weights) {
        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }
        initialize(terms, weights);
    }

    /**
     *
     * @param prefix = prefix
     * @param k= # matches, test to make sure is viable
     * @return
     */
/*
Make sure that the prefix is appropriately sized with Max_Prefix
check that map isn't null
return the properly-sized list
 */
    @Override
    public List<Term> topMatches(String prefix, int k) {
        prefix = prefix.substring(0, Math.min(MAX_PREFIX, prefix.length()));
        List<Term> all = myMap.get(prefix);
        if (all != null) {
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
        }
        return new ArrayList<Term>();
    }

    /**
     *
     * @param terms is array of Strings for words in each Term
     * @param weights is corresponding weight for word in terms
     */
/*
Create a new Term, give it its respective weight
generate substrings to be placed into map
sort the map based on reverse weight
 */
    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        myTerms = new Term[terms.length];

        for (int y = 0; y < terms.length; y++) {
            Term temp = new Term(terms[y], weights[y]);
            int count = Math.min(MAX_PREFIX, terms[y].length());
            for (int p = 0; p <= count; p++) {
                String t = temp.getWord().substring(0, p);
                myMap.putIfAbsent(t, new ArrayList<Term>());
                myMap.get(t).add(temp);
            }
        }
            for (String s : myMap.keySet()) {
                Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
            }
        }

    /**
     *
     * @return int value that is size in bytes of the resultant map
     */

/*
Iterate through the map keyset and the map keyset's values in order to calculate bytesize
 */

    @Override
    public int sizeInBytes() {

        for (String j : myMap.keySet()) {
            mySize += BYTES_PER_CHAR * j.length();
            List<Term> all = myMap.get(j);
            for (int y = 0; y < all.size(); y++) {
                Term germ = all.get(y);
                mySize += BYTES_PER_CHAR * germ.getWord().length() + BYTES_PER_DOUBLE;
            }
        }
        return mySize;
    }
}

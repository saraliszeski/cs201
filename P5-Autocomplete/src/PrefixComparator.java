import java.util.Comparator;

/**
 * Factor pattern for obtaining PrefixComparator objects
 * without calling new. Users simply use
 *
 *     Comparator<Term> comp = PrefixComparator.getComparator(size)
 *
 * @author owen astrachan
 * @date October 8, 2020
 */
public class PrefixComparator implements Comparator<Term> {

    private int myPrefixSize; // size of prefix

    /**
     * private constructor, called by getComparator
     * @param prefix is prefix used in compare method
     */
    private PrefixComparator(int prefix) {
        myPrefixSize = prefix;
    }


    /**
     * Factory method to return a PrefixComparator object
     * @param prefix is the size of the prefix to compare with
     * @return PrefixComparator that uses prefix
     */
    public static PrefixComparator getComparator(int prefix) {
       return new PrefixComparator(prefix);
    }

    /**
     * Compare to adapt myprefixsize , return the comparison value
     * @param v
     * @param w
     * @return
     */

    @Override
    public int compare(Term v, Term w) {
        String newV = v.getWord();
        String newW = w.getWord();
        if (newV.length() > myPrefixSize) {
            newV = newV.substring(0, myPrefixSize);
        }
        if (newW.length() > myPrefixSize) {
            newW = newW.substring(0, myPrefixSize);
        }
        int compared = newV.compareTo(newW);
        return compared;
    }
}

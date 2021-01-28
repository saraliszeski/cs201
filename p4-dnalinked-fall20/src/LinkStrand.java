import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author Alice Wu
 * @author Alex Kumar
 * @author Sara Liszeski
 */;

public class LinkStrand implements IDnaStrand{

    private class Node{
        String info;
        Node next;
        public Node(String s){
            info = s;
            next = null;
        }
    }
    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex=0;
    private int myLocalIndex;
    private Node myCurrent;
    public LinkStrand(){
        this("");
    }

    public LinkStrand(String s) {
        initialize(s);
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        Node current = myFirst;
        while (current != null) {
            ret.append(current.info);
            current = current.next;
        }

        return ret.toString();
    }

    @Override
    public long size() {
        return mySize;
    }

    @Override
    public void initialize(String source) {
        //myFirst = new Node(source);
        //myLast = myFirst;
        mySize = source.length();
        myAppends = 0;
        Node dnaStrand = new Node(source);
        myFirst = dnaStrand;
        myLast = dnaStrand;
        myIndex = 0;
        myLocalIndex = 0;
        myCurrent = myFirst;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        Node dn = new Node(dna);
        myLast.next = dn;
        myLast = dn;
        mySize += dna.length();
        myAppends++;
        return this;
    }

    @Override
    public IDnaStrand reverse() {
        Node first = null;
        Node current = myFirst;
        while (current != null) {
            Node add = new Node(current.info);
            add.next = first;
            first = add;
            current = current.next;
        }

        LinkStrand ret = new LinkStrand();
        Node last = first;
        while (last != null) {
            StringBuilder reverseStr = new StringBuilder(last.info);
            reverseStr.reverse();
            ret.append(reverseStr.toString());
            last = last.next;
        }
        return ret;
    }

    @Override
    public int getAppendCount() {
        return myAppends;
    }

    @Override
    public char charAt(int index) {
//       check to see if valid index
        if (index < 0 || index > mySize - 1) {
            throw new IndexOutOfBoundsException(("Index " + index + " is out of bounds!"));
        }
//      set my index for first run, access to variables if index is smaller than previously-accessed index
        if (myIndex == 0) {
            myIndex = ((int) mySize);
        }
        if (index < myIndex) {
            myCurrent = myFirst;
            myIndex = 0;
            myLocalIndex = 0;
        }
        /**
         * use myindex as counting variable to check if index is within the node
         * reset previous myLocalIndex as to not affect new runs
         * move to next node
         * increment variables to match location of index character within the node
         * return the desired character
         */
        while (myIndex != index) {
            if (myCurrent.info.length() < index - myIndex + 1 + myLocalIndex) {
                myIndex = myIndex - myLocalIndex + myCurrent.info.length();
                myLocalIndex = 0;
                if (myCurrent.next != null)
                    myCurrent = myCurrent.next;
                }
                else {
                    myLocalIndex += index - myIndex;
                    myIndex += index - myIndex;
                }
            }
            return myCurrent.info.charAt(myLocalIndex);
        }

}

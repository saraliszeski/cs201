import com.sun.jdi.IntegerValue;

import java.util.PriorityQueue;

/**
 * Although this class has a history of several years,
 * it is starting from a blank-slate, new and clean implementation
 * as of Fall 2018.
 * <P>
 * Changes include relying solely on a tree for header information
 * and including debug and bits read/written information
 *
 * @author Owen Astrachan
 */

public class HuffProcessor {

	public static final int BITS_PER_WORD = 8;
	public static final int BITS_PER_INT = 32;
	public static final int ALPH_SIZE = (1 << BITS_PER_WORD);
	public static final int PSEUDO_EOF = ALPH_SIZE;
	public static final int HUFF_NUMBER = 0xface8200;
	public static final int HUFF_TREE  = HUFF_NUMBER | 1;

	private final int myDebugLevel;

	public static final int DEBUG_HIGH = 4;
	public static final int DEBUG_LOW = 1;

	public HuffProcessor() {
		this(0);
	}

	public HuffProcessor(int debug) {
		myDebugLevel = debug;
	}

	/**
	 * Compresses a file. Process must be reversible and loss-less.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be compressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void compress(BitInputStream in, BitOutputStream out) {
		int[] counts = new int[ALPH_SIZE + 1];
		counts[PSEUDO_EOF] = 1;
		while (true) {
			int value = in.readBits(BITS_PER_WORD);
			if (value == -1) {
				break;
			}
			counts[value] += 1;
		}
		HuffNode t = makeTreefromCounts(counts);
		String[] encodings = new String[ALPH_SIZE+1];
		makeCodingsfromtree(t, encodings, "");
		out.writeBits(BITS_PER_INT, HUFF_TREE);
		writemytree(t, out);
		in.reset();
		writecompressedbits(in, encodings, out);
		out.close();

		if (myDebugLevel >= DEBUG_HIGH){
			for (int i = 0; i < ALPH_SIZE; i++){
				if (counts[i] != 0){
					System.out.printf("%d\t%d\n", i, counts[i]);
				}
			}

		}
	}
	private HuffNode makeTreefromCounts (int[] counts){
		PriorityQueue<HuffNode> pq = new PriorityQueue<>();
		for (int k = 0; k < counts.length; k ++){
			if (counts[k] >0){
				pq.add(new HuffNode(k, counts[k], null, null));
			}
		}
		System.out.printf("pq created with " + pq.size() + " nodes");
		while (pq.size() > 1){
			HuffNode left = pq.remove();
			HuffNode right = pq.remove();
			HuffNode node = new HuffNode(0, left.myWeight+ right.myWeight,left, right);
			pq.add(node);
		}
		HuffNode root = pq.remove();
		return root;
	}
	private String[] makeCodingsfromtree (HuffNode node, String[] encodings, String path){
		if (node.myRight == null && node.myLeft == null){
			encodings[node.myValue] = path;
		}
		else {
			makeCodingsfromtree(node.myLeft, encodings, path +"0");
			makeCodingsfromtree(node.myRight, encodings, path + "1");
		}

		return encodings;
	}

	private void writemytree (HuffNode node, BitOutputStream output){
		if (node == null){
			return;
		}
		if (node.myRight == null && node.myLeft == null){
			output.writeBits(1,1);
			output.writeBits(BITS_PER_WORD+1, node.myValue);
		}
		else{
			output.writeBits(1,0);
			writemytree(node.myLeft, output);
			writemytree(node.myRight, output);
		}

		return;
	}
	private BitOutputStream writecompressedbits (BitInputStream in, String[] encodings, BitOutputStream out) {
		while (true){
			int bit = in.readBits(BITS_PER_WORD);
			if (bit == -1){
				break;
			}
			String code = encodings[bit];
			out.writeBits(code.length(), Integer.parseInt(code, 2));

		}
//		for (int j = 0; j < encodings.length; j++) {
//			if (encodings[j] != null) {
//				String code = encodings[j];
//				out.writeBits(code.length(), Integer.parseInt(code, 2));
//			}
//		}
		String code = encodings[PSEUDO_EOF];
		out.writeBits(code.length(), Integer.parseInt(code, 2));
		return out;
		}

	/**
	 * Decompresses a file. Output file must be identical bit-by-bit to the
	 * original.
	 *
	 * @param in
	 *            Buffered bit stream of the file to be decompressed.
	 * @param out
	 *            Buffered bit stream writing to the output file.
	 */
	public void decompress(BitInputStream in, BitOutputStream out) {

		int magic = in.readBits(BITS_PER_INT);
		if (magic != HUFF_TREE) {
			throw new HuffException("invalid magic number " + magic);
		}
		HuffNode root = readTree(in);
		HuffNode current = root;
		while (true){
			int bits = in.readBits(1);
			if (bits == -1){
				throw new HuffException("bad input, no pseudo_eof");
			}
			else {
				if (bits == 0) {
					current = current.myLeft;
				}
				else {current = current.myRight;
				}
				if (current.myLeft == null && current.myRight == null) {
					if (current.myValue == PSEUDO_EOF) {
						break;
					} else {
						out.writeBits(BITS_PER_WORD, current.myValue);
						current = root;
					}
				}
			}
		}
		out.close();
	}
	private HuffNode readTree(BitInputStream bitcode){
		int bit = bitcode.readBits(1);
		if (bit== -1) {
			throw new HuffException("bit doesn't exist");
		}
		if (bit== 0){
			HuffNode left = readTree(bitcode);
			HuffNode right = readTree(bitcode);
			return new HuffNode(0,0, left, right);
		}
		else {
			int value = bitcode.readBits(BITS_PER_WORD+1);
			return new HuffNode(value, 0, null, null);
		}
	}
}
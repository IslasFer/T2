package t2; 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

public class HuffmanTree {

	public static final int ERROR = -3;
	public static final int INCOMPLETE_CODE = -2;
	public static final int END = Constant.NUM_Chars;
	private CharFrequncy theCounts;// char to frequency
	private HuffNode root;// root node
	private HuffNode[] nodeArray = new HuffNode[Constant.NUM_Chars + 1];// HuffNode
																		// array

        /**
        * Gera sitaxis Graphviz para o arvor grafico, percorre o tudo arvore
        * 
        * @return String "nodos" do fortamo para o sistema Graphviz
        * 
        **/
        public String syntaxGraphviz(){
                  
            return "digraph Grafo_Huffman{\n" + "size=\"8\"\n" + "node [shape = circle];\n" 
                    + syntaxGraphviz0(root) + "}" ;
          }
        
        public static String syntaxGraphviz0(HuffNode root){
            String nodos= "";
            
            if(root != null ){ 
                if (root.left != null) {           //O no não é folha, imprime a frequencia da folha    
                    //if (root.value!= ERROR && (int)root.value != INCOMPLETE_CODE) {
                        nodos += root.id + labelGraphviz(root, false)+
                                root.id +" -> "+ root.left.id + labelGraphviz(true) 
                                + root.id +" -> "+ root.right.id + labelGraphviz(false);
                                       
                }
                
                else 
                        nodos += root.id + labelGraphviz(root, true);
                
                return nodos + syntaxGraphviz0(root.left)+ syntaxGraphviz0(root.right);
            }
            
            else
                return nodos;
        }
        /**
         * 
         * @param izq
         * @return String 0 para esquerda /1 para direita
         */
        public static String labelGraphviz(boolean izq){
            String label = "";
            
            if(izq == true)
                return label+= "[ label = 0 ];\n";
            
            return label+= "[ label = 1 ];\n";
        }
        /**
         * Metodo valida se o no e folha o no de nivel
         * @param root
         * @param caracter boolean, 
         * @return String label,  
         * true == caracter, o no e folha e imprime caracter e frequencia
         * true != caracter, o no nao e folha e imprime so frequencia
         * 
         */
        public static String labelGraphviz(HuffNode root, boolean caracter){
            String label = " ";
            
            if (caracter && root.value>31 && root.value<254) {
                return label+= "[ label =\""+ (char)root.value +", "+ (int)root.weight+" \"];\n";
            }
            else
                return label+= "[ label ="+ root.weight+" ];\n";
            
        }

        
        
	public HuffmanTree() {
		theCounts = new CharFrequncy();
		root = null;
	}

	/**
	 * create the Huffman Tree based on the char counting
	 * 
	 * @param cc
	 *            CharCounter object
	 */
	public HuffmanTree(CharFrequncy cc) {
		theCounts = cc;
		root = null;
		createHuffmanTree();
	}

	/**
	 * Get the node then traverse from leaf to the root to get the Huffman code
	 * 
	 * @param ch
	 *            index of the character in the array.
	 * @return an int array of Huffman code of the given character.
	 */
	public int[] getCode(int ch) {
		HuffNode current = nodeArray[ch];

		if (current == null)
			return null;
		String v = "";
		HuffNode parent = current.parent;

		while (parent != null) {
			if (parent.left == current)
				v = "0" + v;
			else
				v = "1" + v;
			// from bottom to up
			current = current.parent;
			parent = current.parent;
		}
		int len = v.length();
		int[] result = new int[len];// initialize a result array
		for (int i = 0; i < len; i++)
			result[i] = v.charAt(i) == '0' ? 0 : 1;
		return result;
	}

	/**
	 * Given a Huffman code, get the character coresponding to the code
	 * 
	 * @param code
	 *            the Huffman code
	 * @return the character
	 */
	public int getChar(String code) {
		HuffNode p = this.root;
                

		for (int i = 0; i < code.length(); i++) {
			// current bit is 0, go left
			if (code.charAt(i) == '0') {
				p = p.left;
			}
			// current bit is 1, go right
			else {
				p = p.right;
			}

			// Error
			if (p == null) {
				return this.ERROR;
			}
		}

		return p.value;
	}

	/**
	 * Write the header into the output stream
	 * 
	 * @param out
	 *            The output stream
	 * @return Number of bits have written into the output stream
	 * @throws IOException
	 */
	public int writeHeader(DataOutputStream out) throws IOException {
		int bitsWritten = 0;
		for (int i = 0; i < Constant.NUM_Chars; i++) {
			if (theCounts.getCount(i) > 0) {
				out.writeByte(i);// write a byte
				out.writeInt(theCounts.getCount(i));// write frequency
				bitsWritten += 1 + Integer.SIZE;
			}
		}
		// write a 0 indicating the end of the table
		out.writeByte(0);
		out.writeInt(0);
		bitsWritten += 1 + Integer.SIZE;

		return bitsWritten;
	}

	/**
	 * Read the header from the input stream
	 * 
	 * @param in
	 *            The input stream
	 * @throws IOException
	 */
	public void readHeader(DataInputStream in) throws IOException {
		for (int i = 0; i < Constant.NUM_Chars; i++) {
			this.theCounts.setCount(i, 0);
		}

		byte ch = 0;
		int num = -1;

		// Read in the counts
		while (true) {
			ch = in.readByte();
			num = in.readInt();

			if (num == 0) {
				break;
			}

			theCounts.setCount(ch, num);
		}

		// Create a Huffman tree from the header just read
		this.createHuffmanTree();
	}

	/**
	 * construct huffman tree
	 */
	public void createHuffmanTree() {
		// create a priority queue to save HuffNode
		PriorityQueue<HuffNode> pq = new PriorityQueue<HuffNode>();

		for (int i = 0; i < Constant.NUM_Chars; i++) {
			if (theCounts.getCount(i) > 0) {
				HuffNode newNode = new HuffNode(i, theCounts.getCount(i), null,
						null, null);
				nodeArray[i] = newNode;
				pq.add(newNode);// add the new node.
			}
		}

		nodeArray[END] = new HuffNode(END, 1, null, null, null);
		pq.add(nodeArray[END]);

		while (pq.size() > 1) {
			// get the two smallest nodes each time.
			HuffNode n1 = pq.remove();
			HuffNode n2 = pq.remove();
			// merge two nodes to one nodes.
			HuffNode result = new HuffNode(INCOMPLETE_CODE, n1.weight
					+ n2.weight, n1, n2, null);
			n1.parent = n2.parent = result;
			// construct and push it to priority queue.
			pq.add(result);
		}
		root = pq.element();
	}

	int getValidChar() {
		return theCounts.getValidChar();
	}
}
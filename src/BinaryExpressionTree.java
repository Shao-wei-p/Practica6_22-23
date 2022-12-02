
import java.util.*;

public class BinaryExpressionTree {


	//cronstructor de árbol de expresiones a partir de una expresión notación potfijas completamente parentizada

	public static EDBinaryNode<Character> buildBinaryExpressionTree(String chain) {

		EDBinaryNode<Character> tree = null;
		if (chain == null || chain.length() == 0)
			return tree;

		Stack<EDBinaryNode<Character>> pila = new Stack<>();
		boolean error = false;

		int i = 0;

		while (i < chain.length() && !error) {
			Character c = chain.charAt(i);
			if (Character.isDigit(c)) {
				EDBinaryNode<Character> single = new EDBinaryNode<>(c);
				pila.push(single);
			} else if (isValidOperator(c)) {
				EDBinaryNode<Character> rightS = null, leftS = null;
				if (!pila.empty()) {
					rightS = pila.peek();
					pila.pop();
				} else error = true;  //wrong input chain
				if (!error) {
					if (!pila.empty()) {
						leftS = pila.peek();
						pila.pop();
					} else error = true; //wrong input chain
				}
				if (!error) {
					EDBinaryNode<Character> b = new EDBinaryNode<>(c, leftS, rightS);
					pila.push(b);
				}
			} else if (c != ' ') error = true; //wrong input chain, operator
			i++;  //next character in chain
		}
		if (!error) {
			if (!pila.empty()) {
				tree = pila.peek();
				pila.pop();
			} else error = true; //wrong input chain
		}
		if (!pila.empty()) error = true; //stack must be empty at the end

		if (error) return null;
		else return tree;
	}


	/**
	 * Determines whether the tree is extended or not, i.e., each node has either two or none siblings.
	 *
	 * @return <code>true</code> if extended.
	 */
	public static boolean isExtended(EDBinaryNode<Character> tree) {
		if (tree == null)
			return true;
		if (tree.isLeaf())
			return true;
		if (!tree.hasLeft() || !tree.hasRight())
			return false;
		return isExtended(tree.left()) && isExtended(tree.right());

	}


	/**
	 * Given an expression tree, detemrines whether all the leaves contain digits and
	 * internal nodes only contain valid operators
	 *
	 * @param tree the binary expression tree
	 * @return <code>true</code> if digits are only on the leaves.
	 */
	// EJERCICIO 2
	public static boolean digistsOnLeaves(EDBinaryNode<Character> tree) {
		if (tree==null)
			return true;
		if (tree.isLeaf())
			return tree.data()>='0' && tree.data()<='9';
		if (!isValidOperator(tree.data()))
			return false;
		return digistsOnLeaves(tree.left()) && digistsOnLeaves(tree.right());

	}

	private static boolean isValidOperator(Character c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^')
			return true;
		else return false;
	}


	/**
	 * Given a binary expression tree, computes the result of executing the operations it contains.
	 *
	 * @param tree a correct binary expression tree
	 * @return The result of the evaluation.
	 */
	public static float evaluate(EDBinaryNode<Character> tree) {
		float res=0;
		if (!tree.isLeaf()){
			float izq=evaluate(tree.left());
			float der=evaluate(tree.right());
			if (isValidOperator(tree.data())){
				if (tree.data().equals('+'))
					res=izq+der;
				else if (tree.data().equals('-'))
					res=izq-der;
				else if(tree.data().equals('*'))
					res=izq*der;
				else if(tree.data().equals('/'))
					res=izq/der;
				else
					res= (float) Math.pow(izq,der);
			}
		}else
			return tree.data()-'0';
		return res;
	}

	/**
	 * Returns a list with the result of traversing the nodes of an expression tree in inOrder.
	 * The list must be completely parenthesised.
	 *
	 * @param tree a correct binary expression tree
	 * @return The list with the nodes of the tree in inorder
	 */
	public static List<Character> asListInorder(EDBinaryNode<Character> tree) {
		List res=new LinkedList();
		if(tree!=null){
			res.add('(');
			asListInorder(tree.left(),res);
			res.add(tree.data());
			asListInorder(tree.right(),res);
			res.add(')');
		}
		return res;
	}

	private static void asListInorder(EDBinaryNode<Character> r, List lista) {
		if(r!=null){
			if (r.isLeaf())
				lista.add(r.data());
			else {
				lista.add('(');
				asListInorder(r.left(),lista);
				lista.add(r.data());
				asListInorder(r.right(),lista);
				lista.add(')');
			}
		}
	}


	/**
	 * isSymmetric (EDBinaryNode<T> )
	 *
	 * @param tree BinaryTree
	 * @return true if tree holds the symmetric property in content and in shape
	 */
	public static <T> boolean isSymmetric(EDBinaryNode<T> tree) {
		if(tree==null)
			return true;
		if(tree.isLeaf())
			return true;
		if(tree.hasRight() && tree.hasLeft())
			return isSymmetric(tree.left(), tree.right());
		return false;
	}

	private static <T> boolean isSymmetric(EDBinaryNode<T> l, EDBinaryNode<T> r) {
		if(l==null && r == null)
			return true;
		if (l==null || r==null)
			return false;
		if(l.isLeaf() && r.isLeaf())
			return l.data().equals(r.data());
		if (l.data().equals(r.data()))
			return isSymmetric(l.left(),r.right()) && isSymmetric(l.right(),r.left());
		else
			return false;
	}


	/**
	 * contains
	 * Checks if a binary tree subtree is contained in another binary tree, tree
	 *
	 * @param tree    a binary tree
	 * @param subtree a binary tree
	 * @return true if subtree is contained in tree
	 */
	public static <T> boolean contains(EDBinaryNode<T> tree, EDBinaryNode<T> subtree) {
		if(tree== null && subtree==null)
			return true;
		if (tree == null)
			return false;
		if (subtree == null)
			return true;
		if(tree.isLeaf() && subtree.isLeaf())
			return tree.data().equals(subtree.data());
		if (tree.data().equals(subtree.data())) {
			if (isSame(tree, subtree))
				return true;
		}
		return contains(tree.left(),subtree) || contains(tree.right(),subtree);
	}

	private static <T> boolean isSame(EDBinaryNode<T> tree, EDBinaryNode<T> subtree){
		if(tree== null && subtree==null)
			return true;
		if (tree==null)
			return false;
		if(subtree==null)
			return true;
		if(tree.isLeaf() && subtree.isLeaf())
			return tree.data().equals(subtree.data());
		else {
			if(tree.data().equals(subtree.data()))
				return isSame(tree.left(),subtree.left()) && isSame(tree.right(),subtree.right());
			return false;
		}
	}


	// methods needed for toString
	private static StringBuilder repeated(char c, int times) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++)
			sb.append(c);

		return sb;
	}

	private static int depth(EDBinaryNode<Character> node) {
		if (node == null)
			return 0;

		int ld = depth(node.left()) + 1;
		int rd = depth(node.right()) + 1;

		return ld > rd ? ld : rd;
	}

	private static int createSpaces(EDBinaryNode<Character> tree, List<String> offset, List<String> separator) {
		int depth = depth(tree);

		offset.clear();
		separator.clear();

		if (depth < 1)
			return 0;

		int pad = 1;
		for (int i = 0; i < depth; i++) {
			offset.add(0, repeated(' ', pad - 1).toString());
			pad = 2 * pad + 1;
			separator.add(0, repeated(' ', pad).toString());
		}

		return depth;
	}

	public static String toString(EDBinaryNode<Character> tree) {
		StringBuilder resultado = new StringBuilder();
		Queue<EDBinaryNode<Character>> q = new LinkedList<>();
		List<String> margen = new LinkedList<>();
		List<String> separacion = new LinkedList<>();
		int altura = createSpaces(tree, margen, separacion);

		if (altura == 0) {
			resultado.append("------------\n");
			resultado.append(" Empty tree\n");
			resultado.append("------------\n");
			return resultado.toString();
		}

		StringBuilder barra = repeated('-', ((1 << (altura - 1)) * 4) - 3);

		int nactual = 0;
		int cuenta = 1;
		resultado.append(barra).append('\n').append(margen.get(nactual));
		q.add(tree);

		while (nactual < altura) {
			EDBinaryNode<Character> n = q.remove();
			String dato = " ";
			if (n != null)
				dato = n.data().toString();

			resultado.append(dato);
			cuenta--;
			if (cuenta > 0)
				resultado.append(separacion.get(nactual));
			else {
				resultado.append('\n');
				nactual++;
				cuenta = 1 << nactual;

				if (nactual < altura)
					resultado.append(margen.get(nactual));
				else
					resultado.append(barra).append('\n');
			}

			if (nactual < altura) {
				if (n == null) {
					q.add(null);
					q.add(null);
				} else {
					q.add(n.left());
					q.add(n.right());
				}
			}
		}
		return resultado.toString();
	}
}

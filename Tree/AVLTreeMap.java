/**
 * Class that implements an AVL tree which implements the MyMap interface.
 * @author Yizhen Zhang
 * @version Nov 2, 2022
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends BSTMap<K, V>
        implements MyMap<K, V> {
    private static final int ALLOWED_IMBALANCE = 1;

    /**
     * Creates an empty AVL tree map.
     */
    public AVLTreeMap() { }

    public AVLTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a AVL tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created via a divide-and-conquer
     * approach. If sorted is false, the pairs will be inserted in the order
     * they are received, and the tree will be rotated to maintain the AVL tree
     * balance property.
     * @param elements an array of key-value pairs
     */
    public AVLTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        Pair<K, V> pair = pairs[mid];
        Node<K, V> parent = new Node<>(pair.key, pair.value);
        size++;
        parent.left = createBST(pairs, low, mid - 1);
        if (parent.left != null) {
            parent.left.parent = parent;
        }
        parent.right = createBST(pairs, mid + 1, high);
        if (parent.right != null) {
            parent.right.parent = parent;
        }
        // This line is critical for being able to add additional nodes or to
        // remove nodes. Forgetting this line leads to incorrectly balanced
        // trees.
        parent.height =
                Math.max(avlHeight(parent.left), avlHeight(parent.right)) + 1;
        return parent;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
        NodeOldValuePair nvp = new NodeOldValuePair(null, null);
        nvp = insertAndBalance(key, value, root, nvp);
        return nvp.oldValue;
    }

    public Node<K, V> iterativeSearch(K key,Node<K,V> temp) {
        while(temp != null){
            if (temp.key.compareTo(key) == 0){
                return temp;
            }
            else if (temp.key.compareTo(key) < 0){
                temp = temp.right;
            }else{
                temp = temp.left;
            }
        }
        return null;
    }
    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */

    public V remove(K key) {
        // Replace the line with the code required for proper removal from an
        // AVL tree. This task is extra credit.
        V ans = get(key);
        if (remove(key,root)!= null)
            size --;
        return ans;
    }
    public Node<K,V> remove(K key, Node<K,V> t){
        if (t == null || get(key) == null)
            return null;
        if (size() == 1 && key == root.key){
            Node<K,V> prev_r = root;
            root = null;
            return prev_r;
        }
        int compareResult = key.compareTo(t.key);

        if (compareResult < 0){
            t.left = remove(key,t.left);
            if (t.left != null){

                if (t.left.parent != null)
                    t.left.parent.parent =t.left;
                t.left.parent = t;
            }
        }
        else if (compareResult > 0){
            t.right = remove(key,t.right);
            if (t.right != null){
                if (t.right.parent != null)
                    t.right.parent.parent =t.right;
                t.right.parent = t;

            }
        }
        else if (t.left != null && t.right != null){
            t.key = treeMinimum(t.right).key;
            t.value = treeMinimum(t.right).value;
            t.right = remove(t.key,t.right);
            if (t.right != null) {
                if (t.right.parent != null)
                    t.right.parent.parent =t.right;

                t.right.parent = t;
            }
        }
        else{

            Node<K,V> p = t.parent;

            if (t.left != null){
                if (t == root) {
                    root = t.left;
                    if (root != null)
                        root.parent = null;
                }
                t = t.left;
                if (t != null)
                    t.parent = p;

            }else{
                if (t == root) {
                    root = t.right;
                    if (root != null)
                        root.parent = null;

                }

                if (t.right == null)
                    t.parent = null;
                t = t.right;
                if (t != null) {
                    t.parent = p;

                }

            }

            if (t != root && t!= null){

                t.parent = p;
            }


        }



        t = balance(t);


        return t;
    }


    private NodeOldValuePair insertAndBalance(
            K key, V value, Node<K, V> t, NodeOldValuePair nvp) {
        if (t == null ) {
            size++;
            nvp.node = new Node<K, V>(key, value);
            if (root == null) {
                root = nvp.node;
            }
            return nvp;
        }
        int comparison = key.compareTo(t.key);

        if (comparison == 0){

            nvp.node = new Node<K,V>(key,value);
            nvp.oldValue  = t.value;
            t.value = value;


        }
        // TODO
        else if (comparison < 0) {
            nvp = insertAndBalance(key, value, t.left, nvp);
            t.left = nvp.node;
            if (nvp.node != null)
                nvp.node.parent = t;

        }
        else{
                nvp = insertAndBalance(key,value,t.right,nvp);
                t.right = nvp.node;
                if (nvp.node != null)
                    nvp.node.parent = t;
        }


        Node<K, V> n = balance(t);
        nvp.node = n;
        return nvp;
    }

    private Node<K, V> balance(Node<K, V> t) {
        // TODO
        if (t == null)
            return t;

        if (avlHeight(t.left) - avlHeight(t.right) > 1){
            if (avlHeight(t.left.left)>= avlHeight(t.left.right))
                t = rotateWithLeftChild(t);
            else
                t = doubleWithLeftChild(t);
        }else{
            if (avlHeight(t.right) - avlHeight(t.left) > 1){
                if (avlHeight(t.right.right)>= avlHeight(t.right.left))
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);
            }


        }
        t.height = Math.max(avlHeight(t.left),avlHeight(t.right)) + 1;
        return t;
    }

    private int avlHeight(Node<K, V> t) {
        return t == null ? -1 : t.height;
    }

    private Node<K, V> rotateWithLeftChild(Node<K, V> k2) {


        Node<K,V> k1 = k2.left;
        if (k1.right != null)
            k1.right.parent = k2;
        if (k2 == root) {
            root = k1;
            k1.parent = null;
        }
        k2.left = k1.right;
        k1.right = k2;
        k2.parent = k1;
        if (k1.right != null) {
            k1.right.parent = k2;
        }

        k2.height = Math.max(avlHeight(k2.left),avlHeight(k2.right)) +1;
        k1.height = Math.max(avlHeight(k1.left),k2.height) +1;
        return k1;
    }

    private Node<K, V> rotateWithRightChild(Node<K, V> k1) {
        // TODO
        Node<K,V> k2 = k1.right;

        if (k1 == root) {
            root = k2;
            k2.parent = null;
        }
        k1.right = k2.left;
        k2.left = k1;

        k1.parent = k2;

        k1.height = Math.max( avlHeight( k1.left), avlHeight(k1.right) ) +1 ;
        k2.height = Math.max( k1.height, avlHeight(k2.right) ) +1 ;
        return k2;

    }

    private Node<K, V> doubleWithLeftChild(Node<K, V> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private Node<K, V> doubleWithRightChild(Node<K, V> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private class NodeOldValuePair {
        Node<K, V> node;
        V oldValue;

        NodeOldValuePair(Node<K, V> n, V oldValue) {
            this.node = n;
            this.oldValue = oldValue;
        }
    }

    public static void main(String[] args) {
        boolean usingInts = true;
        if (args.length > 0) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException nfe) {
                usingInts = false;
            }
        }

        AVLTreeMap avlTree;
        if (usingInts) {
            @SuppressWarnings("unchecked")
            Pair<Integer, Integer>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    int val = Integer.parseInt(args[i]);
                    pairs[i] = new Pair<>(val, val);
                } catch (NumberFormatException nfe) {
                    System.err.println("Error: Invalid integer '" + args[i]
                            + "' found at index " + i + ".");
                    System.exit(1);
                }
            }
            avlTree = new AVLTreeMap<Integer, Integer>(pairs);
        } else {
            @SuppressWarnings("unchecked")
            Pair<String, String>[] pairs = new Pair[args.length];
            for (int i = 0; i < args.length; i++) {
                pairs[i] = new Pair<>(args[i], args[i]);
            }
            avlTree = new AVLTreeMap<String, String>(pairs);
        }

        System.out.println(avlTree.toAsciiDrawing());
        System.out.println();
        System.out.println("Height:                   " + avlTree.height());
        System.out.println("Total nodes:              " + avlTree.size());
        System.out.printf("Successful search cost:   %.3f\n",
                avlTree.successfulSearchCost());
        System.out.printf("Unsuccessful search cost: %.3f\n",
                avlTree.unsuccessfulSearchCost());
        avlTree.printTraversal(PREORDER);
        avlTree.printTraversal(INORDER);
        avlTree.printTraversal(POSTORDER);
    }
}

class RedBlackTree{

    private RBNode NIL;
    private RBNode root;
    
    enum Color {
        RED, BLACK
    }

    class RBNode{
        int data;
        Color color;
        RBNode left, right, parent;

        RBNode(int data) {
            this.data = data;
            this.color = Color.RED; // New node is red by default
            left = right = parent = null;
        }
    }


    RedBlackTree() {
        root = null;
        root.color = "BLACK"; // Root is always black
    }

    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data)
            root.left = insertRec(root.left, data);
        else if (data > root.data)
            root.right = insertRec(root.right, data);
        
}
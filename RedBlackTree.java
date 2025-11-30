import java.util.*;

public class RedBlackTree{

    RBNode NIL;
    RBNode root;
    private int treeSize = 0;
    
    public enum Color {
        RED, BLACK
    }

    public class RBNode{
        int data;
        Color color;
        RBNode left, right, parent;

        RBNode(int data) {
            this.data = data;
            this.color = Color.RED; // New node is red by default
            this.left = this.right = this.parent = null;
            //left.color = right.color = Color.BLACK; // NIL children are black
        }
    }


    RedBlackTree() {
        NIL = new RBNode(0);
        NIL.left = NIL.right = NIL.parent = null;
        NIL.color = Color.BLACK; // Root is always black
        root = NIL;
    }

    public void insert(int data) {
        RBNode newNode = new RBNode(data);
        newNode.left = newNode.right = NIL;
        bstInsert(newNode);
        fixInsert(newNode); 
        treeSize++;
    }

    public void bstInsert(RBNode newNode) {
        RBNode y = NIL;
        RBNode x = root;

        while (x != NIL) {
            y = x;
            if (newNode.data < x.data) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = y;
        if (y == NIL) {
            root = newNode; // Tree was empty
        } else if (newNode.data < y.data) {
            y.left = newNode;
        } else {
            y.right = newNode;
        }
    }

    public void fixInsert(RBNode z) {
        while (z.parent.color == Color.RED) {
            if (z.parent == z.parent.parent.left) {
                RBNode y = z.parent.parent.right; // Uncle
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        leftRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                RBNode y = z.parent.parent.left; // Uncle
                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rightRotate(z);
                    }
                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }
        root.color = Color.BLACK; // Ensure root is always black
    }

    public void leftRotate(RBNode x) {
        RBNode y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    public void rightRotate(RBNode y) {
        RBNode x = y.left;
        y.left = x.right;
        if (x.right != NIL) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == NIL) {
            root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        x.right = y;
        y.parent = x;
    }

    public boolean contains(int key) {
        return searchNode(key) != NIL;
    }

    private RBNode searchNode(int key) {
        RBNode current = root;

        while (current != NIL) {
            if (key == current.data) {
                return current;
            } else if (key < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return NIL;
    }

    public int size() {
        return treeSize;
    }

    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

private void inOrderHelper(RBNode node, List<Integer> list) {
    if (node == NIL) return;
    inOrderHelper(node.left, list);
    list.add(node.data);
    inOrderHelper(node.right, list);
}
    
}
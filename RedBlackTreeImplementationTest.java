import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.*;

public class RedBlackTreeImplementationTest {
    @Test
    public void testEmptyTree() {
        RedBlackTree t = new RedBlackTree();
        assertEquals(0, t.size());
        assertFalse(t.contains(10));
        assertTrue(t.inOrder().isEmpty());
        assertTrue(isValidRedBlackTree(t));
    }

    @Test
    public void testSingleInsert() {
        RedBlackTree t = new RedBlackTree();
        t.insert(10);

        assertEquals(1, t.size());
        assertTrue(t.contains(10));
        assertTrue(isValidRedBlackTree(t));
    }

    @Test
    public void testMultipleInsert() {
        RedBlackTree t = new RedBlackTree();
        int[] keys = {10, 5, 15, 1, 7, 12, 20};
        for (int k : keys) t.insert(k);

        List<Integer> sorted = Arrays.asList(1, 5, 7, 10, 12, 15, 20);
        sorted.sort(Integer::compareTo);
        assertEquals(sorted, t.inOrder());
        assertTrue(isValidRedBlackTree(t));
    }

    @Test
    public void testDuplicateInsert_NoGrowth() {
        RedBlackTree t = new RedBlackTree();
        t.insert(10);
        t.insert(10);     // assume duplicates ignored

        assertEquals(1, t.size());
        assertTrue(isValidRedBlackTree(t));
    }

    /* ---------------------------------------------------------
     * ROTATION / STRUCTURE BEHAVIOR TESTS
     * (trigger known rotation scenarios)
     * --------------------------------------------------------- */

    @Test
    public void testLeftRightCaseInsert() {
        RedBlackTree t = new RedBlackTree();
        t.insert(10);
        t.insert(5);
        t.insert(7);      // triggers LR rotation in RBT

        List<Integer> expected = Arrays.asList(5,7,10);
        assertEquals(expected, t.inOrder());
        assertTrue(isValidRedBlackTree(t));
    }

    @Test
    public void testRightLeftCaseInsert() {
        RedBlackTree t = new RedBlackTree();
        t.insert(10);
        t.insert(15);
        t.insert(12);     // triggers RL rotation

        List<Integer> expected = Arrays.asList(10, 12, 15);
        assertEquals(expected, t.inOrder());
        assertTrue(isValidRedBlackTree(t));
    }

    /* ---------------------------------------------------------
     * INTEGRATION TESTS
     * --------------------------------------------------------- */

    @Test
    public void testIncreasingSequence() {
        RedBlackTree t = new RedBlackTree();
        for (int i = 1; i <= 100; i++) t.insert(i);

        assertEquals(100, t.size());
        assertEquals(generateRange(1, 100), t.inOrder());
        assertTrue(isValidRedBlackTree(t));
    }

    @Test
    public void testRandomizedInsertions() {
        RedBlackTree t = new RedBlackTree();
        Random r = new Random(42);

        Set<Integer> inserted = new TreeSet<>();
        for (int i = 0; i < 200; i++) {
            int val = r.nextInt(1000);
            inserted.add(val);
            t.insert(val);
        }

        assertEquals(inserted.size(), t.size());
        assertEquals(new ArrayList<>(inserted), t.inOrder());
        assertTrue(isValidRedBlackTree(t));
    }
     /* ---------------------------------------------------------
     * RED-BLACK TREE VALIDATOR
     * --------------------------------------------------------- */

    /** Fully checks all RB invariants */
    private boolean isValidRedBlackTree(RedBlackTree t) {
        // 1. In-order sorted ⇒ BST property
        List<Integer> in = t.inOrder();
        for (int i = 1; i < in.size(); i++) {
            if (in.get(i - 1) >= in.get(i)) return false;
        }

        // 2. Root must be black
        if (t.root.color != RedBlackTree.Color.BLACK) return false;

        // 3. NIL must always be black
        if (t.NIL.color != RedBlackTree.Color.BLACK) return false;

        // 4. Check invariants on every subtree
        return validateNode(t.root, t);
    }

    /**
     * Validates:
     * - no red parent has red children
     * - consistent black-height across all leaf paths
     */
    private boolean validateNode(RedBlackTree.RBNode node, RedBlackTree t) {
        if (node == t.NIL) {
            // NIL contributes 1 black to every path
            return true;
        }

        // red-parent rule
        if (node.color == RedBlackTree.Color.RED) {
            if (node.left.color == RedBlackTree.Color.RED || node.right.color == RedBlackTree.Color.RED) {
                return false;
            }
        }

        // Recursively validate subtrees
        if (!validateNode(node.left, t)) return false;
        if (!validateNode(node.right, t)) return false;

        // Validate black-height consistency
        int leftBH = blackHeight(node.left, t);
        int rightBH = blackHeight(node.right, t);
        if (leftBH != rightBH) return false;

        return true;
    }

    /**
     * Computes black height. If inconsistent, returns -1.
     */
    private int blackHeight(RedBlackTree.RBNode node, RedBlackTree t) {
        if (node == t.NIL) return 1;

        int left = blackHeight(node.left, t);
        int right = blackHeight(node.right, t);
        if (left == -1 || right == -1 || left != right) return -1;

        return left + (node.color == RedBlackTree.Color.BLACK ? 1 : 0);
    }

    /** Utility to generate a range of integers */
    private List<Integer> generateRange(int start, int end) {
        List<Integer> range = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            range.add(i);
        }
        return range;
    }

}

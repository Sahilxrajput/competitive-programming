import java.util.*;

public class Main {

    static class Node {
        int open;
        int close;
        int len;

        Node(int open, int close, int len) {
            this.open = open;
            this.close = close;
            this.len = len;
        }
    }

    static class SegmentTree {

        Node[] tree;
        int n;

        SegmentTree(char[] arr) {
            n = arr.length;
            tree = new Node[4 * n];
            build(0, 0, n - 1, arr);
        }

        void build(int node, int start, int end, char[] arr) {

            if (start == end) {

                if (arr[start] == '(') {
                    tree[node] = new Node(1, 0, 0);
                } else {
                    tree[node] = new Node(0, 1, 0);
                }

                return;
            }

            int mid = start + (end - start) / 2;

            build(2 * node + 1, start, mid, arr);
            build(2 * node + 2, mid + 1, end, arr);

            tree[node] = merge(
                    tree[2 * node + 1],
                    tree[2 * node + 2]);
        }

        Node merge(Node left, Node right) {
            if (left == null)
                return right;
            if (right == null)
                return left;

            int matched = Math.min(left.open, right.close);

            int len = left.len + right.len + 2 * matched;

            int open = left.open + right.open - matched;

            int close = left.close + right.close - matched;

            return new Node(open, close, len);
        }

        Node query(int l, int r) {
            return queryHelper(0, 0, n - 1, l, r);
        }

        Node queryHelper(
                int node,
                int start,
                int end,
                int l,
                int r) {

            // Completely outside
            if (r < start || end < l) {
                return new Node(0, 0, 0);
            }

            // Completely inside
            if (l <= start && end <= r) {
                return tree[node];
            }

            int mid = start + (end - start) / 2;

            Node left = queryHelper(
                    2 * node + 1,
                    start,
                    mid,
                    l,
                    r);

            Node right = queryHelper(
                    2 * node + 2,
                    mid + 1,
                    end,
                    l,
                    r);

            return merge(left, right);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String s = sc.next();
        char[] arr = s.toCharArray();

        int q = sc.nextInt();

        SegmentTree st = new SegmentTree(arr);

        while (q-- > 0) {

            // Convert to 0-based
            int l = sc.nextInt() - 1;
            int r = sc.nextInt() - 1;

            Node ans = st.query(l, r);

            System.out.println(ans.len);
        }

        sc.close();
    }
}
public class Segment {
    static class SegmentTree {

        int[] tree;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];

            build(0, 0, n - 1, arr);
        }

        void build(int node, int start, int end, int[] arr) {

            if (start == end) {
                tree[node] = arr[start];
                return;
            }

            int mid = start + (end - start) / 2;

            build(2 * node + 1, start, mid, arr);
            build(2 * node + 2, mid + 1, end, arr);

            tree[node] = merge(
                    tree[2 * node + 1],
                    tree[2 * node + 2]);
        }

        void update(int pos, int value) {
            updateHelper(0, 0, n - 1, pos, value);
        }

        void updateHelper(int node, int start, int end,
                int pos, int value) {

            if (start == end) {
                tree[node] = value;
                return;
            }

            int mid = start + (end - start) / 2;

            if (pos <= mid) {
                updateHelper(
                        2 * node + 1,
                        start,
                        mid,
                        pos,
                        value);
            } else {
                updateHelper(
                        2 * node + 2,
                        mid + 1,
                        end,
                        pos,
                        value);
            }

            tree[node] = merge(
                    tree[2 * node + 1],
                    tree[2 * node + 2]);
        }

        int query(int l, int r) {
            return queryHelper(0, 0, n - 1, l, r);
        }

        int queryHelper(int node, int start, int end,
                int l, int r) {

            // Completely outside
            if (r < start || end < l) {
                // Change this depending on the problem
                return 0;
            }

            // Completely inside
            if (l <= start && end <= r) {
                return tree[node];
            }

            int mid = start + (end - start) / 2;

            int left = queryHelper(
                    2 * node + 1,
                    start,
                    mid,
                    l,
                    r);

            int right = queryHelper(
                    2 * node + 2,
                    mid + 1,
                    end,
                    l,
                    r);

            return merge(left, right);
        }

        int merge(int left, int right) {
            // Change this depending on the problem
            // Default: SUM
            return left + right;
        }

        int getRoot() {
            return tree[0];
        }
    }

    public static void main(String[] args) {
        
    }
}

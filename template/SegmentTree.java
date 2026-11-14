// class SegmentTree {

//     class Node {
//         int open;
//         int close;
//         int len;

//         Node(int open, int close, int len) {
//             this.open = open;
//             this.close = close;
//             this.len = len;
//         }
//     }

//     Node[] tree;
//     int n;

//     SegmentTree(char[] arr) {
//         n = arr.length;
//         tree = new Node[4 * n];
//         build(0, 0, n - 1, arr);
//     }

//     void build(int node, int start, int end, char[] arr) {

//         if (start == end) {

//             if (arr[start] == '(') {
//                 tree[node] = new Node(1, 0, 0);
//             } else {
//                 tree[node] = new Node(0, 1, 0);
//             }

//             return;
//         }

//         int mid = start + (end - start) / 2;

//         build(2 * node + 1, start, mid, arr);
//         build(2 * node + 2, mid + 1, end, arr);

//         tree[node] = merge(
//                 tree[2 * node + 1],
//                 tree[2 * node + 2]);
//     }

//     Node merge(Node left, Node right) {
//         if (left == null)
//             return right;
//         if (right == null)
//             return left;

//         int matched = Math.min(left.open, right.close);

//         int len = left.len + right.len + 2 * matched;

//         int open = left.open + right.open - matched;

//         int close = left.close + right.close - matched;

//         return new Node(open, close, len);
//     }

//     Node query(int l, int r) {
//         return queryHelper(0, 0, n - 1, l, r);
//     }

//     Node queryHelper(
//             int node,
//             int start,
//             int end,
//             int l,
//             int r) {

//         // Completely outside
//         if (r < start || end < l) {
//             return new Node(0, 0, 0);
//         }

//         // Completely inside
//         if (l <= start && end <= r) {
//             return tree[node];
//         }

//         int mid = start + (end - start) / 2;

//         Node left = queryHelper(
//                 2 * node + 1,
//                 start,
//                 mid,
//                 l,
//                 r);

//         Node right = queryHelper(
//                 2 * node + 2,
//                 mid + 1,
//                 end,
//                 l,
//                 r);

//         return merge(left, right);
//     }
// }



class SegmentTree {
    int n;
    int[] tree;

    SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }

    int merge(int a, int b) {
        return a + b;
    }

    int identity() {
        return 0; // Sum: 0, Min: INF, Max: -INF
    }

    void build(int[] arr, int node, int l, int r) {
        if (l == r) {
            tree[node] = arr[l];
            return;
        }

        int mid = l + (r - l) / 2;

        build(arr, 2 * node + 1, l, mid);
        build(arr, 2 * node + 2, mid + 1, r);

        tree[node] = merge(
                tree[2 * node + 1],
                tree[2 * node + 2]);
    }

    void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            tree[node] = val;
            return;
        }

        int mid = l + (r - l) / 2;

        if (idx <= mid)
            update(2 * node + 1, l, mid, idx, val);
        else
            update(2 * node + 2, mid + 1, r, idx, val);

        tree[node] = merge(
                tree[2 * node + 1],
                tree[2 * node + 2]);
    }

    int query(int node, int l, int r, int ql, int qr) {
        // No overlap
        if (qr < l || r < ql)
            return identity();

        // Complete overlap
        if (ql <= l && r <= qr)
            return tree[node];

        // Partial overlap
        int mid = l + (r - l) / 2;

        int left = query(2 * node + 1, l, mid, ql, qr);
        int right = query(2 * node + 2, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    void update(int idx, int val) {
        update(0, 0, n - 1, idx, val);
    }

    int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }
}

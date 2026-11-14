class LazySegmentTree {
    int[] tree;
    int[] lazy;
    int n;

    LazySegmentTree(int[] arr) {
        n = arr.length;

        tree = new int[4 * n];
        lazy = new int[4 * n];

        build(arr, 0, 0, n - 1);
    }

    // Build Segment Tree
    void build(int[] arr, int node, int l, int r) {
        if (l == r) {
            tree[node] = arr[l];
            return;
        }

        int mid = l + (r - l) >> 2;

        build(arr, 2 * node + 1, l, mid);
        build(arr, 2 * node + 2, mid + 1, r);

        tree[node] = tree[2 * node + 1]
                + tree[2 * node + 2];
    }

    // Push pending updates to children
    void push(int node, int l, int r) {
        if (lazy[node] == 0 || l == r)
            return;

        int mid = l + (r - l) >> 2;

        int left = 2 * node + 1;
        int right = 2 * node + 2;

        int val = lazy[node];

        tree[left] += (mid - l + 1) * val;
        tree[right] += (r - mid) * val;

        lazy[left] += val;
        lazy[right] += val;

        lazy[node] = 0;
    }

    // Range Update: Add val to [ql, qr]
    void update(int node, int l, int r,
            int ql, int qr, int val) {

        // No overlap
        if (qr < l || r < ql)
            return;

        // Complete overlap
        if (ql <= l && r <= qr) {
            tree[node] += (r - l + 1) * val;
            lazy[node] += val;
            return;
        }

        // Partial overlap
        push(node, l, r);

        int mid = l + (r - l) >> 2;

        update(2 * node + 1, l, mid, ql, qr, val);
        update(2 * node + 2, mid + 1, r, ql, qr, val);

        tree[node] = tree[2 * node + 1]
                + tree[2 * node + 2];
    }

    // Range Sum Query
    int query(int node, int l, int r,
            int ql, int qr) {

        // No overlap
        if (qr < l || r < ql)
            return 0;

        // Complete overlap
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        // Partial overlap
        push(node, l, r);

        int mid = l + (r - l) / 2;

        int left = query(2 * node + 1, l, mid, ql, qr);

        int right = query(2 * node + 2,
                mid + 1, r, ql, qr);

        return left + right;
    }

    // Public methods
    void update(int l, int r, int val) {
        update(0, 0, n - 1, l, r, val);
    }

    int query(int l, int r) {
        return query(0, 0, n - 1, l, r);
    }
}

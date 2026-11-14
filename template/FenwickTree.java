class FenwickTree {
    int[] bit;
    int n;

    FenwickTree(int n) {
        this.n = n;
        bit = new int[n + 1];
    }

    void update(int i, int val) {
        while (i <= n) {
            bit[i] += val;
            i += i & -i;
        }
    }

    int query(int i) {
        int sum = 0;

        while (i > 0) {
            sum += bit[i];
            i -= i & -i;
        }

        return sum;
    }

    int rangeQuery(int l, int r) {
        return query(r) - query(l - 1);
    }
}
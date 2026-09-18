#include <bits/stdc++.h>
using namespace std;

struct Node {
    int open;
    int close;
    int len;

    Node(int open = 0, int close = 0, int len = 0) {
        this->open = open;
        this->close = close;
        this->len = len;
    }
};

class SegmentTree {
private:
    vector<Node> tree;
    int n;

    void build(int node, int start, int end, const string& s) {

        if (start == end) {

            if (s[start] == '(') {
                tree[node] = Node(1, 0, 0);
            } else {
                tree[node] = Node(0, 1, 0);
            }

            return;
        }

        int mid = start + (end - start) / 2;

        build(2 * node + 1, start, mid, s);
        build(2 * node + 2, mid + 1, end, s);

        tree[node] = merge(
            tree[2 * node + 1],
            tree[2 * node + 2]
        );
    }

    Node merge(const Node& left, const Node& right) {

        int matched = min(left.open, right.close);

        int len = left.len + right.len + 2 * matched;

        int open = left.open + right.open - matched;

        int close = left.close + right.close - matched;

        return Node(open, close, len);
    }

    Node queryHelper(
        int node,
        int start,
        int end,
        int l,
        int r
    ) {

        // Completely outside
        if (r < start || end < l) {
            return Node(0, 0, 0);
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
            r
        );

        Node right = queryHelper(
            2 * node + 2,
            mid + 1,
            end,
            l,
            r
        );

        return merge(left, right);
    }

public:

    SegmentTree(const string& s) {

        n = s.length();

        tree.resize(4 * n);

        build(0, 0, n - 1, s);
    }

    Node query(int l, int r) {
        return queryHelper(0, 0, n - 1, l, r);
    }
};

int main() {

    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string s;
    cin >> s;

    int q;
    cin >> q;

    SegmentTree st(s);

    while (q--) {

        // Codeforces gives 1-based indices
        // Convert to 0-based
        int l, r;
        cin >> l >> r;

        l--;
        r--;

        Node ans = st.query(l, r);

        cout << ans.len << '\n';
    }

    return 0;
}
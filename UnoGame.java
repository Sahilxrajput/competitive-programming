import java.io.*;
import java.util.*;

public class UnoGame {
    static int groupCount, limit, rivalCount, answer;
    static int[] weight, people;
    static int[][] rivals;
    static boolean[] blocked;

    static void evaluate() {
        int[] dp = new int[limit + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;

        for (int g = 0; g < groupCount; g++) {
            if (blocked[g])
                continue;

            int w = weight[g];
            int p = people[g];

            for (int s = limit; s >= w; s--) {
                if (dp[s - w] != -1) {
                    dp[s] = Math.max(
                            dp[s], dp[s - w] + p);
                }
            }
        }

        for (int x : dp) {
            answer = Math.max(answer, x);
        }
    }

    static void dfs(int idx) {
        if (idx == rivalCount) {
            evaluate();
            return;
        }

        int a = rivals[idx][0];
        int b = rivals[idx][1];

        if (blocked[a] || blocked[b]) {
            dfs(idx + 1);
            return;
        }

        // Exclude first group
        blocked[a] = true;
        dfs(idx + 1);
        blocked[a] = false;

        // Exclude second group
        blocked[b] = true;
        dfs(idx + 1);
        blocked[b] = false;
    }

    static void solve(FastScanner fs) throws Exception {
        int n = fs.nextInt();

        HashMap<String, Integer> id = new HashMap<>();

        for (int i = 0; i < n; i++) {
            id.put(fs.next(), i);
        }

        int[] skill = new int[n];

        for (int i = 0; i < n; i++) {
            skill[i] = fs.nextInt();
        }

        int[] groupOf = new int[n];
        Arrays.fill(groupOf, -1);

        weight = new int[n];
        people = new int[n];

        int friendCount = fs.nextInt();

        for (int i = 0; i < friendCount; i++) {
            int a = id.get(fs.next());
            int b = id.get(fs.next());

            groupOf[a] = groupCount;
            groupOf[b] = groupCount;

            weight[groupCount] = skill[a] + skill[b];
            people[groupCount] = 2;
            groupCount++;
        }

        // Players without friends become individual groups
        for (int i = 0; i < n; i++) {
            if (groupOf[i] == -1) {
                groupOf[i] = groupCount;
                weight[groupCount] = skill[i];
                people[groupCount] = 1;
                groupCount++;
            }
        }

        int n2 = fs.nextInt();

        rivals = new int[n2][2];
        blocked = new boolean[groupCount];

        for (int i = 0; i < n2; i++) {
            int a = groupOf[id.get(fs.next())];
            int b = groupOf[id.get(fs.next())];

            if (a == b) {
                // Friends who are also rivals cannot be selected
                blocked[a] = true;
            } else {
                rivals[rivalCount][0] = a;
                rivals[rivalCount][1] = b;
                rivalCount++;
            }
        }

        limit = fs.nextInt();

        dfs(0);

        System.out.println(answer);
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        solve(fs);
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0)
                    return -1;
            }
            return buffer[ptr++];
        }

        String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;

            do {
                c = read();
            } while (c <= 32 && c != -1);

            while (c > 32) {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}

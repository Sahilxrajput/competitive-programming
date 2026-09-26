import java.io.*;
import java.util.*;

public class DTwoDigitStrings {

    static void solve(FastScanner fs) throws Exception {

        String a = fs.next();
        String b = fs.next();

        int n = a.length();
        int m = b.length();

        int[] prea = new int[n + 1];
        int[] preb = new int[m + 1];

        for (int i = 0; i < n; i++) {
            prea[i + 1] = (prea[i] + (a.charAt(i) - '0')) % 10;
        }

        for (int i = 0; i < m; i++) {
            preb[i + 1] = (preb[i] + (b.charAt(i) - '0')) % 10;
        }

        int[][] dp = new int[n + 1][m + 1];

        for (int j = 1; j <= m; j++) {
            for (int i = 1; i <= n; i++) {

                dp[i][j] = Math.max(
                        dp[i - 1][j],
                        dp[i][j - 1]);

                if (prea[i] == preb[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }

        if (prea[n] == preb[m]) {
            System.out.println(dp[n][m]);
        } else {
            System.out.println(-1);
        }
    }

    public static void main(String[] args) throws Exception {

        FastScanner fs = new FastScanner();

        int t = fs.nextInt();

        while (t-- > 0) {
            solve(fs);
        }
    }

    // Fast Scanner
    static class FastScanner {

        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        private StringTokenizer st;

        String next() throws Exception {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }

            return st.nextToken();
        }

        int nextInt() throws Exception {
            return Integer.parseInt(next());
        }
    }
}
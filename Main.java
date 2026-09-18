import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        StringBuilder out = new StringBuilder();

        int tc = Integer.parseInt(fs.next());

        while (tc-- > 0) {
            int n = Integer.parseInt(fs.next());
            String s = fs.next();

            int[] cnt = new int[26];
            for (int i = 0; i < n; i++)
                cnt[s.charAt(i) - 'a']++;

            Integer[] idx = new Integer[26];
            for (int i = 0; i < 26; i++)
                idx[i] = i;


            Arrays.sort(
                    idx,
                    (a, b) -> (cnt[b] != cnt[a]) ? (cnt[b] - cnt[a]) : (b - a));

            int bestK = 1, changes = n;
            
            // try all possible number of distinct letters k that divide n
            for (int k = 1; k <= 26; k++) {
                if (n % k != 0)
                    continue; // each used letter must appear exactly n/k times
                int unchanged = 0;
                int limit = n / k;
                for (int i = 0; i < k; i++)
                    unchanged += Math.min(cnt[idx[i]], limit); // sum kept characters
                if (n - unchanged < changes) {
                    changes = n - unchanged;
                    bestK = k;
                }
            }

            int quota = n / bestK; // required count per chosen letter
            TreeMap<Character, Integer> mp = new TreeMap<>(); // lexicographically ordered letters with remaining quota
            for (int i = 0; i < bestK; i++)
                mp.put((char) ('a' + idx[i]), quota);

            char[] ans = new char[n];
            Arrays.fill(ans, ' ');

            // First pass: keep original characters if their letter still has quota
            for (int i = 0; i < n; i++) {
                char c = s.charAt(i);
                Integer q = mp.get(c); // only chosen letters are tracked
                if (q != null && q > 0) {
                    ans[i] = c;
                    mp.put(c, q - 1);
                }
            }

            // Second pass: fill remaining positions with the smallest available letter
            for (int i = 0; i < n; i++) {
                if (ans[i] != ' ')
                    continue;
                while (!mp.isEmpty() && mp.firstEntry().getValue() == 0)
                    mp.pollFirstEntry(); // drop exhausted letters
                char ch = mp.firstKey();
                ans[i] = ch;
                mp.put(ch, mp.get(ch) - 1);
            }

            out.append(changes).append('\n').append(new String(ans)).append('\n');
        }

        System.out.print(out.toString());
    }

    static final class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            this.in = is;
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

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= 32 && c != -1);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = 0;
            while (c > 32) {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }

        // String next() throws IOException {
        // int c;
        // do {
        // c = read();
        // } while (c <= 32 && c != -1);

        // StringBuilder sb = new StringBuilder();
        // while (c > 32) {
        // sb.append((char) c);
        // c = read();
        // }
        // return sb.toString();
        // }

        private String next() throws IOException {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = read();
                if (c == -1)
                    return null;
            } while (c <= 32);
            while (c > 32) {
                sb.append((char) c);
                c = read();
            }
            return sb.toString();
        }

    }
}


import java.io.*;
import java.util.*;

public class Main {
    static int m, n, len;
    static char[][] grid;
    static int[][][] dist;
    static boolean[][] clear;

    static final int[] DR = { -1, 1, 0, 0 };
    static final int[] DC = { 0, 0, -1, 1 };

    // Check whether the ladder can occupy this position
    static boolean canPlace(int r, int c, int orientation) {
        for (int k = 0; k < len; k++) {
            int nr = r + (orientation == 1 ? k : 0);
            int nc = c + (orientation == 0 ? k : 0);

            if (nr < 0 || nr >= m ||
                    nc < 0 || nc >= n ||
                    grid[nr][nc] == 'B') {
                return false;
            }
        }
        return true;
    }

    // Check if the entire len x len square is free
    static boolean isClearSquare(int r, int c) {
        return r >= 0 && c >= 0 &&
                r + len <= m && c + len <= n &&
                clear[r][c];
    }

    static void add(int r, int c, int orientation,
            int d, ArrayDeque<int[]> queue) {

        if (dist[r][c][orientation] == -1) {
            dist[r][c][orientation] = d;
            queue.offer(new int[] { r, c, orientation });
        }
    }

    static int bfs(int sr, int sc, int so,
            int tr, int tc, int to) {

        dist = new int[m][n][2];

        for (int[][] row : dist) {
            for (int[] cell : row) {
                Arrays.fill(cell, -1);
            }
        }

        ArrayDeque<int[]> queue = new ArrayDeque<>();
        add(sr, sc, so, 0, queue);

        while (!queue.isEmpty()) {
            int[] state = queue.poll();

            int r = state[0];
            int c = state[1];
            int o = state[2];
            int d = dist[r][c][o];

            if (r == tr && c == tc && o == to) {
                return d;
            }

            // Try moving in all four directions
            for (int dir = 0; dir < 4; dir++) {
                int nr = r + DR[dir];
                int nc = c + DC[dir];

                if (canPlace(nr, nc, o)) {
                    add(nr, nc, o, d + 1, queue);
                }
            }

            // Try rotating the ladder
            if (o == 0) {
                // Horizontal -> Vertical
                for (int top = r - len + 1; top <= r; top++) {
                    if (!isClearSquare(top, c)) {
                        continue;
                    }

                    for (int col = c; col < c + len; col++) {
                        add(top, col, 1, d + 1, queue);
                    }
                }
            } else {
                // Vertical -> Horizontal
                for (int left = c - len + 1; left <= c; left++) {
                    if (!isClearSquare(r, left)) {
                        continue;
                    }

                    for (int row = r; row < r + len; row++) {
                        add(row, left, 0, d + 1, queue);
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());

        grid = new char[m][n];

        int sr = -1, sc = -1;
        int tr = -1, tc = -1;
        int sourceLastRow = -1;
        int targetLastRow = -1;

        for (int i = 0; i < m; i++) {
            // Supports both spaced and unspaced input
            String line = br.readLine()
                    .trim()
                    .replaceAll("\\s+", "");

            for (int j = 0; j < n; j++) {
                char ch = line.charAt(j);
                grid[i][j] = ch;

                if (ch == 'l') {
                    if (sr == -1) {
                        sr = i;
                        sc = j;
                    }
                    sourceLastRow = i;
                    len++;
                } else if (ch == 'L') {
                    if (tr == -1) {
                        tr = i;
                        tc = j;
                    }
                    targetLastRow = i;
                }
            }
        }

        if (sr == -1 || tr == -1) {
            System.out.println("Impossible");
            return;
        }

        int so = sr == sourceLastRow ? 0 : 1;
        int to = tr == targetLastRow ? 0 : 1;

        // Precompute squares in which rotation is allowed
        clear = new boolean[m][n];

        for (int r = 0; r + len <= m; r++) {
            for (int c = 0; c + len <= n; c++) {
                boolean ok = true;

                for (int i = r; i < r + len && ok; i++) {
                    for (int j = c; j < c + len; j++) {
                        if (grid[i][j] == 'B') {
                            ok = false;
                            break;
                        }
                    }
                }

                clear[r][c] = ok;
            }
        }

        int ans = bfs(sr, sc, so, tr, tc, to);

        System.out.println(ans == -1 ? "Impossible" : ans);
    }
}

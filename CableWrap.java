import java.io.*;
import java.util.*;

public class CableWrap {
    static int n, m;
    static char[][] grid;
    static boolean[][] cable;

    // 0 = no crossing
    // 1 = horizontal rod
    // 2 = vertical rod
    static int[][] crossing;
    static boolean[] horizontalRod, verticalRod;

    static boolean isHorizontalRod(int r) {
        boolean hasR = false;

        for (int c = 0; c < m; c++) {
            char ch = grid[r][c];

            if (ch == '.')
                return false;
            if (ch == 'R')
                hasR = true;

            if (ch == 'C') {
                if (r == 0 || r == n - 1 ||
                        grid[r - 1][c] != 'C' ||
                        grid[r + 1][c] != 'C') {
                    return false;
                }
            }
        }
        return hasR;
    }

    static boolean isVerticalRod(int c) {
        boolean hasR = false;

        for (int r = 0; r < n; r++) {
            char ch = grid[r][c];

            if (ch == '.')
                return false;
            if (ch == 'R')
                hasR = true;

            if (ch == 'C') {
                if (c == 0 || c == m - 1 ||
                        grid[r][c - 1] != 'C' ||
                        grid[r][c + 1] != 'C') {
                    return false;
                }
            }
        }
        return hasR;
    }

    static boolean isCableOnHorizontalRod(int r, int c) {
        return grid[r][c] == 'C' ||
                (r > 0 && r + 1 < n &&
                        grid[r - 1][c] == 'C' &&
                        grid[r + 1][c] == 'C');
    }

    static boolean isCableOnVerticalRod(int r, int c) {
        return grid[r][c] == 'C' ||
                (c > 0 && c + 1 < m &&
                        grid[r][c - 1] == 'C' &&
                        grid[r][c + 1] == 'C');
    }

    static int solve() {
        horizontalRod = new boolean[n];
        verticalRod = new boolean[m];
        cable = new boolean[n][m];
        crossing = new int[n][m];

        // Identify rods and visible cable cells
        for (int r = 0; r < n; r++) {
            horizontalRod[r] = isHorizontalRod(r);

            for (int c = 0; c < m; c++) {
                if (grid[r][c] == 'C') {
                    cable[r][c] = true;
                }
            }
        }

        for (int c = 0; c < m; c++) {
            verticalRod[c] = isVerticalRod(c);
        }

        // Reconstruct intersections on horizontal rods
        for (int r = 0; r < n; r++) {
            if (!horizontalRod[r])
                continue;

            for (int c = 0; c < m; c++) {
                if (isCableOnHorizontalRod(r, c)) {
                    crossing[r][c] = 1;
                    cable[r][c] = true;
                }
            }
        }

        // Reconstruct intersections on vertical rods
        for (int c = 0; c < m; c++) {
            if (!verticalRod[c])
                continue;

            for (int r = 0; r < n; r++) {
                if (isCableOnVerticalRod(r, c)) {
                    crossing[r][c] = 2;
                    cable[r][c] = true;
                }
            }
        }

        int[] dr = { -1, 1, 0, 0 };
        int[] dc = { 0, 0, -1, 1 };

        // Find cable endpoint (degree = 1)
        int startR = -1, startC = -1;

        outer: for (int r = 0; r < n; r++) {
            for (int c = 0; c < m; c++) {
                if (!cable[r][c])
                    continue;

                int degree = 0;

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    if (nr >= 0 && nr < n &&
                            nc >= 0 && nc < m &&
                            cable[nr][nc]) {
                        degree++;
                    }
                }

                if (degree == 1) {
                    startR = r;
                    startC = c;
                    break outer;
                }
            }
        }

        if (startR == -1)
            return 0;

        int[] hSum = new int[n];
        int[] vSum = new int[m];
        boolean[][] seen = new boolean[n][m];

        int r = startR, c = startC;
        int prevR = -1, prevC = -1;

        // Trace the cable from one endpoint to the other
        while (true) {
            seen[r][c] = true;

            if (crossing[r][c] != 0 && prevR != -1) {
                int direction;

                if (crossing[r][c] == 1) {
                    // Vertical cable crossing a horizontal rod
                    direction = prevR < r ? 1 : -1;
                } else {
                    // Horizontal cable crossing a vertical rod
                    direction = prevC < c ? 1 : -1;
                }

                int layer = grid[r][c] == 'C' ? 1 : -1;
                int contribution = direction * layer;

                if (crossing[r][c] == 1) {
                    hSum[r] += contribution;
                } else {
                    vSum[c] += contribution;
                }
            }

            int nextR = -1, nextC = -1;

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < n &&
                        nc >= 0 && nc < m &&
                        cable[nr][nc] && !seen[nr][nc]) {

                    nextR = nr;
                    nextC = nc;
                    break;
                }
            }

            if (nextR == -1)
                break;

            prevR = r;
            prevC = c;
            r = nextR;
            c = nextC;
        }

        // Calculate minimum switches
        int answer = 0;

        for (int i = 0; i < n; i++) {
            if (horizontalRod[i]) {
                answer += Math.abs(hSum[i]) / 2;
            }
        }

        for (int j = 0; j < m; j++) {
            if (verticalRod[j]) {
                answer += Math.abs(vSum[j]) / 2;
            }
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        grid = new char[n][m];

        for (int r = 0; r < n; r++) {
            String line = br.readLine().replaceAll("\\s+", "");

            for (int c = 0; c < m; c++) {
                grid[r][c] = line.charAt(c);
            }
        }

        System.out.println(solve());
    }
}

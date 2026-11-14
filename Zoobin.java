import java.io.*;
import java.util.*;

public class Zoobin {
    static int vertices, edges;

    // Encode a graph as sorted undirected edges
    static String encode(int[][] graph) {
        char[] encoded = new char[graph.length];

        for (int i = 0; i < graph.length; i++) {
            int a = Math.min(graph[i][0], graph[i][1]);
            int b = Math.max(graph[i][0], graph[i][1]);

            encoded[i] = (char) (a * vertices + b);
        }

        Arrays.sort(encoded);
        return new String(encoded);
    }

    // DFS to enumerate simple cycles
    static void dfs(int start, int at,
            ArrayList<Integer>[] adj,
            boolean[] visited,
            int[] path, int length,
            ArrayList<int[]> cycles) {

        for (int next : adj[at]) {
            if (next == start) {
                if (length >= 3 &&
                        path[1] < path[length - 1]) {

                    cycles.add(Arrays.copyOf(path, length));
                }
            } else if (next > start && !visited[next]) {
                visited[next] = true;
                path[length] = next;

                dfs(start, next, adj, visited,
                        path, length + 1, cycles);

                visited[next] = false;
            }
        }
    }

    static ArrayList<int[]> getCycles(String state) {
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = new ArrayList[vertices];

        for (int i = 0; i < vertices; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < state.length(); i++) {
            int encoded = state.charAt(i);

            int u = encoded / vertices;
            int v = encoded % vertices;

            adj[u].add(v);
            adj[v].add(u);
        }

        boolean[] visited = new boolean[vertices];
        int[] path = new int[vertices];
        ArrayList<int[]> cycles = new ArrayList<>();

        for (int start = 0; start < vertices; start++) {
            if (adj[start].size() < 2)
                continue;

            visited[start] = true;
            path[0] = start;

            dfs(start, start, adj, visited,
                    path, 1, cycles);

            visited[start] = false;
        }

        return cycles;
    }

    // Rotate all vertices of a cycle by one position
    static String rotate(String state,
            int[] cycle, int direction) {

        int[] map = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            map[i] = i;
        }

        int length = cycle.length;

        for (int i = 0; i < length; i++) {
            map[cycle[i]] = cycle[(i + direction + length) % length];
        }

        char[] changed = new char[state.length()];

        for (int i = 0; i < state.length(); i++) {
            int edge = state.charAt(i);

            int u = map[edge / vertices];
            int v = map[edge % vertices];

            if (u > v) {
                int temp = u;
                u = v;
                v = temp;
            }

            changed[i] = (char) (u * vertices + v);
        }

        Arrays.sort(changed);
        return new String(changed);
    }

    // Expand one complete BFS level
    static int expand(ArrayDeque<String> queue,
            HashMap<String, Integer> own,
            HashMap<String, Integer> other) {

        int levelSize = queue.size();
        int best = Integer.MAX_VALUE;

        while (levelSize-- > 0) {
            String current = queue.poll();
            int currentDist = own.get(current);

            for (int[] cycle : getCycles(current)) {
                for (int direction : new int[] { 1, -1 }) {

                    String next = rotate(current, cycle, direction);

                    if (own.containsKey(next))
                        continue;

                    Integer otherDist = other.get(next);

                    if (otherDist != null) {
                        best = Math.min(
                                best,
                                currentDist + 1 + otherDist);
                    } else {
                        own.put(next, currentDist + 1);
                        queue.add(next);
                    }
                }
            }
        }

        return best == Integer.MAX_VALUE ? -1 : best;
    }

    static int solve(String source, String target) {
        if (source.equals(target))
            return 0;

        ArrayDeque<String> leftQ = new ArrayDeque<>();
        ArrayDeque<String> rightQ = new ArrayDeque<>();

        HashMap<String, Integer> leftD = new HashMap<>();
        HashMap<String, Integer> rightD = new HashMap<>();

        leftQ.add(source);
        rightQ.add(target);

        leftD.put(source, 0);
        rightD.put(target, 0);

        while (!leftQ.isEmpty() && !rightQ.isEmpty()) {
            int answer;

            if (leftQ.size() <= rightQ.size()) {
                answer = expand(leftQ, leftD, rightD);
            } else {
                answer = expand(rightQ, rightD, leftD);
            }

            if (answer != -1)
                return answer;
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        edges = fs.nextInt();

        HashMap<Integer, Integer> ids = new HashMap<>();

        int[][] start = new int[edges][2];
        int[][] goal = new int[edges][2];

        HashSet<Integer> startVertices = new HashSet<>();
        HashSet<Integer> goalVertices = new HashSet<>();

        for (int i = 0; i < 2 * edges; i++) {
            int rawA = fs.nextInt();
            int rawB = fs.nextInt();

            ids.putIfAbsent(rawA, ids.size());
            ids.putIfAbsent(rawB, ids.size());

            int a = ids.get(rawA);
            int b = ids.get(rawB);

            int[][] graph = i < edges ? start : goal;
            int index = i < edges ? i : i - edges;

            graph[index][0] = a;
            graph[index][1] = b;

            if (i < edges) {
                startVertices.add(a);
                startVertices.add(b);
            } else {
                goalVertices.add(a);
                goalVertices.add(b);
            }
        }

        vertices = ids.size();

        if (!startVertices.equals(goalVertices)) {
            System.out.println(-1);
            return;
        }

        System.out.println(
                solve(encode(start), encode(goal)));
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

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;

            if (c == '-') {
                sign = -1;
                c = read();
            }

            int num = 0;

            while (c > ' ') {
                num = num * 10 + c - '0';
                c = read();
            }

            return num * sign;
        }
    }
}

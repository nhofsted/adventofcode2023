public class LinearSystem {
    public static double[] solve(double[][] m) {
        int n = m.length;

        for (int p = 0; p < n; p++) {
            // find largest pivot
            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(m[i][p]) > Math.abs(m[max][p])) {
                    max = i;
                }
            }

            // swap
            double[] temp = m[p];
            m[p] = m[max];
            m[max] = temp;

            if (Math.abs(m[p][p]) == 0) return null;

            // make triangular matrix
            for (int i = p + 1; i < n; i++) {
                double alpha = m[i][p] / m[p][p];
                for (int j = p; j <= n; j++) {
                    m[i][j] -= alpha * m[p][j];
                }
            }
        }

        // substitution
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += m[i][j] * x[j];
            }
            x[i] = (m[i][m.length] - sum) / m[i][i];
        }

        return x;
    }
}
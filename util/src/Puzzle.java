import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class Puzzle {

    protected abstract int getSampleSolution();

    protected abstract int getSolution(BufferedReader in) throws Exception;

    protected void run() throws Exception {
        // First test whether we implemented the sample correctly
        InputStream in = getInputStream("data/sample" + getClass().getName() + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int correctSampleSolution = getSampleSolution();
        int sampleSolution = getSolution(br);
        if (sampleSolution == correctSampleSolution) {
            System.out.println("Sample solution matches");
        } else {
            System.out.println("Your solution " + sampleSolution + " doesn't match the actual solution " + correctSampleSolution);
            return;
        }

        // Now give the solution
        in = getInputStream("data/input.txt");
        br = new BufferedReader(new InputStreamReader(in));
        int solution = getSolution(br);
        System.out.println("The solution is: " + solution);
    }

    private InputStream getInputStream(String path) throws IOException {
        InputStream in;
        Path readPath = Path.of(path);
        try {
            in = Files.newInputStream(readPath);
        } catch (IOException e) {
            System.out.println("error opening file " + readPath.toAbsolutePath());
            throw e;
        }
        return in;
    }
}
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

public abstract class Puzzle {

    protected abstract long getSampleSolution();

    protected abstract long getSolution(BufferedReader in) throws Exception;

    protected void run() throws Exception {
        // First test whether we implemented the sample correctly
        Path path = Path.of("data/sample.txt");
        if(!Files.exists(path)) path = Path.of("data/sample" + getClass().getName() + ".txt");
        InputStream in = getInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        long correctSampleSolution = getSampleSolution();
        long sampleSolution = getSolution(br);
        if (sampleSolution == correctSampleSolution) {
            System.out.println("Sample solution matches");
        } else {
            System.out.println("Your solution " + sampleSolution + " doesn't match the actual solution " + correctSampleSolution);
            return;
        }

        // Now give the solution
        in = getInputStream(Path.of("data/input.txt"));
        br = new BufferedReader(new InputStreamReader(in));
        Instant start = Instant.now();
        long solution = getSolution(br);
        Instant end = Instant.now();
        System.out.println("The solution is: " + solution);
        System.out.println("Compute time: " + Duration.between(start, end));
    }

    private InputStream getInputStream(Path path) throws IOException {
        InputStream in;
        try {
            in = Files.newInputStream(path);
        } catch (IOException e) {
            System.out.println("error opening file " + path.toAbsolutePath());
            throw e;
        }
        return in;
    }
}
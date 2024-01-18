import java.io.BufferedReader;

public class Day24B extends Puzzle {

    public static void main(String[] args) throws Exception {
        new Day24B().run();
    }

    @Override
    protected long getSampleSolution() {
        return 47;
    }

    @Override
    protected long getSolution(BufferedReader in) {
        HailStone[] h = in.lines()
                .limit(4)
                .map(HailStone::parse)
                .toArray(HailStone[]::new);

        HailStone hA = h[0];
        HailStone hB = h[1];
        HailStone hC = h[2];
        HailStone hD = h[3];

        double[] solution = LinearSystem.solve(new double[][]{
                {hA.vy() - hB.vy(), hB.vx() - hA.vx(), 0, hB.y() - hA.y(), hA.x() - hB.x(), 0, hA.x() * hA.vy() - hA.y() * hA.vx() - hB.x() * hB.vy() + hB.y() * hB.vx()},
                {hA.vz() - hB.vz(), 0, hB.vx() - hA.vx(), hB.z() - hA.z(), 0, hA.x() - hB.x(), hA.x() * hA.vz() - hA.z() * hA.vx() - hB.x() * hB.vz() + hB.z() * hB.vx()},
                {hA.vy() - hC.vy(), hC.vx() - hA.vx(), 0, hC.y() - hA.y(), hA.x() - hC.x(), 0, hA.x() * hA.vy() - hA.y() * hA.vx() - hC.x() * hC.vy() + hC.y() * hC.vx()},
                {hA.vz() - hC.vz(), 0, hC.vx() - hA.vx(), hC.z() - hA.z(), 0, hA.x() - hC.x(), hA.x() * hA.vz() - hA.z() * hA.vx() - hC.x() * hC.vz() + hC.z() * hC.vx()},
                {hA.vy() - hD.vy(), hD.vx() - hA.vx(), 0, hD.y() - hA.y(), hA.x() - hD.x(), 0, hA.x() * hA.vy() - hA.y() * hA.vx() - hD.x() * hD.vy() + hD.y() * hD.vx()},
                {hA.vz() - hD.vz(), 0, hD.vx() - hA.vx(), hD.z() - hA.z(), 0, hA.x() - hD.x(), hA.x() * hA.vz() - hA.z() * hA.vx() - hD.x() * hD.vz() + hD.z() * hD.vx()},
        });

        assert solution != null;
        return (long) (solution[0] + solution[1] + solution[2]);
    }
}
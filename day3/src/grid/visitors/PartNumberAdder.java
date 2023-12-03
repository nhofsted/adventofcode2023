package grid.visitors;

import grid.Grid;

public class PartNumberAdder extends AbstractGridVisitor {
    private int sum;
    private int workingNumber;
    private int part;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int visit(int[] data) {
        // Implement this without branches for speed and fun
        int d = data[0];

        int notNumberLow = ((d & Grid.NOT_NUMBER) >> Grid.NOT_NUMBER_POS) * Integer.MAX_VALUE;
        int numberLow = (~notNumberLow) & Integer.MAX_VALUE;
        int partLow = ((d & Grid.PART) >> Grid.PART_POS) * Integer.MAX_VALUE;

        // if ((d & grid.Grid.NOT_NUMBER) != 0) {
        //     if (part != 0) {
        //         sum += workingNumber;
        //     }
        //     part = 0;
        //     workingNumber = 0;
        // }
        sum += workingNumber & part & notNumberLow;
        part = part & numberLow;
        workingNumber = workingNumber & numberLow;

        // if ((d & grid.Grid.NOT_NUMBER) == 0) {
        //     if ((d & grid.Grid.PART) != 0) {
        //         part = Integer.MAX_VALUE;
        //     }
        //     workingNumber *= 10;
        //     workingNumber += d & grid.Grid.NUMBER_MASK;
        // }
        part = part | (partLow & numberLow);
        workingNumber *= 0x0A ^ (0x0B & notNumberLow);
        workingNumber += d & Grid.NUMBER_MASK & numberLow;

        return d;
    }

    public void end() {
        // if (part != 0) {
        //     sum += workingNumber;
        // }
        sum += workingNumber & part;

        part = 0;
        workingNumber = 0;
    }

    public int getSum() {
        return sum;
    }
}
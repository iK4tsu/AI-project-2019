package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

public class Pair {
    private Cell cell1;
    private Cell cell2;
    private int value;

    public Pair(Cell cell1, Cell cell2) {
        this.cell1 = cell1;
        this.cell2 = cell2;
    }

    public Cell getCell1() {
        return cell1;
    }

    public Cell getCell2() {
        return cell2;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean equals(Cell c1, Cell c2)
    {
        return (c1 == cell1 && c2 == cell2) ||
                (c1 == cell2 && c2 == cell1);
    }

    @Override
    public String toString() {
        return cell1.getLine() + "-" + cell1.getColumn() + " / " + cell2.getLine() + "-" + cell2.getColumn() + ": " + value + "\n";
    }
}

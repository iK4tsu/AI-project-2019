package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Action;
import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class WarehouseState extends State implements Cloneable {

    //TODO: this class might require the definition of additional methods and/or attributes

    private int[][] matrix;
    private int lineAgent, columnAgent;
    private int lineExit;
    private int columnExit;
    private int steps;

    public WarehouseState(int[][] matrix) {
        this.matrix = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                this.matrix[i][j] = matrix[i][j];
                switch (matrix[i][j])
                {
                    // Door && Agent
                    case Properties.AGENT:
                        lineAgent = lineExit = i;
                        columnAgent = columnExit = j;
                        break;
                    // Shelf
                    case Properties.SHELF:
                        break;
                    // Free space: '0'
                    default:
                        break;
                }
            }
        }
    }

    public void executeAction(Action action) {
        action.execute(this);
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        fireUpdatedEnvironment();
    }


    public double computeShelfDistances()
    {
        return steps;
    }


    // can move up if the cell above isn't a shelf or a wall (the lat row)
    public boolean canMoveUp() {
        if (lineAgent > 0)
            return matrix[lineAgent - 1][columnAgent] != Properties.SHELF;
        else
            return false;
    }

    // can move down if the cell to right isn't a shelf or a wall (the last column)
    public boolean canMoveRight() {
        if (columnAgent + 1 < matrix.length)
            return matrix[lineAgent][columnAgent + 1] != Properties.SHELF;
        else
            return false;
    }

    /// ditto
    public boolean canMoveDown() {
        if (lineAgent + 1 < matrix.length)
            return matrix[lineAgent + 1][columnAgent] != Properties.SHELF;
        else
            return false;
    }

    /// ditto
    public boolean canMoveLeft() {
        if (columnAgent > 0)
            return matrix[lineAgent][columnAgent - 1] != Properties.SHELF;
        else
            return false;
    }

    public void moveUp() {
        matrix[lineAgent--][columnAgent] = 0;
        matrix[lineAgent][columnAgent] = 1;
        steps++;
    }

    public void moveRight() {
        matrix[lineAgent][columnAgent++] = 0;
        matrix[lineAgent][columnAgent] = 1;
        steps++;
    }

    public void moveDown() {
        matrix[lineAgent++][columnAgent] = 0;
        matrix[lineAgent][columnAgent] = 1;
        steps++;
    }

    public void moveLeft() {
        matrix[lineAgent][columnAgent--] = 0;
        matrix[lineAgent][columnAgent] = 1;
        steps++;
    }

    public void setCellAgent(int line, int column) {
        matrix[lineAgent][columnAgent] = 0;
        lineAgent = line;
        columnAgent = column;
        matrix[lineAgent][columnAgent] = 1;
    }

    public void setCellExit(int line, int column) {
        lineExit = line;
        columnExit = column;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getSize() {
        return matrix.length;
    }

    public Color getCellColor(int line, int column) {
        if (line == lineExit && column == columnExit && (line != lineAgent || column != columnAgent))
            return Properties.COLOREXIT;

        switch (matrix[line][column]) {
            case Properties.AGENT:
                return Properties.COLORAGENT;
            case Properties.SHELF:
                return Properties.COLORSHELF;
            default:
                return Properties.COLOREMPTY;
        }
    }

    public int getLineAgent() {
        return lineAgent;
    }

    public int getColumnAgent() {
        return columnAgent;
    }

    public boolean equalsAgent(Cell cell)
    {
        if (cell.getColumn() == columnExit && cell.getLine() == lineExit)
            return columnExit == columnAgent && lineExit == lineAgent;
        else
            return cell.getColumn() + 1 == columnAgent &&
                   cell.getLine() == lineAgent;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof WarehouseState)) {
            return false;
        }

        WarehouseState o = (WarehouseState) other;
        if (matrix.length != o.matrix.length) {
            return false;
        }

        return Arrays.deepEquals(matrix, o.matrix);
    }

    @Override
    public int hashCode() {
        return 97 * 7 + Arrays.deepHashCode(this.matrix);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            buffer.append('\n');
            for (int j = 0; j < matrix.length; j++) {
                buffer.append(matrix[i][j]);
                buffer.append(' ');
            }
        }
        return buffer.toString();
    }

    @Override
    public WarehouseState clone() {
        WarehouseState ret = new WarehouseState(matrix);
        ret.setCellExit(lineExit, columnExit);
        return ret;
    }

    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }
}

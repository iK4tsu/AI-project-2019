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
        this.matrix = matrix;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
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
        // TODO
        throw new UnsupportedOperationException("Not implemented yet."); // delete after implementing
    }

    public void executeActionSimulation(Action action) {
        action.execute(this);
        // TODO

        fireUpdatedEnvironment();
        throw new UnsupportedOperationException("Not implemented yet."); // delete after implementing
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
        lineAgent--;
        steps++;
    }

    public void moveRight() {
        columnAgent++;
        steps++;
    }

    public void moveDown() {
        lineAgent++;
        steps++;
    }

    public void moveLeft() {
        columnAgent--;
        steps++;
    }

    public void setCellAgent(int line, int column) {
        lineAgent = line;
        columnAgent = column;
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

        return Arrays.deepEquals(matrix, o.matrix) && (((WarehouseState) other).columnAgent == columnAgent && ((WarehouseState) other).lineAgent == lineAgent);
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
        ret.setCellAgent(lineAgent, columnAgent);
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

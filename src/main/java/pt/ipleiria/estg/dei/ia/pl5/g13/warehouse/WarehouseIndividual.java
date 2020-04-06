package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.IntStream;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;

public class WarehouseIndividual extends IntVectorIndividual<WarehouseProblemForGA, WarehouseIndividual> {

    public WarehouseIndividual(WarehouseProblemForGA problem, int size) {
        super(problem, size);

        //range for genome initialization
        Integer[] productsArray = IntStream.rangeClosed(1, size).boxed().toArray(Integer[]::new);

        // shuffle products
        Collections.shuffle(Arrays.asList(productsArray), GeneticAlgorithm.random);

        // set the genome with suffled values
        for (int i = 0; i < genome.length; i++)
            setGene(i, productsArray[i]);
    }

    public WarehouseIndividual(WarehouseIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {
        fitness = 0;
        ArrayList<Request> requests = problem.agentSearch.getRequests();

        for (int i = 0; i < requests.size(); i++) {
            Cell agent = problem.agentSearch.getCellAgent();
            Request request = requests.get(i);
            for (int product : request.getRequest()) {
                int idxShelf = getShelfPos(this.genome, product);
                Cell destination = problem.agentSearch.getShelves().get(idxShelf);
                fitness += getPairValue(agent, destination);
                agent = destination;
            }
            fitness += getPairValue(agent, problem.agentSearch.getCellAgent());
        }

        return fitness;
    }

    private int getPairValue(Cell c1, Cell c2)
    {
        for(Pair pair : (LinkedList<Pair>) problem.agentSearch.getPairs())
        {
            if (pair.equals(c1, c2))
                return pair.getValue();
        }

        throw new IllegalArgumentException("Pair not found");
    }

    public static int getShelfPos(int[] genome, int value) {
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value)
                return i;
        }

        throw new IllegalArgumentException("Invalid product " + String.valueOf(value));
    }

    /**
     * This methods gets a product in a speficied shelf (coordenates)
     * @param line line position in the matrix
     * @param column column position in the matrix
     * @return the product id if the shelf as a prodyct and 0 otherwise
     */
    public int getProductInShelf(int line, int column){
        LinkedList<Cell> shelves = problem.agentSearch.getShelves();
        for (int i = 0; i < shelves.size(); i++) {
            Cell c = shelves.get(i);

            if(c.getColumn() == column && c.getLine() == line)
            {
                if(genome[i] <= problem.agentSearch.getNumProducts())
                    return genome[i];
                else
                    return 0;
            }
        }

        throw new IllegalArgumentException("Invalid shelf cell: " + new Cell(line, column).toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath:");
        for (int i = 0; i < genome.length; i++) {
            int product = (genome[i] <= problem.agentSearch.getNumProducts())
                ? genome[i]
                    : 0;
            sb.append(" ").append(product);
        }

        return sb.toString();
    }

    /**
     * Compares if an individual is better then another by its fitness.
     * @param i individual to compare
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(WarehouseIndividual i) {
        return (this.fitness == i.getFitness()) ? 0 : (this.fitness < i.getFitness()) ? 1 : -1;
    }

    @Override
    public WarehouseIndividual clone() {
        return new WarehouseIndividual(this);

    }
}

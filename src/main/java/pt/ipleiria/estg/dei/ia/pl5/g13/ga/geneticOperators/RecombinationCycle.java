package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class RecombinationCycle<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationCycle(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {
        int size = ind1.getNumGenes();

        Set<Integer> visitedIndices = new HashSet<Integer>(size);
        List<Integer> indices = new ArrayList<Integer>(size);

        int idx = GeneticAlgorithm.random.nextInt(size);
        int cycle = 1;

        while (visitedIndices.size() < size) {
            indices.add(idx);

            int item = ind2.getGene(idx);
            idx = ind1.getIndexof(item);

            while (idx != indices.get(0)) {
                indices.add(idx);

                item = ind2.getGene(idx);
                idx = ind1.getIndexof(item);
            }

            if (cycle++ % 2 != 0)
                for (int i : indices)
                    ind1.swapGenes(ind2, i);

            visitedIndices.addAll(indices);

            idx = (indices.get(0) + 1) % size;
            while (visitedIndices.contains(idx) && visitedIndices.size() < size) {
                idx++;
                if (idx >= size) {
                    idx = 0;
                }
            }
            indices.clear();
        }
    }

    @Override
    public String toString()
    {
        return "CX";
    }
}

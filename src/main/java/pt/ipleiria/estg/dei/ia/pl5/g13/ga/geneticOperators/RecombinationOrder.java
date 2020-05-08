package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class RecombinationOrder<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationOrder(double probability) {
        super(probability);
    }

    @Override
    public void recombine(I ind1, I ind2) {

        int p1 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes() - 1);
        int p2 = GeneticAlgorithm.random.nextInt(ind1.getNumGenes());

        int start = Math.min(p1, p2);
        int end = Math.max(p1, p2);

        int size = ind1.getNumGenes();

        List<Integer> child1 = new Vector<Integer>();
        List<Integer> child2 = new Vector<Integer>();

        List<Integer> tour1 = Arrays.stream(ind1.getGenome())
            .boxed().collect(Collectors.toList());
        List<Integer> tour2 = Arrays.stream(ind2.getGenome())
            .boxed().collect(Collectors.toList());

        child1.addAll(tour1.subList(start, end));
        child2.addAll(tour2.subList(start, end));

        int idx = 0;
        int curP1 = 0;
        int curP2 = 0;

        for (int i = 0; i < size; i++) {

            idx = (end + i) % size;

            curP1 = ind1.getGene(idx);
            curP2 = ind2.getGene(idx);

            if (!child1.contains(curP2))
                child1.add(curP2);
            if (!child2.contains(curP1))
                child2.add(curP1);
        }

        Collections.rotate(child1, start);
        Collections.rotate(child2, start);

        for(int i = 0; i < child1.size(); i++)
            ind1.setGene(i, child1.get(i));

        for(int i = 0; i < child2.size(); i++)
            ind2.setGene(i, child2.get(i));
    }

    @Override
    public String toString(){
        return "OX1";
    }
}

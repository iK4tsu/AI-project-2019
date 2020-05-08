package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class MutationInversion<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationInversion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int first = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int second;

        do {
            second = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        } while (first == second);

        if (first > second) {
            int aux = first;
            first = second;
            second = aux;
        }

        for(int i = second - 1; i > first; i--) {
            int aux = ind.getGene(i);
            ind.setGene(i, ind.getGene(first));
            ind.setGene(first, aux);

            first++;
        }
    }

    @Override
    public String toString() {
        return "Inversion";
    }
}

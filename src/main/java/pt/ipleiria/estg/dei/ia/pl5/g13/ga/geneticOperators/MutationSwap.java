package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class MutationSwap<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public MutationSwap(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        int swap1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int swap2;

        do {
            swap2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        } while (swap1 == swap2);

        int aux = ind.getGene(swap2);
        ind.setGene(swap2, ind.getGene(swap1));
        ind.setGene(swap1, aux);
    }

    @Override
    public String toString() {
        return "Swap";
    }
}

package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Individual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Population;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

import static pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm.random;

public abstract class Mutation <I extends Individual, P extends Problem<I>> extends GeneticOperator{

    public Mutation(double probability){
        super(probability);
    }

    public void run(Population<I, P> population) {
        int populationSize = population.getSize();
        for (int i = 0; i < populationSize; i++) {
            if (random.nextDouble() < getProbability()) {
                mutate(population.getIndividual(i));
            }
        }
    }

    public abstract void mutate(I individual);
}

package pt.ipleiria.estg.dei.ia.pl5.g13.ga.selectionMethods;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Individual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Population;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public abstract class SelectionMethod <I extends Individual, P extends Problem<I>>{

    protected int popSize;
    
    public SelectionMethod(int popSize){
        this.popSize = popSize;
    }

    public abstract Population<I, P> run(Population<I, P> original);
}
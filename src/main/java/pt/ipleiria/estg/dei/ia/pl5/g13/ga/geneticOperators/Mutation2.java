package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class Mutation2<I extends IntVectorIndividual, P extends Problem<I>> extends Mutation<I, P> {

    public Mutation2(double probability) {
        super(probability);
    }

    @Override
    public void mutate(I ind) {
        //TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public String toString() {
        //TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}

package pt.ipleiria.estg.dei.ia.pl5.g13.ga;

public interface Problem<E extends Individual> {

    /*
     * This method returns a new Individual. It acts as a kind of factory of
     * Individuals. This is a problem specific operation.
     */
    E getNewIndividual();

}

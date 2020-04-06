package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {
    //TODO: this class might require the definition of additional methods and/or attributes
    WarehouseAgentSearch agentSearch;

    public WarehouseProblemForGA(WarehouseAgentSearch agentSearch) {
        this.agentSearch = agentSearch;
    }

    @Override
    public WarehouseIndividual getNewIndividual() {
        return new WarehouseIndividual(this, agentSearch.getShelves().size());
    }

    @Override
    public String toString() {
        //TODO:
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}

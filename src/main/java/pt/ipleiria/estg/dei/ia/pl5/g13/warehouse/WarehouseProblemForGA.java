package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class WarehouseProblemForGA implements Problem<WarehouseIndividual> {

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
        return "# of products: " + agentSearch.getNumProducts();
    }

}

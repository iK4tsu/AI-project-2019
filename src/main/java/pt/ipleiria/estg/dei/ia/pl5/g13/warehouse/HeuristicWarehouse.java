package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Heuristic;

public class HeuristicWarehouse extends Heuristic<WarehouseProblemForSearch, WarehouseState> {
    @Override
    public double compute(WarehouseState state){
        return state.computeShelfDistances();
    }

    @Override
    public String toString(){
        return "Heuristic Warehouse";
    }
}

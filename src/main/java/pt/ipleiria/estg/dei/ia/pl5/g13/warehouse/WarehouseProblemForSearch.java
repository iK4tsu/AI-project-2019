package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Action;
import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Problem;

import java.util.LinkedList;
import java.util.List;

public class WarehouseProblemForSearch<S extends WarehouseState> extends Problem<S> {

    private LinkedList<Action> actions;
    private final Cell goalPosition;

    public WarehouseProblemForSearch(S initialWarehouseState, Cell goalPosition) {
        super(initialWarehouseState);
        actions = new LinkedList<>();
        actions.add(new ActionDown());
        actions.add(new ActionLeft());
        actions.add(new ActionRight());
        actions.add(new ActionUp());
        this.goalPosition = goalPosition;
    }

    @Override
    public List<S> executeActions(S state) {
        LinkedList<S> ret = new LinkedList<>();

        for (Action action: actions)
        {
            if (action.isValid(state))
            {
                S successor = (S) state.clone();
                action.execute(successor);
                ret.add(successor);
            }
        }

        return ret;
    }

    // determine if all the requests are completed
    @Override
    public boolean isGoal(S state) {
        return state.equalsAgent(goalPosition);
    }
}

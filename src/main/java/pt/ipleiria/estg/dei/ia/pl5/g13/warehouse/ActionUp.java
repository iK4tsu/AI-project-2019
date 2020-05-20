package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Action;

public class ActionUp extends Action<WarehouseState> {

    public ActionUp(){
        super(1);
    }

    @Override
    public void execute(WarehouseState state){
        state.moveUp();
        state.setAction(this);
    }

    @Override
    public boolean isValid(WarehouseState state){
        return state.canMoveUp();
    }
}
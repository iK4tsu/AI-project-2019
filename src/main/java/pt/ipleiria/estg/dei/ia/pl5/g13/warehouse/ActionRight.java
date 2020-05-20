package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Action;

public class ActionRight extends Action<WarehouseState> {

    public ActionRight(){
        super(1);
    }

    @Override
    public void execute(WarehouseState state){
        state.moveRight();
        state.setAction(this);
    }

    @Override
    public boolean isValid(WarehouseState state){
        return state.canMoveRight();
    }
}
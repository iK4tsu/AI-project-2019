package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Action;

public class ActionLeft extends Action<WarehouseState>{

    public ActionLeft(){
        super(1);
    }

    @Override
    public void execute(WarehouseState state){
        state.moveLeft();
        state.setAction(this);
    }

    @Override
    public boolean isValid(WarehouseState state){
        return state.canMoveLeft();
    }
}

package pt.ipleiria.estg.dei.ia.pl5.g13.warehouse;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Action;

public class ActionDown extends Action<WarehouseState>{

    public ActionDown(){
        super(1);
    }

    @Override
    public void execute(WarehouseState state){
        state.moveDown();
        state.setAction(this);
    }

    @Override
    public boolean isValid(WarehouseState state){
        return state.canMoveDown();
    }
}
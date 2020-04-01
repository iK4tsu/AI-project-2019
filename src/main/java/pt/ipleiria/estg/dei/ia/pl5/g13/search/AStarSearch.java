package pt.ipleiria.estg.dei.ia.pl5.g13.search;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.State;

import java.util.List;

public class AStarSearch extends InformedSearch {

    //f = g + h
    @Override
    public void addSuccessorsToFrontier(List<State> successors, Node parent) {
        for (State s : successors) {
            double g = parent.getG() + s.getAction().getCost();
            if (!(frontier.containsState(s) || explored.contains(s))) {
                frontier.add(new Node(s, parent, g, g + heuristic.compute(s)));
            }
        }
    }

    @Override
    public String toString() {
        return "A* pt.ipleiria.estg.dei.ia.pl5.g13.search";
    }
}

package pt.ipleiria.estg.dei.ia.pl5.g13.search;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Heuristic;
import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Problem;
import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Solution;
import pt.ipleiria.estg.dei.ia.pl5.g13.utils.NodePriorityQueue;


public abstract class InformedSearch extends GraphSearch<NodePriorityQueue>{

    protected Heuristic heuristic;
    
    public InformedSearch(){
        frontier = new NodePriorityQueue();
    }
    
    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        stopped = false;
        this.heuristic = problem.getHeuristic();
        return graphSearch(problem);
    }    
}
package pt.ipleiria.estg.dei.ia.pl5.g13.utils;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.State;
import pt.ipleiria.estg.dei.ia.pl5.g13.search.Node;

import java.util.Queue;

public interface NodeCollection extends Queue<Node> {
    public boolean containsState(State e);
}

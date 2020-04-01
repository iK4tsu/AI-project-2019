package pt.ipleiria.estg.dei.ia.pl5.g13.search;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Problem;
import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Solution;

public interface SearchMethod {

   Solution search(Problem problem);
   
   Statistics getStatistics();
   
   void stop();
   
   boolean hasBeenStopped();
}
package pt.ipleiria.estg.dei.ia.pl5.g13.ga;

import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.ExperimentListener;

public interface GAListener extends ExperimentListener{

    void generationEnded(GAEvent e);
    void runEnded(GAEvent e);
}

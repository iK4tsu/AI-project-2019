package pt.ipleiria.estg.dei.ia.pl5.g13.experiments;

public class ExperimentEvent {
    
    Experiment source;

    public ExperimentEvent(Experiment source) {
        this.source = source;
    }
    
    public Experiment getSource(){
        return source;
    }
}

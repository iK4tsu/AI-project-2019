package pt.ipleiria.estg.dei.ia.pl5.g13.statisticsGA;

import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.Experiment;
import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.ExperimentEvent;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.*;

import java.io.File;

public class StatisticBestInRun<I extends Individual, P extends Problem<I>> implements GAListener {

    private I bestInExperiment;

    public StatisticBestInRun(String experimentHeader) {
        File file = new File("statistic_best_per_experiment_fitness.xls");
        if(!file.exists()){
            pt.ipleiria.estg.dei.ia.pl5.g13.utils.FileOperations.appendToTextFile("statistic_best_per_experiment_fitness.xls", experimentHeader + "\t" + "Fitness:" + "\r\n");
        }
    }

    @Override
    public void generationEnded(GAEvent e) {
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<I, P> ga = e.getSource();
        if (bestInExperiment == null || ga.getBestInRun().compareTo(bestInExperiment) > 0) {
            bestInExperiment = (I) ga.getBestInRun().clone();
        }
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {

        String experimentTextualRepresentation = ((Experiment) e.getSource()).getExperimentTextualRepresentation();
        String experimentHeader = ((Experiment) e.getSource()).getExperimentHeader();
        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        pt.ipleiria.estg.dei.ia.pl5.g13.utils.FileOperations.appendToTextFile("statistic_best_per_experiment_fitness.xls", experimentConfigurationValues + "\t" + bestInExperiment.getFitness() + "\r\n");
        pt.ipleiria.estg.dei.ia.pl5.g13.utils.FileOperations.appendToTextFile("statistic_best_per_experiment.txt", "\r\n\r\n" + experimentTextualRepresentation + "\r\n" + bestInExperiment);
    }
}

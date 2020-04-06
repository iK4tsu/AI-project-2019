package pt.ipleiria.estg.dei.ia.pl5.g13.statisticsGA;

import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.Experiment;
import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.ExperimentEvent;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.*;
import pt.ipleiria.estg.dei.ia.pl5.g13.utils.FileOperations;

import java.io.File;

public class StatisticBestInRun<I extends Individual, P extends Problem<I>> implements GAListener {

    private I bestInExperiment;
    private File file;
    private String filename;

    public StatisticBestInRun(String experimentHeader, String filename) {
        this.filename = filename;
        file = new File("statistic_best_per_experiment_fitness_" + filename + ".xls");
        if(!file.exists()){
            FileOperations.appendToTextFile(file.getAbsolutePath(), experimentHeader + "\t" + "Fitness:" + "\r\n");
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

        FileOperations.appendToTextFile(file.getAbsolutePath(), experimentConfigurationValues + "\t" + bestInExperiment.getFitness() + "\r\n");
        FileOperations.appendToTextFile("statistic_best_per_experiment_" + this.filename + ".txt", "\r\n\r\n" + experimentTextualRepresentation + "\r\n" + bestInExperiment);
    }
}

package pt.ipleiria.estg.dei.ia.pl5.g13.statisticsGA;

import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.Experiment;
import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.ExperimentEvent;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.*;
import pt.ipleiria.estg.dei.ia.pl5.g13.utils.Maths;

import java.io.File;

public class StatisticBestAverage<E extends Individual, P extends Problem<E>> implements GAListener  {
    
    private final double[] values;
    private int run;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        values = new double[numRuns];
        File file = new File("statistic_average_fitness.xls");
        if(!file.exists()){
            pt.ipleiria.estg.dei.ia.pl5.g13.utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentHeader + "\t" + "Average:" + "\t" + "StdDev:" + "\r\n");
        }
    }

    @Override
    public void generationEnded(GAEvent e) {    
    }

    @Override
    public void runEnded(GAEvent e) {
        GeneticAlgorithm<E, P> ga = e.getSource();
        values[run++] = ga.getBestInRun().getFitness();
    }

    @Override
    public void experimentEnded(ExperimentEvent e) {
        double average = Maths.average(values);
        double sd = Maths.standardDeviation(values, average);

        String experimentConfigurationValues = ((Experiment) e.getSource()).getExperimentValues();

        pt.ipleiria.estg.dei.ia.pl5.g13.utils.FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentConfigurationValues + "\t" + average + "\t" + sd + "\r\n");
    }
}

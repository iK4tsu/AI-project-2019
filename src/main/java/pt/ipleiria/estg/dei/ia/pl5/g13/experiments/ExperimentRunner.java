package pt.ipleiria.estg.dei.ia.pl5.g13.experiments;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Solution;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.Pair;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.WarehouseAgentSearch;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.WarehouseExperimentsFactory;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.WarehouseProblemForGA;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.WarehouseProblemForSearch;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.WarehouseState;

public class ExperimentRunner {
	private WarehouseExperimentsFactory experimentsFactory;
	private WarehouseProblemForGA problemGA;

	public ExperimentRunner(
		WarehouseExperimentsFactory experimentsFactory,
		WarehouseProblemForGA problemGA)
	{
		this(experimentsFactory);
		this.problemGA = problemGA;
	}

	public ExperimentRunner(WarehouseExperimentsFactory experimentsFactory)
	{
		this.experimentsFactory = experimentsFactory;
	}

	public ExperimentRunner(File configurationFile) throws IOException
	{
		this.experimentsFactory = new WarehouseExperimentsFactory(configurationFile);
	}

	public double getProgress()
	{
		return ( (double) experimentsFactory.getCurExperimentIndex() / experimentsFactory.getNumExperiments() ) * 100;
	}

	public WarehouseProblemForGA run()
	{
		try {
			int[][] matrix = WarehouseAgentSearch.readInitialStateFromFile(new File(experimentsFactory.getFile()));
			WarehouseAgentSearch agentSearch = new WarehouseAgentSearch(new WarehouseState(matrix));

			LinkedList<Pair> pairs = agentSearch.getPairs();
			for (Pair p : pairs) {
				WarehouseState state = ((WarehouseState) agentSearch.getEnvironment()).clone();
				if (state.getLineAgent()!=p.getCell1().getLine() || state.getColumnAgent()!=p.getCell1().getColumn() )
					state.setCellAgent(p.getCell1().getLine(), p.getCell1().getColumn()+1);
				else
					state.setCellAgent(p.getCell1().getLine(), p.getCell1().getColumn());
				WarehouseProblemForSearch problem = new WarehouseProblemForSearch(state, p.getCell2());
				Solution s = agentSearch.solveProblem(problem);
				p.setValue((int) s.getCost());
			}
			problemGA = new WarehouseProblemForGA(agentSearch);

			while (experimentsFactory.hasMoreExperiments()) {
				try {

					Experiment experiment = experimentsFactory.nextExperiment(agentSearch);
					experiment.run();

				} catch (IOException e1) {
					e1.printStackTrace(System.err);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return problemGA;
	}
}

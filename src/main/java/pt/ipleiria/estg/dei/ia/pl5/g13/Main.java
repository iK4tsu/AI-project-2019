package pt.ipleiria.estg.dei.ia.pl5.g13;

import javax.swing.*;

import pt.ipleiria.estg.dei.ia.pl5.g13.gui.*;
import pt.ipleiria.estg.dei.ia.pl5.g13.experiments.ExperimentRunner;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {

	public Main() {

	MainFrame frame = new MainFrame();

	// Center the window
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension frameSize = frame.getSize();
	if (frameSize.height > screenSize.height) {
		frameSize.height = screenSize.height;
	}
	if (frameSize.width > screenSize.width) {
		frameSize.width = screenSize.width;
	}
	frame.setLocation((screenSize.width - frameSize.width) / 2,
						(screenSize.height - frameSize.height) / 2);

	frame.setVisible(true);
	}

	public static void main(String[] args) {
		if (args.length == 1)
		{
			try {
				ExperimentRunner runner = new ExperimentRunner(new File(args[0]));

				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					@Override
					public Void doInBackground() {
						runner.run();
						return null;
					}
				};

				System.out.println("Starting worker for '" + args[0] + "'");
				worker.execute();
				while (!worker.isDone())
				{
					System.out.println("Worker for '" + args[0] + "' progress: " + runner.getProgress() + "%");
					Thread.sleep(5000);
				}
				System.out.println("Worker for '" + args[0] + "' finished!");
			} catch(IOException | InterruptedException ex)
			{
				ex.printStackTrace();
			}
		} else
		{
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					new Main();
				}
			});
		}
	}
}

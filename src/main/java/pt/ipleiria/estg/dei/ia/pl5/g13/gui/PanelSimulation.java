package pt.ipleiria.estg.dei.ia.pl5.g13.gui;

import pt.ipleiria.estg.dei.ia.pl5.g13.agentSearch.Solution;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.WarehouseState;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.Cell;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.EnvironmentListener;
import pt.ipleiria.estg.dei.ia.pl5.g13.warehouse.Properties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class PanelSimulation extends JPanel implements EnvironmentListener {

    public static final int PANEL_SIZE = 380;
    public static final int CELL_SIZE = 18;
    public static final int GRID_TO_PANEL_GAP = 10;
    private MainFrame mainFrame;
    private WarehouseState environment;
    private Image image;
    JPanel environmentPanel = new JPanel();
    final PanelInformation panelInformation = new PanelInformation();
    final JButton buttonSimulate = new JButton("Simulate Each Request");
    private int numRequest, numSteps, indexRequest = 0;

    SwingWorker worker;

    public PanelSimulation(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        environmentPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        setLayout(new BorderLayout());

        add(environmentPanel, BorderLayout.NORTH);
        add(panelInformation, BorderLayout.CENTER);
        add(buttonSimulate, BorderLayout.SOUTH);
        buttonSimulate.addActionListener(new SimulationPanel_buttonSimulate_actionAdapter(this));

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
    }

    public void setEnvironment(WarehouseState environment) {
        this.environment = environment;
    }

    public void setJButtonSimulateEnabled(boolean enabled) {
        if (worker != null) {
            worker.cancel(true);
            worker = null;
            panelInformation.textFieldRequests.setText("0");
            panelInformation.textFieldSteps.setText("0");
            environmentPanel.repaint();
        }
        buttonSimulate.setEnabled(enabled);
    }


    public void buttonSimulate_actionPerformed(ActionEvent e) {

        createEnvironment();
        final PanelSimulation simulationPanel = this;

        worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    buttonSimulate.setEnabled(false);
                    if (numRequest == 0)
                        numSteps = 0;
                    else
                        environment.setSteps(numSteps);
                    mainFrame.getAgentSearch().setEnvironment(environment);
                    numRequest++;
                    indexRequest = 0;
                    mainFrame.getAgentSearch().executeSolutionSimulation((Solution) mainFrame.getAgentSearch().getSolutions().get(numRequest - 1));
                    numSteps = environment.getSteps();

                    if (numRequest >= mainFrame.getAgentSearch().getSolutions().size())
                        numRequest = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void done() {

                environment.removeEnvironmentListener(simulationPanel);
                buttonSimulate.setEnabled(true);
            }
        };
        worker.execute();

    }

    public void createEnvironment() {
        environment = new WarehouseState(mainFrame.getWarehouseState().getMatrix());

        environment.addEnvironmentListener(this);

        buildImage(environment);
        environmentUpdated();
    }

    public void buildImage(WarehouseState environment) {
        image = new BufferedImage(
                environment.getSize() * CELL_SIZE + 1,
                environment.getSize() * CELL_SIZE + 1,
                BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public void environmentUpdated() {

        int n = environment.getSize();
        Graphics g = image.getGraphics();
        g.setFont(new Font("Arial", Font.PLAIN, 9));

        // get the request number based on numRequest
        // this executes once before numRequest is increased
        int indexSolution = numRequest == 0 ? numRequest : numRequest - 1;

        // get the next product index in the current request
        // when the last product is catched, this var will get reseted
        // otherwise an index out of bounds exception is thrown
        if (indexRequest >= mainFrame.getAgentSearch().getRequests().get(indexSolution).getRequest().length)
            indexRequest = mainFrame.getAgentSearch().getRequests().get(indexSolution).getRequest().length - 1;

        // the genome with the current product placements
        int[] genome = mainFrame.getBestInRun().getGenome();

        // the next product in the current request
        int nextRequest = mainFrame.getAgentSearch().getRequests().get(indexSolution).getRequest()[indexRequest];

        // the shelf containing the next product
        Cell shelf = mainFrame.getAgentSearch().getShelf(mainFrame.getBestInRun().getShelfPos(genome, nextRequest));

        //Fill the cells color
        boolean hasProduct;
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                hasProduct = false;
                Color color = environment.getCellColor(y, x);
                if (color == Properties.COLORSHELF) {
                    int product = mainFrame.getBestInRun().getProductInShelf(y, x);
                    if (product != 0) {
                        hasProduct = true;

                        // if the shelf contains a product, check if it's the next in the request list
                        // if it is, also check if the Agent is next to it
                        // if it is catch the product and move on to the next one
                        if (environment.equalsAgent(shelf) && environment.getColumnAgent() == x + 1 && environment.getLineAgent() == y) {
                            indexRequest++;
                            g.setColor(Properties.COLORSHELFPRODUCTCATCH);
                            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }
                        else {
                            g.setColor(Properties.COLORSHELFPRODUCT);
                            g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }
                        g.setColor(Color.BLACK);
                        g.drawString(Integer.toString(product), x * CELL_SIZE + 2, y * CELL_SIZE + CELL_SIZE - 2);
                    }
                }

                if (!hasProduct) {
                    g.setColor(color);
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        //Draw the grid lines
        g.setColor(Color.BLACK);
        for (int i = 0; i <= n; i++) {
            g.drawLine(0, i * CELL_SIZE, n * CELL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, n * CELL_SIZE);
        }

        g = environmentPanel.getGraphics();
        g.drawImage(image, GRID_TO_PANEL_GAP, GRID_TO_PANEL_GAP, null);
        panelInformation.textFieldRequests.setText(Integer.toString(numRequest));
        panelInformation.textFieldSteps.setText(Integer.toString(environment.getSteps()));

        try {
            Thread.sleep(200);
        } catch (InterruptedException ignore) {
        }
    }

    public void setNumRequest(int numRequest) {
        this.numRequest = numRequest;
    }
}

//--------------------
class SimulationPanel_buttonSimulate_actionAdapter implements ActionListener {

    final private PanelSimulation adaptee;

    SimulationPanel_buttonSimulate_actionAdapter(PanelSimulation adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        adaptee.buttonSimulate_actionPerformed(e);
    }
}

class PanelInformation extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 3;
    JTextField textFieldRequests = new JTextField("0", TEXT_FIELD_LENGHT);
    JTextField textFieldSteps = new JTextField("0", TEXT_FIELD_LENGHT);

    public PanelInformation() {
        title = "Simulation information";

        labels.add(new JLabel("Request: "));
        valueComponents.add(textFieldRequests);
        labels.add(new JLabel("Steps: "));
        valueComponents.add(textFieldSteps);
        configure();
    }

    public JTextField gettextFieldRequests() {
        return textFieldRequests;
    }

    public JTextField getTextFieldSteps() {
        return textFieldSteps;
    }


}

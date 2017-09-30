import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

@SuppressWarnings("serial")
class FractalViewer extends JFrame implements ChangeListener, ActionListener
{
	private int width = 1200, height = 800, zoomRate=5, iterations=256;
	ImagePanel imagePanel;
	Panel buttonPanel;
	JButton resetButton, zoomInButton, zoomOutButton, backButton, exportButton;
	JSlider zoomRateSlider, iterationSlider;
	Container c;

	
	public static void main(String[] args)
	{
		new FractalViewer();
	}


	public FractalViewer()
	{
		setTitle("Fractal Viewer");
		setSize(width,height+100);


		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		buildGUI();
		setVisible(true);
	}


	private void buildGUI()
	{
		c=getContentPane();
		c.setLayout(new BorderLayout());

		buttonPanel=new Panel();
		buttonPanel.setLayout(new FlowLayout());


		resetButton=new JButton("Reset Image");
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);

		zoomInButton=new JButton("Zoom In");
		zoomInButton.addActionListener(this);
		buttonPanel.add(zoomInButton);

		zoomOutButton=new JButton("Zoom Out");
		zoomOutButton.addActionListener(this);
		buttonPanel.add(zoomOutButton);

		backButton=new JButton("Go Back");
		backButton.addActionListener(this);
		buttonPanel.add(backButton);

		exportButton=new JButton("Export Image");
		exportButton.addActionListener(this);
		buttonPanel.add(exportButton);

		buttonPanel.add(Box.createHorizontalStrut(100));

		zoomRateSlider=new JSlider(SwingConstants.HORIZONTAL,0,20,zoomRate);
		zoomRateSlider.setBorder(BorderFactory.createTitledBorder("Zoom Rate"));
		zoomRateSlider.setMajorTickSpacing(5);
		zoomRateSlider.setMinorTickSpacing(1);
		zoomRateSlider.setPaintTicks(true);
		zoomRateSlider.setPaintLabels(true);
		zoomRateSlider.setSnapToTicks(true);
		buttonPanel.add(zoomRateSlider);
		zoomRateSlider.addChangeListener(this);

		buttonPanel.add(Box.createHorizontalStrut(100));

		iterationSlider=new JSlider(SwingConstants.HORIZONTAL,0,10000,iterations);
		iterationSlider.setBorder(BorderFactory.createTitledBorder("Maximum Iterations: 			"+iterations));
		iterationSlider.setMajorTickSpacing(10000);
		iterationSlider.setPaintTicks(true);
		iterationSlider.setPaintLabels(true);
		iterationSlider.setValueIsAdjusting(true);
		buttonPanel.add(iterationSlider);
		iterationSlider.addChangeListener(this);

		SpinnerModel model = new SpinnerNumberModel(256,1,1000000,1);
		JSpinner iterationSpinner=new JSpinner(model);
		buttonPanel.add(iterationSpinner);				

		imagePanel=new ImagePanel(width,height);

		c.add((buttonPanel),BorderLayout.NORTH);
		c.add((imagePanel),BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		if(e.getSource()==resetButton)
			imagePanel.reset();
		else if(e.getSource()==zoomInButton)
			imagePanel.zoomIn();
		else if(e.getSource()==zoomOutButton)
			imagePanel.zoomOut();
		else if(e.getSource()==exportButton)
			imagePanel.export();
		else if(e.getSource()==backButton)
			imagePanel.goBack();
	}


	@Override
	public void stateChanged(ChangeEvent e) 
	{
		JSlider source = (JSlider)e.getSource();
		if (source==zoomRateSlider && !source.getValueIsAdjusting()) 
		{
			zoomRate = source.getValue();
			imagePanel.setZoomRate(zoomRate);
		}
		else if (source==iterationSlider && !source.getValueIsAdjusting())
		{ 
			iterations=source.getValue();	
			iterationSlider.setBorder(BorderFactory.createTitledBorder("Maximum Iterations: 			"+iterations));
			imagePanel.setMaxIterations(iterations);
		}
	}
	
	
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.filechooser.*;


@SuppressWarnings("serial")
class ImagePanel extends JPanel implements MouseListener
{
	private int width;
	private int height;
	private int maxIterations = 256;

	private ArrayList<Bounds> bounds;	
	private Bounds currentBounds;
	private double XMIN;
 	private double XMAX;
 	private double YMIN;
 	private double YMAX;
	double a = -0.5;
	double b = 0;
	

	private double zoomRate = 2;


	public ImagePanel(int xSize, int ySize)
	{
		width=xSize;
		height=ySize;
		bounds=new ArrayList<Bounds>();
		currentBounds=new Bounds(-2,1,-1,1);
		addMouseListener(this);
	
	}


	@Override
	public void update(Graphics g)
	{
		XMIN = currentBounds.getXMin();
		XMAX = currentBounds.getXMax();
		YMIN = currentBounds.getYMin();
		YMAX = currentBounds.getYMax();
		
		System.out.println("Working...");

		for (int i = 0; i < width; i++)
		{
		      	for (int j = 0; j < height; j++) 
			{
				double a = XMIN + i * (XMAX - XMIN) / width;
				double b = YMIN + j * (YMAX - YMIN) / height;
				int color=escapesToInfinity(a,b);
														
				if (color==0)
					g.setColor(Color.black);
				else
				{	
					//color=16777216/color;
					g.setColor(new Color(color));
				}
				g.fillRect(i,j,1,1);
			}
		}
		System.out.println("Done...");
	}

	
	@Override
	public void paint(Graphics g)
	{
		update(g);
	}

	
	private int escapesToInfinity(double a, double b) 
	{
    	double x = 0.0;
    	double y = 0.0;
    	int iterations = 0;
    	do 
		{
      		double xnew = x * x - y * y + a;
      		double ynew = 2 * x * y + b;
      		x = xnew;
      		y = ynew;
      		iterations++;
      		if (iterations == maxIterations) {
      			return 0; 
			}
	    } while (x <= 2 && y <= 2);
	    
    	return iterations;		
  	}


	@Override
	public void mousePressed(MouseEvent evt) 
	{
		if(evt.getButton()==1)
		{
			bounds.add(currentBounds);
	
			a = XMIN + evt.getX() * (XMAX - XMIN) / width;
			b = YMIN + evt.getY() * (YMAX - YMIN) / height; 
		
			double widthScaled=XMAX-XMIN;
			double heightScaled=YMAX-YMIN;

			XMIN=a-(widthScaled/(2*zoomRate));
			XMAX=a+(widthScaled/(2*zoomRate));	
			YMIN=b-(heightScaled/(2*zoomRate));
			YMAX=b+(heightScaled/(2*zoomRate));
		
			Bounds bnds=new Bounds(XMIN,XMAX,YMIN,YMAX);
			currentBounds=bnds;
			repaint();
		}
		else if(evt.getButton()==3 && bounds.size()>0)
			goBack();
	}


	public void reset()
	{
		currentBounds=new Bounds(-2,1,-1,1);
		bounds.clear();
		repaint();
	}


	public void zoomIn()
	{
		bounds.add(currentBounds);
	
		double widthScaled=XMAX-XMIN;
		double heightScaled=YMAX-YMIN;

		XMIN=a-(widthScaled/(2*zoomRate));
		XMAX=a+(widthScaled/(2*zoomRate));	
		YMIN=b-(heightScaled/(2*zoomRate));
		YMAX=b+(heightScaled/(2*zoomRate));
		
		Bounds bnds=new Bounds(XMIN,XMAX,YMIN,YMAX);
		currentBounds=bnds;
		repaint();
	}


	public void zoomOut()
	{
		bounds.add(currentBounds);
	
		double widthScaled=XMAX-XMIN;
		double heightScaled=YMAX-YMIN;

		XMIN=a-(widthScaled/(2/zoomRate));
		XMAX=a+(widthScaled/(2/zoomRate));	
		YMIN=b-(heightScaled/(2/zoomRate));
		YMAX=b+(heightScaled/(2/zoomRate));
		
		Bounds bnds=new Bounds(XMIN,XMAX,YMIN,YMAX);
		currentBounds=bnds;
		repaint();
		
	}


	public void export()
	{
		JFrame win = (JFrame)SwingUtilities.getWindowAncestor(this);System.out.println(win.getWidth()+","+win.getHeight());
		BufferedImage image = (BufferedImage)win.createImage(win.getWidth(), win.getHeight());
		BufferedImage cutImage = image.getSubimage(0,win.getHeight()-height,width,height);
		Graphics g = image.getGraphics();
		win.paint(g);
		g.dispose();
		
		File file = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Specify a file to save");
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
        		"PNG Image", "png");
    		fileChooser.setFileFilter(filter);	  
		 
		int userSelection = fileChooser.showSaveDialog(win);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) 
		{
		    file = fileChooser.getSelectedFile();
		
			try      
			{
				ImageIO.write(cutImage, "png", file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}


	public void goBack()
	{
		currentBounds=bounds.get(bounds.size()-1);
		bounds.remove(bounds.size()-1);
		repaint();
	}


	public void setZoomRate(int zr)
	{
		if(zr==0)
			zr=1;
		zoomRate=zr;
	} 


	public void setMaxIterations(int its)
	{
		maxIterations=its;
		repaint();
	}

	
	@Override
	public void mouseClicked(MouseEvent evt) { }
	@Override
	public void mouseReleased(MouseEvent evt) { }
	@Override
	public void mouseEntered(MouseEvent evt) { }
	@Override
	public void mouseExited(MouseEvent evt) { }

}

class Bounds
{
	double xMin, xMax, yMin, yMax;


	public Bounds(double xMn, double xMx, double yMn, double yMx)
	{
		xMin = xMn;
		xMax = xMx;
		yMin = yMn;
		yMax = yMx;
	}

	
	public void setXMin(double xMn)
	{
		xMin = xMn;
	}


	public void setXMax(double xMx)
	{
		xMax = xMx;
	}


	public void setYMin(double yMn)
	{
		yMin = yMn;
	}


	public void setYMax(double yMx)
	{
		yMax = yMx;
	}

	
	public double getXMin()
	{
		return xMin;
	}


	public double getXMax()
	{
		return xMax;
	}
	

	public double getYMin()
	{
		return yMin;
	}


	public double getYMax()
	{
		return yMax;
	}


	public String toString()
	{
		return "XMin = "+xMin + "\n + XMax = "+xMax + "\n + YMin = "+yMin + "\n + YMax = "+yMax;
	}
}


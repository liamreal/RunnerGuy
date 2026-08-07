package com.liamreal.util;

public class Vector2f implements Comparable<Vector2f> {

	private double x=0;
	private  double y=0;
	
	public Vector2f() 
	{  
		setX(0.0f);
		setY(0.0f);
	}
	 
	public Vector2f(double x, double y) 
	{ 
		this.setX(x);
		this.setY(y);
	}

	// from: https://stackoverflow.com/questions/369512/how-to-compare-objects-by-multiple-fields
	public int compareTo(Vector2f other) {
		int i = this.compareX(other);
		if (i != 0) return i;

		i = this.compareY(other);
		return i;
	}

	// compare individual points
	public int compareX(Vector2f other) { return Double.compare(this.getX(), other.getX()); }
	public int compareY(Vector2f other) { return Double.compare(this.getY(), other.getY()); }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj.getClass() != this.getClass()) { return false; }
        Vector2f other = (Vector2f) obj;
		if (this.getX() != other.getX()) { return false; }
		if (this.getY() != other.getY()) { return false; }
        return true;
    }
	
	// distance between 2 vectors is same as distance between 2 points
	public double euclideanDistance(Vector2f otherVector) {
		double xDifference = (otherVector.getX() - this.getX());
		double yDifference = (otherVector.getY() - this.getY());
		return Math.sqrt(
			xDifference*xDifference + yDifference*yDifference
		);
	}

	 //implement Vector plus a Vector  and comment what the method does  
	public Vector2f PlusVector(Vector2f Additonal) 
	{ 
		return new Vector2f(this.getX()+Additonal.getX(), this.getY()+Additonal.getY());
	} 
	
	 //implement Vector minus a Vector  and comment what the method does  
	public Vector2f MinusVector(Vector2f Minus) 
	{ 
		return new Vector2f(this.getX()-Minus.getX(), this.getY()-Minus.getY());
	}
	
	//Implement a Vector * Scalar  and comment what the method does    ( we wont create Scalar * Vector due to expediency ) 
	public Vector2f byScalar(double scale )
	{
		return new Vector2f(this.getX()*scale, this.getY()*scale);
	}
	
	//implement returning the negative of a Vector  and comment what the method does  
	public Vector2f  NegateVector()
	{
		return new Vector2f(-this.getX(), -this.getY());
	}
	
	//implement getting the length of a Vector    and comment what the method does
	public double length()
	{
	    return (double) Math.sqrt(getX()*getX() + getY()*getY());
	}
	
	//implement getting the Normal  of a Vector   and comment what the method does
	public Vector2f Normal()
	{
		double LengthOfTheVector=  this.length();
		// if coords are 0 would be divide by zero (infinity), so return zero vector
		if (LengthOfTheVector == 0) { return new Vector2f(0, 0); }
		return this.byScalar(1.0f/ LengthOfTheVector); 
	} 
	
	//implement getting the dot product of Vector.Vector and comment what the method does 

	public double dot(Vector2f v)
	{ 
		return ( this.getX()*v.getX() + this.getY()*v.getY());
	}
	
	//implement getting the cross product of Vector X Vector and comment what the method does  
	public double cross(Vector2f v)  
	{ 
    	return getX()*v.getY() - getY()*v.getX();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String toString() {
		return String.format("%s: (%.2f,%.2f)", super.toString(), this.getX(), this.getY());
	}

 
}
	 
	   

/*

										MMMM                                        
										MMMMMM                                      
 										MM MMMM                                    
 										MMI  MMMM                                  
 										MMM    MMMM                                
 										MMM      MMMM                              
  										MM        MMMMM                           
  										MMM         MMMMM                         
  										MMM           OMMMM                       
   										MM             .MMMM                     
MMMMMMMMMMMMMMM                        MMM              .MMMM                   
MM   IMMMMMMMMMMMMMMMMMMMMMMMM         MMM                 MMMM                 
MM                  ~MMMMMMMMMMMMMMMMMMMMM                   MMMM               
MM                                  OMMMMM                     MMMMM            
MM                                                               MMMMM          
MM                                                                 MMMMM        
MM                                                                   ~MMMM      
MM                                                                     =MMMM    
MM                                                                        MMMM  
MM                                                                       MMMMMM 
MM                                                                     MMMMMMMM 
MM                                                                  :MMMMMMMM   
MM                                                                MMMMMMMMM     
MM                                                              MMMMMMMMM       
MM                             ,MMMMMMMMMM                    MMMMMMMMM         
MM              IMMMMMMMMMMMMMMMMMMMMMMMMM                  MMMMMMMM            
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM               ZMMMMMMMM              
MMMMMMMMMMMMMMMMMMMMMMMMMMMMM          MM$             MMMMMMMMM                
MMMMMMMMMMMMMM                       MMM            MMMMMMMMM                  
  									MMM          MMMMMMMM                     
  									MM~       IMMMMMMMM                       
  									MM      DMMMMMMMM                         
 								MMM    MMMMMMMMM                           
 								MMD  MMMMMMMM                              
								MMM MMMMMMMM                                
								MMMMMMMMMM                                  
								MMMMMMMM                                    
  								MMMM                                      
  								MM                                        
                             GlassGiant.com */
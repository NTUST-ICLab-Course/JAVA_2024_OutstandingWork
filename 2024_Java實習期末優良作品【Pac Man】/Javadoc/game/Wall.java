package game;
// Name: 				Date:

   import java.awt.*;
    public class Wall
   {
    	private static final int XADD = 250;
 	    private static final int YADD = 150;
 	    private static final int DUnits = 20;
 	    
    	private int myX;
        private int myY;
        private int myXW;
        private int myYW;
    //private fields, all ints, for a Bumper
    //hint: the "location" of the bumper begins at its top left corner.      
	 
   
   
     //constructors
       public Wall()         //default constructor
      {
    	   myX = 0;
           myY = 0;
           myXW = 0;
           myYW = 0;
      }
       public Wall(int x, int y, int xWidth, int yWidth)
      {
    	   myX = x;
           myY = y;
           myXW = xWidth;
           myYW = yWidth;
      }
      
     // accessor methods  (one for each field)
       public int getX() 
       { 
          return myX;
       }
       public int getY()      
       { 
     	  return myY;
       }
       public int getXWidth() 
       { 
     	  return myXW;
       }
       public int getYWidth() 
       { 
     	  return myYW;
       }
    // modifier methods
       public void setX(int x)
       {
          myX = x;
       } 
       public void setY(int y)
       {
     	  myY = y;
       } 
       public void setXWidth(int x)
       {
          myXW = x;
       } 
       public void setYWidth(int y)
       {
     	  myYW = y;
       } 

       public void draw(Graphics myBuffer) 
      {
         myBuffer.setColor(Color.BLUE);
         myBuffer.fillRoundRect(XADD+myX*DUnits-2, YADD+myY*DUnits-2, myXW*DUnits-DUnits+5, myYW*DUnits-DUnits+5, 30, 30);
      }   

   }

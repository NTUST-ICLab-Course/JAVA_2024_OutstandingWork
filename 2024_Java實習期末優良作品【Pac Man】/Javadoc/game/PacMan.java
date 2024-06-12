package game;

   import java.awt.*;
   
   public class PacMan
   {
	  private static final int XADD = 250;
	  private static final int YADD = 150;
	  private static final int DUnits = 20;
      private double myX;   
      private double myY;

      public PacMan()
      {
         myX = 14;
         myY = 23;
      }
      public PacMan(int x, int y)
      {
         myX = x;
         myY = y;
      }
    // accessor methods
      public double getX() 
      { 
         return myX;
      }
      public double getY()      
      { 
    	  return myY;
      }
   // modifier methods
      public void setX(double x)
      {
         myX = x;
         if(myX < 0) myX = 27;
         if(myX > 27) myX = 0;
      } 
      public void setY(double y)
      {
    	 myY = y;
    	 if(myY < 0) myY = 30;
         if(myY > 30) myY = 0;
      } 
    //	 instance methods
      public void draw(Graphics myBuffer) 
      {
         myBuffer.setColor(Color.YELLOW);
         myBuffer.fillOval((int)(XADD+myX*DUnits-9), (int)(YADD+myY*DUnits-9), 19, 19);
      }
   }
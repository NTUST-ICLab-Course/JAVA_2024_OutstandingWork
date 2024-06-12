package game;

   import java.awt.*;
   
   public class bean
   {
	  private static final int XADD = 250;
	  private static final int YADD = 150;
	  private static final int DUnits = 20;
      private int myX;   
      private int myY;
      private boolean bigbean;
      private boolean show;

      public bean()
      {
         myX = 200;
         myY = 200;
      }
      public bean(int x, int y, boolean big, boolean S)
      {
         myX = x;
         myY = y;
         bigbean = big;
         show = S;
      }
    // accessor methods
      public int getX() 
      { 
         return myX;
      }
      public int getY()      
      { 
    	  return myY;
      }
      public boolean getB() 
      { 
    	  return bigbean;
      }
      public boolean getS() 
      { 
         return show;
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
      public void setB(boolean B)
      {
    	  bigbean = B;
      }
      public void setS(boolean S)
      {
    	  show = S;
      }
    //	 instance methods
      public void draw(Graphics myBuffer) 
      {
         myBuffer.setColor(Color.orange);
         if(show)if(!bigbean)myBuffer.fillOval(XADD+myX*DUnits-2, YADD+myY*DUnits-2, 5, 5);
         		 else myBuffer.fillOval(XADD+myX*DUnits-6, YADD+myY*DUnits-6, 13, 13);
      }
   }
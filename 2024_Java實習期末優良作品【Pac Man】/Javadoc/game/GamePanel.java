package game;
//Name:    Date:
   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.*;
   import java.awt.image.*;
   
    public class GamePanel extends JPanel
   {
      private static final int FRAME = 1000;
      private static final Color BACKGROUND = new Color(0, 0, 0);
      private BufferedImage myImage;
      private Graphics myBuffer;
      private PacMan PM;
      private bean[] beans;
      private Wall[] Walls;
      private Timer t; 
      private int dir=1, dirr=1;
      private int Score=0;
      private String Score_String;
		//constructor   
       public GamePanel()
      {
    	   myImage =  new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
           myBuffer = myImage.getGraphics();
           myBuffer.setColor(BACKGROUND);
           myBuffer.fillRect(0, 0, FRAME,FRAME);
           
           PM = new PacMan();
           WallsCreate();
           beansCreate();
           beansSet();
          
           t = new Timer(100, new Listener());
           t.start();
           
           addKeyListener(new Key());
           setFocusable(true);
      }
       public void paintComponent(Graphics g)
      {
    	   g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
      }
       private class Key extends KeyAdapter {
    	   public void keyPressed (KeyEvent e) {
    		   if(e.getKeyCode() == KeyEvent.VK_UP) dir = 4;
    		   if(e.getKeyCode() == KeyEvent.VK_RIGHT) dir = 3;
    		   if(e.getKeyCode() == KeyEvent.VK_DOWN) dir = 2;
    		   if(e.getKeyCode() == KeyEvent.VK_LEFT) dir = 1;
    	   }
       }
       private class Listener implements ActionListener
      {
          public void actionPerformed(ActionEvent e)
         {
        	  myBuffer.setColor(BACKGROUND);
              myBuffer.fillRect(0, 0, FRAME,FRAME);
              

        	  PMrun();
        	  PMeat();

        	  DrawWall();
        	  Drawbeans();
        	  PM.draw(myBuffer);
        	  ShowScore();
        	  
        	  repaint();
         }
      }   
       

       		private void WallsCreate(){				//Map
       			Walls = new Wall[54];
                Walls[0] = new Wall(0, 0, 28, 1);		//boundary
                Walls[1] = new Wall(27, 0, 1, 10);
                Walls[2] = new Wall(0, 0, 1, 10);
                Walls[3] = new Wall(0, 9, 6, 1);
                Walls[4] = new Wall(22, 9, 6, 1);
                Walls[5] = new Wall(5, 9, 1, 5);
                Walls[6] = new Wall(22, 9, 1, 5);
                Walls[7] = new Wall(0, 13, 6, 1);
                Walls[8] = new Wall(22, 13, 6, 1);
                Walls[9] = new Wall(0, 15, 6, 1);
                Walls[10] = new Wall(22, 15, 6, 1);
                Walls[11] = new Wall(5, 15, 1, 5);
                Walls[12] = new Wall(22, 15, 1, 5);
                Walls[13] = new Wall(0, 19, 6, 1);
                Walls[14] = new Wall(22, 19, 6, 1);
                Walls[15] = new Wall(0, 19, 1, 12);
                Walls[16] = new Wall(27, 19, 1, 12);
                Walls[17] = new Wall(0, 30, 28, 1);
                
                
                Walls[18] = new Wall(2, 2, 4, 3);		//upper part
                Walls[19] = new Wall(2, 6, 4, 2);
                Walls[20] = new Wall(7, 2, 5, 3);
                Walls[21] = new Wall(7, 6, 2, 8);
                Walls[22] = new Wall(7, 9, 5, 2);
                Walls[23] = new Wall(10, 6, 8, 2);
                Walls[24] = new Wall(13, 6, 2, 5);
                Walls[25] = new Wall(13, 0, 2, 5);
                Walls[26] = new Wall(16, 2, 5, 3);
                Walls[27] = new Wall(19, 6, 2, 8);
                Walls[28] = new Wall(16, 9, 5, 2);
                Walls[29] = new Wall(22, 2, 4, 3);
                Walls[30] = new Wall(22, 6, 4, 2);
                
                
                Walls[31] = new Wall(10, 12, 3, 1);		//middle
                Walls[32] = new Wall(15, 12, 3, 1);
                Walls[33] = new Wall(10, 12, 1, 5);
                Walls[34] = new Wall(10, 16, 8, 1);
                Walls[35] = new Wall(17, 12, 1, 5);
                
                
                Walls[36] = new Wall(7, 15, 2, 5);		//lower part
                Walls[37] = new Wall(19, 15, 2, 5);
                Walls[38] = new Wall(10, 18, 8, 2);
                Walls[39] = new Wall(13, 18, 2, 5);
                Walls[40] = new Wall(0, 24, 3, 2);
                Walls[41] = new Wall(2, 21, 4, 2);
                Walls[42] = new Wall(4, 21, 2, 5);
                Walls[43] = new Wall(7, 21, 5, 2);
                Walls[44] = new Wall(7, 24, 2, 5);
                Walls[45] = new Wall(2, 27, 10, 2);
                Walls[46] = new Wall(10, 24, 8, 2);
                Walls[47] = new Wall(13, 24, 2, 5);
                
                Walls[48] = new Wall(16, 21, 5, 2);
                Walls[49] = new Wall(22, 21, 4, 2);
                Walls[50] = new Wall(22, 21, 2, 5);
                Walls[51] = new Wall(19, 24, 2, 5);
                Walls[52] = new Wall(16, 27, 10, 2);
                Walls[53] = new Wall(25, 24, 3, 2);
                
       		}
       		private void DrawWall(){
       			for(int i=0; i<Walls.length; i++) {
       				Walls[i].draw(myBuffer);
       			}
       		}
       		
       		private void beansCreate(){	
       			beans = new bean[868]; 
                for(int i=0; i<28; i++) {
             	   for(int j=0; j<31; j++) {
             		   beans[i+j*28] = new bean(i, j, false, false);
             	   }
                }
       		}
       		private void beansSet(){	
       			//Rows (X1,X2,Y)
       			beansSetRows(1, 12, 1);
       			beansSetRows(15, 26, 1);
       			beansSetRows(1, 26, 5);
       			beansSetRows(1, 6, 8);
       			beansSetRows(9, 12, 8);
       			beansSetRows(15, 18, 8);
       			beansSetRows(21, 26, 8);
       			beansSetRows(1, 12, 20);
       			beansSetRows(15, 26, 20);
       			beansSetRows(1, 3, 23);
       			beansSetRows(6, 21, 23);
       			beansSetRows(24, 26, 23);
       			beansSetRows(1, 6, 26);
       			beansSetRows(9, 12, 26);
       			beansSetRows(15, 18, 26);
       			beansSetRows(21, 26, 26);
       			beansSetRows(1, 26, 29);
       			
       			//Cols (Y1,Y2,X)
       			beansSetCols(1, 8, 1);
       			beansSetCols(1, 26, 6);
       			beansSetCols(1, 5, 12);
       			beansSetCols(1, 5, 15);
       			beansSetCols(1, 26,21);
       			beansSetCols(1, 8, 26);
       			beansSetCols(5, 8, 9);
       			beansSetCols(5, 8, 18);
       			beansSetCols(20, 23,1);
       			beansSetCols(20, 23, 12);
       			beansSetCols(20, 23, 15);
       			beansSetCols(20, 23, 26);
       			beansSetCols(23, 26, 3);
       			beansSetCols(23, 26, 6);
       			beansSetCols(23, 26, 18);
       			beansSetCols(23, 26, 24);
      			beansSetCols(26, 29, 1);
       			beansSetCols(26, 29, 12);
       			beansSetCols(26, 29, 15);
       			beansSetCols(26, 29, 26);
       			
       			//Big beans[x+y*28]
       			beans[1+3*28].setB(true);
       			beans[26+3*28].setB(true);
       			beans[1+23*28].setB(true);
       			beans[26+23*28].setB(true);
       			//pac man
       			beans[14+23*28].setS(false);
       		}
       		private void beansSetRows(int x1, int x2, int y){	
       			for(int i=x1; i<=x2; i++) {
              		 beans[i+y*28].setS(true);
                 }
       		}
       		private void beansSetCols(int y1, int y2, int x){	
       			for(int i=y1; i<=y2; i++) {
               		  beans[x+i*28].setS(true);
                 }
        	}
       		
       		private void Drawbeans(){	
                for(int i=0; i<28; i++) {
              	  for(int j=0; j<31; j++) {
              		  beans[i+j*28].draw(myBuffer);
              	  }
                }
       		}
       		
       		private void ShowScore() {
       			Score_String = Score+"";
       			myBuffer.setFont(new Font("Dialog", Font.PLAIN, 30));
       			myBuffer.setColor(Color.white);
       			myBuffer.drawString("SCORE", 680, 110);
       			myBuffer.drawString(Score_String, 770-(int)(Math.log10(Score))*15, 140);
       		}
       		
       		
       		private void PMrun(){
       			//new dir feasible?
       			if(dir == 1 ) 
       			if(!block(PM.getX()-1, PM.getY()))
       			if((PM.getX() % 1 == 0 && PM.getY() % 1 == 0) || dirr == 3)
       				dirr = dir;
       			
       			if(dir == 2) 
       			if(!block(PM.getX(), PM.getY()+1))
       			if((PM.getX() % 1 == 0 && PM.getY() % 1 == 0) || dirr == 4)
       				dirr = dir;
       			
       			if(dir == 3) 
       			if(!block(PM.getX()+1, PM.getY()))
       			if((PM.getX() % 1 == 0 && PM.getY() % 1 == 0) || dirr == 1)
       				dirr = dir;
       			
       			if(dir == 4) 
       			if(!block(PM.getX(), PM.getY()-1))
       			if((PM.getX() % 1 == 0 && PM.getY() % 1 == 0) || dirr == 2)
       				dirr = dir;
       			
       			//overwrite dir
       			if(dirr == 1 )if(!block(PM.getX()-1, PM.getY())) PM.setX(PM.getX()-0.5);
       			if(dirr == 2) if(!block(PM.getX(), PM.getY()+1)) PM.setY(PM.getY()+0.5);
       			if(dirr == 3) if(!block(PM.getX()+1, PM.getY())) PM.setX(PM.getX()+0.5);
       			if(dirr == 4) if(!block(PM.getX(), PM.getY()-1)) PM.setY(PM.getY()-0.5);
       			
       		 }
       		private boolean block(double X, double Y){
       			for(int i=0; i<Walls.length; i++) {
       				if( X >= Walls[i].getX() &&
       					X <= Walls[i].getX()+Walls[i].getXWidth()-1 &&
       					Y >= Walls[i].getY() &&
       					Y <= Walls[i].getY()+Walls[i].getYWidth()-1) 
     				{
       					return true;
       				}
       			}
				return false;
       		 }
       		private void PMeat(){
       			if((PM.getX() % 1 == 0) && PM.getY() % 1 == 0) {
       			if(beans[(int)(PM.getX()+PM.getY()*28)].getS()) {
       				beans[(int)(PM.getX()+PM.getY()*28)].setS(false);
       				Score += 10;
       			}}
       		 }
   }
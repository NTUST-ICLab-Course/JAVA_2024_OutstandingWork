package game;
   //Torbert, e-mail: smtorbert@fcps.edu
	//version 6.17.2003

   import javax.swing.JFrame;
    public class Main
   {
       public static void main(String[] args)
      { 
         JFrame frame = new JFrame();
         frame.setSize(1000,1000);
         frame.setLocation(400, 0);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       	 frame.setContentPane(new GamePanel());
         frame.setVisible(true);
      }
   }
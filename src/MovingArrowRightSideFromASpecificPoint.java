import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MovingArrowRightSideFromASpecificPoint extends JFrame implements MouseMotionListener,MouseListener{
	static int countArrow=0;

	static final float MAXARROW=1000;
	static  float ARROWSPEED=5;
	static  float BALOONSPEED=50;
	public MovingArrowRightSideFromASpecificPoint() {
		getContentPane().setLayout(null);
		//t.start();
		addArrows(0);
		addBaloons();
		this.setSize(500, 500);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}

	ArrayList<Arrow> arrows=new ArrayList<Arrow>();
	ArrayList<Baloon> baloons=new ArrayList<Baloon>();
	public static void main(String ...args)
	{
		MovingArrowRightSideFromASpecificPoint window = new MovingArrowRightSideFromASpecificPoint();
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		Arrow arrow=arrows.get(arrows.size()-1);
		
		arrow.setBounds(arrow.getBounds().x,e.getY(), arrow.getBounds().width, arrow.getBounds().height);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(arrows.size()<=MAXARROW)
		{
		arrows.get(arrows.size()-1).setText("-:----");
		//arrows.get(arrows.size()-1).setForeground(Color.GRAY);
		arrows.get(arrows.size()-1).T=new MoveArrow();
		
		arrows.get(arrows.size()-1).T.moveArrow(0, e.getY(), 500, arrows.get(arrows.size()-1));
		//this.removeMouseMotionListener(this);
		addArrows(e.getY());
		addBaloons();
		}
		else
		{
			arrows.get(arrows.size()-1).setText("  }");
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	void addArrows(int offset)
	{
		arrows.add(new Arrow());
		arrows.get(arrows.size()-1).setBounds(0, offset, 200, 100);
		this.add(arrows.get(arrows.size()-1));
	}
	synchronized void checkForBlast()
	{
		
		for(int i=0;i<arrows.size();i++)
		{
			for(int j=0;j<baloons.size();j++)
			{
				Rectangle rect=new Rectangle(baloons.get(j).getBounds());
				
				int x=arrows.get(i).getX()+arrows.get(i).getWidth();
				int y=arrows.get(i).getY()+arrows.get(i).getHeight()/2;
				
				
				if(rect.contains(new Point(x, y)))
						{
					baloons.get(j).T.stop();
					this.remove(baloons.get(j));
					baloons.remove(j);
					System.out.println("Blasted");
					
					//JOptionPane.showMessageDialog(mARSSFAP, "blasted");
						}
			}
		}
		
	}
	
	
	void addBaloons()
	{
		 baloons.add(new Baloon());
		//baloons.get(baloons.size()-1).setBounds(100, 45, 50, 50);
		this.add(baloons.get(baloons.size()-1));
		
		baloons.get(baloons.size()-1).T=new MoveBaloon();
		
		baloons.get(baloons.size()-1).T.moveABaloon(100, 45, 100, baloons.get(baloons.size()-1));
		
	}

	MovingArrowRightSideFromASpecificPoint mARSSFAP=this;
	

class Arrow extends JLabel{
	
	MoveArrow T;
	public Arrow() {
		super("   -}----");
		super.setFont(new Font(super.getFont().getFontName(), super.getFont().getStyle(), 50));
	}
	
}
class Baloon extends JPanel{
	
	MoveBaloon T;
	public Baloon() {
		int r=new Random().nextInt(255);
		int g=new Random().nextInt(255);
		int b=new Random().nextInt(255);
		
		super.setBackground(new Color(r,g,b));
	}
	
}

class MoveArrow extends Thread {
	
	int xs, y, xd;
	Component cmp;


	
	private void moveArrow()
	{
	
		int i=xs;
		int width=cmp.getBounds().width;
		int height=cmp.getBounds().height;
		while(i<getToolkit().getScreenSize().width){
		cmp.setBounds(i+1,y ,width, height);
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		}
		System.out.println("Thread ended"+countArrow++);
	}
	public void moveArrow(int xs,int distance,int xd,Component cmp)
	{
		
		this.xd=xd;
		this.y=distance;
		this.cmp=cmp;
		this.xs=xs;
		super.start();
		
	}
	
	@Override
	public  void run() {
		
		moveArrow();
};
}
class MoveBaloon extends Thread {
	
	
	int xs;
	int y;
	int xd;
	Component cmp;
	
	
	private void moveBaloon()
	{

		int i=0;
		int width=cmp.getBounds().width;
		int height=cmp.getBounds().height;
		int offset=new Random().nextInt(1000);
		while(true){
		cmp.setBounds((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()-new Random().nextInt(10)*3-offset,i+1 ,50, 50);
		checkForBlast();
		if(cmp.getBounds().getY()>Toolkit.getDefaultToolkit().getScreenSize().getHeight())
		{
			i=1;
		}
		try {
			Thread.sleep((int)BALOONSPEED);
			if(BALOONSPEED>1)
			BALOONSPEED-=(int)0.10;
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		}
	}
	
	public void moveABaloon(int xs,int distance,int xd,Component cmp)
	{
		
		this.xd=xd;
		this.y=distance;
		this.cmp=cmp;
		this.xs=xs;
		super.start();
		
	}
	
	@Override
	public  void run() {
		
		moveBaloon();
};
}
}

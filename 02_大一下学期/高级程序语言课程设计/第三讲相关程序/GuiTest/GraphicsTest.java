package GuiTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JApplet;

public class GraphicsTest extends JApplet {
	public void init(){
		setSize(1000, 1200);
	}
	public void paint(Graphics g) {
		
		g.setColor(Color.blue);
		g.drawLine(30, 30, 70, 70);
		g.drawLine(400, 650, 160, 150);
		
		
		g.setColor(Color.red);
		g.drawRect(80, 40, 60, 40);
		g.fillRect(160, 20, 60, 40);
		
		
		g.setColor(Color.green);
		g.drawRoundRect(20, 100, 80, 120, 20, 20);
		g.fillRoundRect(120, 100, 80, 140, 40, 30);
		
		g.fill3DRect(220, 100, 80, 140,true);
		g.fill3DRect(400, 100, 80, 140, false);
		
		g.setColor(Color.yellow);
		g.drawOval(330, 20, 60, 60);
		g.fillOval(430, 20, 80, 60);
		
		g.setColor(Color.black);
		g.drawArc(10, 320, 100, 60, 35, 65);
		g.drawArc(110, 320, 100, 60, 35, -140);
		g.fillArc(210, 320, 100, 60, 35, 65);
		g.fillArc(310, 320, 100, 60, 35, -140);
		
		
		int j=600;      
		int xValues[] = { 20+j, 40+j, 50+j, 30+j, 20+j, 15+j,20+j };   
		int yValues[] = { 50, 50, 60, 80, 80, 60 ,50};
		Polygon polygon1 = new Polygon( xValues, yValues, 7 );
		
		g.drawPolygon( polygon1 );
		int xValues2[] = { 70+j, 90+j, 100+j, 80+j, 70+j, 65+j, 60+j };
		int yValues2[] = { 100, 100, 110, 110, 130, 110, 90 };  
		g.drawPolyline( xValues2, yValues2, 7 );

		 
	}
}

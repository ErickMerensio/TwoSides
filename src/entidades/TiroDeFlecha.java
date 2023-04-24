package entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import world.Camera;

public class TiroDeFlecha extends Entidade{

	private int dx;
	private int dy;
	private int speed = 4;
	
	public TiroDeFlecha(int x, int y, int width, int height, BufferedImage sprite,int dx,int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		x += dx*speed;
		y += dy*speed;
	}
		
	public void render (Graphics g) {
	
		g.setColor(Color.yellow);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, widht, height);
		//g.drawImage(Entidade.Flecha_Entidade, this.getX() - Camera.x, this.getY() - Camera.y,null);
		
	}
	
}

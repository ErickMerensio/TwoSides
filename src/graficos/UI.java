package graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import entidades.Player;

public class UI {

	public void render (Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8, 4,50, 8);
		g.setColor(Color.green);
		g.fillRect(8, 4, (int)((Player.vida/Player.maxVida)*50), 8);
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,8));
		g.drawString((int)Player.vida+"/"+(int)Player.maxVida, 18, 11);
	}
	
}
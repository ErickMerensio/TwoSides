package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	public String[] opcoes = {"Novo Jogo","Sair"};
	public int opcaoAtual = 0;
	public int maxOpcao = opcoes.length - 1;
	public boolean up,down;

	public void tick () {
		if(up) {
			up = false;
			opcaoAtual --;
			if(opcaoAtual < 0)
				opcaoAtual = maxOpcao;
		}
		if(down) {
			down = false;
			opcaoAtual ++;
			if(opcaoAtual > maxOpcao)
				opcaoAtual = 0;
		}
	}
	
	public void render(Graphics g) {
		//menu
		g.setColor(Color.black);
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.green);
		g.setFont(new Font("arial",Font.BOLD,50));
		g.drawString("TWO SIDES", (Game.WIDTH*Game.SCALE) /2 - 150, (Game.WIDTH*Game.SCALE) /2 - 250 );
		
		// opções do menu
		g.setFont(new Font("arial",Font.BOLD,25));
		g.setColor(Color.white);
		g.drawString("Novo Jogo ", (Game.WIDTH*Game.SCALE) /2 - 80, (Game.WIDTH*Game.SCALE) /2 - 175);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) /2 - 48, (Game.WIDTH*Game.SCALE) /2 - 125);
		
		if(opcoes[opcaoAtual] == "Novo Jogo") {
			g.drawString("--> ", (Game.WIDTH*Game.SCALE) /2 - 120, (Game.WIDTH*Game.SCALE) /2 - 175);
		}else if (opcoes[opcaoAtual] == "Sair") {
			g.drawString("-->", (Game.WIDTH*Game.SCALE) /2 - 88, (Game.WIDTH*Game.SCALE) /2 - 125);
			
		}
	}
	
}

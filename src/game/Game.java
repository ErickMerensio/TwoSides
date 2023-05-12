package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

import entidades.Radu;
import entidades.Cutscene;
import entidades.Cutscene2;
import entidades.Entidade;
import entidades.Inimigo;
import entidades.Lia;
import entidades.PaiDoRadu;
import entidades.Player;
import entidades.TiroDeFlecha;
import graficos.Spritesheet;
import graficos.UI;
import world.Camera;
import world.World;

public class Game extends Canvas implements Runnable,KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static JFrame jframe;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	public static  Player player;
	private BufferedImage image;
	public static List<Entidade> entidades;
	public static List<Inimigo> inimigos;
	public static List<Radu> boss;
	public static List<PaiDoRadu> chefao;
	public static List<TiroDeFlecha> flechas;
	public static List<Cutscene> cut;
	public static List<Cutscene2> cut2;
	public static Spritesheet spritesheet;
	public static World world;
	public static Random rand;
	public static List<Lia> lia;
	public Menu menu;
	public UI ui;
	
	public static int levelAtual = 1, maxLevel = 7;
	public static String estadoDoJogo = "Menu";
	public  boolean mostrarMensagemGameOver = true;
	private int framesGameOver = 0;
	private boolean reiniciarOJogo = false;
	
		//cutscene 
	public static int entrada = 1;
	public static int comecar = 2;
	public static int jogando = 3;
	public static int estado_cena = entrada ; 
	public int timeCena = 0, maxTimeCena = 60*15;
	
	public Game ( ) {
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		
		//Inicializando objeto
		ui = new UI();
		image = new BufferedImage (WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entidades = new ArrayList<Entidade>();
		inimigos = new ArrayList<Inimigo>();
		boss = new ArrayList<Radu>();
		chefao = new ArrayList<PaiDoRadu>();
		flechas = new ArrayList<TiroDeFlecha>();
		cut = new ArrayList<Cutscene>();
		cut2 = new ArrayList<Cutscene2>();
		spritesheet = new Spritesheet("/spritesheeet.png");
		player = new Player(0,0,16,16,spritesheet.getSprite(0, 0, 16, 16));
		lia = new ArrayList<Lia>();
		entidades.add(player);
		world = new World("/level1.png");
		menu = new Menu();
		//fim
		
	}
	
	public void initFrame() {
		
		jframe = new JFrame();
		jframe.add(this);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setTitle("TwoSides");
		
	}
	
	
	public synchronized void start () {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop () {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.start();
	}
	
	public void tick () {
		if(estadoDoJogo == "Normal") {
			reiniciarOJogo = false;
			
			if(Game.levelAtual == 1) {
			if (Game.estado_cena == Game.jogando) {
		for (int i=0; i < entidades.size(); i++ ) {
			Entidade e = entidades.get(i);
			e.tick();
		}
		for(int i = 0; i < flechas.size(); i++) {
			flechas.get(i).tick();
		}
			} else {
				if (Game.estado_cena == Game.entrada) {
					if(player.getX() < -55) {
						player.x++;
					} else {
						Game.estado_cena = Game.comecar;
					}
				} else if (Game.estado_cena == Game.comecar) {
					timeCena++;
					if(timeCena == maxTimeCena) {
						Game.estado_cena = Game.jogando;
						timeCena = 0;
					}
				}
				}
				}

			if(Game.levelAtual == 2) {
				if (Game.estado_cena == Game.jogando) {
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
						player.y = 144;
						if(player.getX() < -55) {
							player.x++;
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
					}
			if(Game.levelAtual == 3) {
				player.y = 80;
				Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT);
				if (Game.estado_cena == Game.jogando) {
					
					for(int i=0; i <= Cutscene.vida; i++) {
						Cutscene.vida --;
						
					}
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
							if(player.getX()  < 190 ) {	
								if (player.getX()  > 0 && player.getX() < 150) {
								Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
								}
								if (player.getX()  > 150 && player.getX() < 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
								if (player.getX()  > 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
					
							player.x++;
							
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
			}	
			
			if(Game.levelAtual == 4) {
				if (Game.estado_cena == Game.jogando) {
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
						if(player.getX() < -55) {
							player.x++;
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
					}
			
			if(Game.levelAtual == 5) {
				player.y = 96;
				Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT);
				if (Game.estado_cena == Game.jogando) {
					for(int i=0; i <= Cutscene2.vida; i++) {
						Cutscene2.vida--;
					}
					for(int i=0; i <= Radu.vida; i++) {
						Radu.vida--;
					}
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
							if(player.getX()  < 190 ) {	
								if (player.getX()  > 0 && player.getX() < 150) {
								Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
								}
								if (player.getX()  > 150 && player.getX() < 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
								if (player.getX()  > 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
					
							player.x++;
							
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
			}
			
			if(Game.levelAtual == 6) {
				if (Game.estado_cena == Game.jogando) {
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
			for(int i = 0; i < flechas.size(); i++) {
				flechas.get(i).tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
						if(player.getX() < -55) {
							player.x++;
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
					}
			
			if(Game.levelAtual == 7) {
				player.y = 144;
				Camera.y = Camera.clamp(getY() - (Game.HEIGHT/2),0,World.HEIGHT * 16 - Game.HEIGHT);
				if (Game.estado_cena == Game.jogando) {
					for(int i=0; i <= Cutscene.vida; i++) {
						Cutscene.vida --;
						
					}
			for (int i=0; i < entidades.size(); i++ ) {
				Entidade e = entidades.get(i);
				e.tick();
			}
				} else {
					if (Game.estado_cena == Game.entrada) {
							if(player.getX()  < 190 ) {	
								if (player.getX()  > 0 && player.getX() < 150) {
								Camera.x = Camera.clamp(getX() - (Game.WIDTH/2),0,World.WIDTH * 16 - Game.WIDTH);
								}
								if (player.getX()  > 150 && player.getX() < 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
								if (player.getX()  > 190) {
									Camera.x = Camera.clamp(getX() +(Game.WIDTH),0,World.WIDTH * 16 - Game.WIDTH);
									}
					
							player.x++;
							
						} else {
							Game.estado_cena = Game.comecar;
						}
					} else if (Game.estado_cena == Game.comecar) {
						timeCena++;
						if(timeCena == maxTimeCena) {
							Game.estado_cena = Game.jogando;
							timeCena = 0;
						}
					}
					}
			}	

			if(inimigos.size() == 0  && cut.size() == 0 && cut2.size() == 0 && Game.levelAtual < 4) {
				// avançar para o prox level 
				levelAtual++;
				if(levelAtual > maxLevel ) {
					levelAtual = levelAtual ;
				}
				String novoMundo = "level" + levelAtual + ".png";
				World.reiniciarOJogo(novoMundo);
				Game.estado_cena = Game.entrada;
				timeCena = 0;
				timeCena++;
				}
				if(inimigos.size()  <=0  && boss.size() <= 0 && cut.size() == 0 && cut2.size() == 0 && Game.levelAtual >= 4 && Game.levelAtual < 6) {
					// avançar para o prox level 
					levelAtual++;
					if(levelAtual > maxLevel ) {
						levelAtual = levelAtual ;
					}
					String novoMundo = "level" + levelAtual + ".png";
					World.reiniciarOJogo(novoMundo);
					Game.estado_cena = Game.entrada;
					timeCena = 0;
					timeCena++;
					}
				if(inimigos.size() <= 0  && chefao.size() <= 0 && cut.size() == 0 && cut2.size() == 0 && Game.levelAtual >= 6 ) {
					// avançar para o prox level 
					levelAtual++;
					if(levelAtual > maxLevel ) {
						levelAtual = levelAtual ;
					}
					String novoMundo = "level" + levelAtual + ".png";
					World.reiniciarOJogo(novoMundo);
					Game.estado_cena = Game.entrada;
					timeCena = 0;
					timeCena++;
					}
			
		} else if (estadoDoJogo == "Game_Over" ) {
			this.framesGameOver ++;
			if (this.framesGameOver == 30) {
				this.framesGameOver = 0;
				if(this.mostrarMensagemGameOver) 
					this.mostrarMensagemGameOver = false;
					else 
						this.mostrarMensagemGameOver = true;
				
			} if(reiniciarOJogo) {
				reiniciarOJogo = false;
				estadoDoJogo = "Normal";
				levelAtual = levelAtual ;
				if (Game.levelAtual == 6) {
					
				}
				if (Game.levelAtual == 4) {
				
				}
				String novoMundo = "level" + levelAtual + ".png";
				World.reiniciarOJogo(novoMundo);
			}
		} else if (estadoDoJogo == "Menu") {
			menu.tick();
		}
		System.out.println(chefao.size());
		System.out.println(boss.size());
		System.out.println(inimigos.size());
		}
		
	
	
	public void render () {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//renderização do jogo
		world.render(g);
		for (int i=0; i < entidades.size(); i++ ) {
			Entidade e = entidades.get(i);
			e.render(g);
		}
		for(int i = 0; i < flechas.size(); i++) {
			flechas.get(i).render(g);;
		}
		ui.render(g);
		//fim
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE,HEIGHT*SCALE,null);
		g.setFont(new Font("arial",Font.BOLD,20));
		g.setColor(Color.white);
		g.drawString("Flechas: " + player.flechas , 600, 20);
		if (estadoDoJogo == "Game_Over") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial",Font.BOLD,40));
			g.setColor(Color.white);
			g.drawString("Game Over !!!", (WIDTH*SCALE) / 2 - 110, (HEIGHT*SCALE) /2 - 15);
			g.setFont(new Font("arial",Font.BOLD,30));
			if(mostrarMensagemGameOver)
			g.drawString("--> Para reiniciar o jogo aperte a tecla Enter <--", (WIDTH*SCALE) / 2 - 330, (HEIGHT*SCALE) /2 +40);
		} else if (estadoDoJogo == "Menu") {
			menu.render(g);
		}
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 1) {
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Instruções:", 30,50);
			g.drawString("1: Use W/A/S/D ou as teclas da seta para fazer o personagem andar", 50,95);
			g.drawString("2: Use a tecla espaço para atirar", 50,125);
			g.drawString("3: Se quiser pausar o jogo use a tecla ESC ou a tecla P", 50,155);
			g.drawString("4: Ao colidir com os objetos, esses objetos serão coletados", 50,185);
			g.drawString("5: Não deixe os inimigos chegarem perto, pois eles vão dar dano", 50,215);
			
			
		}
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 2) {
			maxTimeCena = 60*7;
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Dicas:", 80,50);
			g.drawString("1: Lia foi raptada encontre-a", 100,95);
			g.drawString("2: No caminho tem inimigos derrote eles", 100,125);
			g.drawString("3: Procure quem fez isso com a Lia.", 100,155);
		}
		
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 3 && timeCena > 60*2) {
			maxTimeCena = 60*6;
			g.setColor(Color.black);
			g.fillOval(120,120, 450,70);
			g.setFont(new Font("Arial",Font.BOLD,15));
			g.setColor(Color.white);
			g.drawString("Anahí encontra lia ",260,150 );	
		}
		
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 4) {
			maxTimeCena = 60*7;
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Dicas:", 100,50);
			g.drawString("1: Anahí e Lia avistam algo", 100,95);
			g.drawString("2: Radu está queimando a floresta", 100,125);
			g.drawString("3: Derrote-o.", 100,155);
		}
				
				if(Game.estado_cena == Game.comecar && Game.levelAtual == 5 ){
					maxTimeCena = 60*10;
					g.setColor(Color.black);
					g.fillOval(70,100, 600,90);
					g.setFont(new Font("Arial",Font.BOLD,20));
					g.setColor(Color.white);
					g.drawString("- Obrigada por me salvar... Estou de volta, Anahí", 115,140);
					g.drawString("- Meu pai Luke estava me manipulando", 115,165);
				}
				
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 6){
			maxTimeCena = 60*7;
			g.setColor(Color.black);
			g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.setColor(Color.white);
			g.drawString("Dicas:", 100,50);
			g.drawString("1: Luke aparece", 100,95);
			g.drawString("2: Lia percebe que foi esse homem que a raptou", 100,125);
			g.drawString("3: Derrote-o e salve a floresta.", 100,155);
		}
		if(Game.estado_cena == Game.comecar && Game.levelAtual == 7) {
			maxTimeCena = 60*4;
		}
		bs.show();
				
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta > 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer+=1000;
			}
		}
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			// mover para direita pressionando a seta pra direita ou a tecla D
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			//mover para esquerda pressionando a seta da esquerda ou a tecla A
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			// mover para cima pressionando a seta pra cima ou a tecla W
		}	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			//mover para baixo pressionando a seta pra baixo ou a tecla S
		}	
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			// selecionar a opção do menu com o enter
			reiniciarOJogo = true;
			if(estadoDoJogo == "Menu") {
				menu.enter = true;
			}
			if (Game.levelAtual == 7)
				System.exit(1);
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P ) {
			// entrar na pausa
			estadoDoJogo = "Menu";
			menu.pausa = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			// parar de mover para direita quando não pressionar a seta pra direita ou a tecla D
			player.right = false;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			//parar de mover para esquerda quando não pressionar a seta da esquerda ou a tecla A
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			// parar de mover para cima quando não pressionar a seta pra cima ou a tecla W
			player.up = false;
			if (estadoDoJogo == "Menu") {
				menu.up = true;
			}
		}	else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			//parar de mover para baixo quando não pressionar a seta pra baixo ou a tecla S
			player.down = false;
			if (estadoDoJogo == "Menu") {
				menu.down = true;
			}
			
		} if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			// aperta o espaço para começar atirar  S
			player.tiro =true;
		}
		
	}

}

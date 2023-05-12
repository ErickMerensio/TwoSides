package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entidades.Arco;
import entidades.Cutscene;
import entidades.Cutscene2;
import entidades.Radu;
import entidades.Entidade;
import entidades.Lia;
import entidades.Flechas;
import entidades.Inimigo;
import entidades.PacoteDeVida;
import entidades.PaiDoRadu;
import entidades.Player;
import game.Game;
import graficos.Spritesheet;

public class World {
	
		public static Tile[] tiles;
		public static  int WIDTH,HEIGHT;
		public static final int Tile_Size = 16;
	
		public World (String path) {
			try {
				BufferedImage map = ImageIO.read(getClass().getResource(path));
				int[] pixels = new int[map.getWidth() * map.getHeight()];
				WIDTH = map.getWidth();
				HEIGHT = map.getHeight();
				tiles = new Tile[map.getWidth() * map.getHeight()];
				map.getRGB(0, 0, map.getWidth(), map.getHeight() ,pixels,0,map.getWidth());
				
				// looping para rodar cada posição do mapa e reconhecer as cores para representar itens,chão e outra coisas
				for (int xx =0; xx < map.getWidth(); xx++) {
					for(int yy =0; yy < map.getHeight(); yy++) {
						int pixelAtual = pixels[xx + (yy*map.getWidth())];
						tiles[xx + (yy*WIDTH)] = new ChaoTile(xx*16,yy*16,Tile.Tile_Padrao)  ;
							
							if(pixelAtual == 0xFF000000 ) {
								tiles[xx + (yy*WIDTH)] = new ChaoTile(xx*16,yy*16,Tile.Tile_Chao);
								// pixel do chão
							}else if (pixelAtual == 0xFF76428a ){	
								// pixel do chão queimado
									tiles[xx + (yy*WIDTH)] = new ChaoTile(xx*16,yy*16,Tile.Tile_ChaoQueimado);
							}
						else if (pixelAtual == 0xFFFFFFFF) {
							// pixel da parede
							tiles[xx + (yy*WIDTH)] = new ParedeTile(xx*16,yy*16,Tile.Tile_Parede);
						}else if (pixelAtual == 0xFF8a6f30)	 {
							//pixel da parede com fogo
							tiles[xx + (yy*WIDTH)] = new ParedeTile(xx*16,yy*16,Tile.Tile_ParedeQueimada);
						}else if (pixelAtual == 0xFF5fcde4) {
							// pixel do personagem
							Game.player.setX(xx*16);
							Game.player.setY(yy*16);
						}else if (pixelAtual == 0xFFac3232) {
							//pixel dos inimigos
							BufferedImage[] buf = new BufferedImage[2];
							buf[0] = Game.spritesheet.getSprite(96, 16, 16, 16);
							buf[1] = Game.spritesheet.getSprite(112, 16, 16, 16);
							Inimigo inimigo = new Inimigo(xx*16,yy*16,16,16,null);
							Game.entidades.add(inimigo);
							Game.inimigos.add(inimigo);
						}else if (pixelAtual == 0xFFdf7126) {
							// pixel do arco
							Game.entidades.add(new Arco(xx*16,yy*16,16,16,Entidade.Arco_Entidade));
						}else if (pixelAtual == 0xFFac0c1f) {
							//pixel do pacote de vida
							Game.entidades.add(new PacoteDeVida(xx*16,yy*16,16,16,Entidade.PacoteDeVida_Entidade));
						}else if (pixelAtual == 0xFFfbf236) {
							// pixel das flechas
							Game.entidades.add(new Flechas(xx*16,yy*16,16,16,Entidade.Flecha_Entidade));
						}else if (pixelAtual == 0xFFd77bba) {
							//pixel do radu
							BufferedImage[] buf = new BufferedImage[1];
							buf[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
							Radu boss = new Radu(xx*16,yy*16,16,16,null);
							Game.entidades.add(boss);
							Game.boss.add(boss);
						}else if (pixelAtual == 0xFF09fa04) {
							//pixel do paidoradu
							BufferedImage[] buf = new BufferedImage[1];
							buf[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
							PaiDoRadu chefao = new PaiDoRadu(xx*16,yy*16,16,16,null);
							Game.entidades.add(chefao);
							Game.chefao.add(chefao);
						}else if (pixelAtual == 0xFFff28ff) {
							//pixel da Lia
							BufferedImage[] buf = new BufferedImage[1];
							buf[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
							Lia gatinha = new Lia(xx*16,yy*16,16,16,null);
							Game.entidades.add(gatinha);
							Game.lia.add(gatinha);
						}else if (pixelAtual == 0xFFe5ba2e) {
							//cutscene
							BufferedImage[] buf = new BufferedImage[1];
							buf[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
							if ( Game.levelAtual == 3) {
							Cutscene tempo = new Cutscene(xx*16,yy*16,16,16,null);
							Game.entidades.add(tempo);
							Game.cut.add(tempo);
							}
						}else if (pixelAtual == 0xFF44e3b9) {
							//cutscene
							BufferedImage[] buf = new BufferedImage[1];
							buf[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
							Cutscene2 tempo2 = new Cutscene2(xx*16,yy*16,16,16,null);
							Game.entidades.add(tempo2);
							Game.cut2.add(tempo2);
						}	
					else if (pixelAtual == 0xFF44e3b9) {
						//cutscene 2
						BufferedImage[] buf = new BufferedImage[1];
						buf[0] = Game.spritesheet.getSprite(0, 96, 16, 16);
						Cutscene2 tempo2 = new Cutscene2(xx*16,yy*16,16,16,null);
						Game.entidades.add(tempo2);
						Game.cut2.add(tempo2);
					}	
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//verificar se vai colidir com algo
		public static boolean isFree(int xnext, int ynext) {
			int x1 = xnext / Tile_Size;
			int y1 = ynext / Tile_Size;
			
			int x2 = (xnext + Tile_Size - 1) / Tile_Size;
			int y2 = ynext / Tile_Size;
			
			int x3 = xnext  / Tile_Size;
			int y3 = (ynext + Tile_Size - 1) / Tile_Size;
			
			int x4 = (xnext + Tile_Size - 1) / Tile_Size;
			int y4 = (ynext + Tile_Size - 1) / Tile_Size;
			
			return !((tiles[x1 + (y1*World.WIDTH)] instanceof ParedeTile) 
					|| (tiles[x2 + (y2*World.WIDTH)] instanceof ParedeTile)
					|| (tiles[x3 + (y3*World.WIDTH)] instanceof ParedeTile)
					|| (tiles[x4 + (y4*World.WIDTH)] instanceof ParedeTile ));
		}
		
		public static void reiniciarOJogo (String level) {
			Game.entidades = new ArrayList<Entidade>();
			Game.inimigos = new ArrayList<Inimigo>();
			Game.spritesheet = new Spritesheet("/spritesheeet.png");
			Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(0, 0, 16, 16));
			Game.entidades.add(Game.player);
			Game.world = new World("/" + level);
			return;
		}

		public void render(Graphics g) {
			
			int xstart = Camera.x/16;
			int ystart = Camera.y/16;
			int xfinal = xstart + (Game.WIDTH/16);
			int yfinal = ystart + (Game.HEIGHT/16);
			
			for(int xx = xstart; xx <= xfinal; xx++) {
				for(int yy = ystart; yy <= yfinal; yy++) {
					if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
						continue;
					}
					Tile tile = tiles[xx + (yy*WIDTH)];
					tile.render(g);
				}
			}
		}
}

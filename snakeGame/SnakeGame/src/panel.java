import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;


import javax.swing.JPanel;

public class panel extends JPanel implements ActionListener{
	static int width = 1200;
	static int height = 600;
	static int unit = 50;
	//in specify time in which new frame is displayed
	Timer timer;
	//deciding food location randomly
	Random random;
	int foodx, foody;
	
	int score;
	int length = 3;
	
	char dir = 'R';
	
	//to specify if the game has ended or not
	boolean flag = false;
	static int Delay = 160;
	
	// to store x and y coordinates of the snake
	int xsnake[] = new int[288];
	int ysnake[] = new int[288];
	
	panel() {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		
		//this command allows keyboard input to be processed
		this.setFocusable(true);
		random = new Random();
		//creating abstract class for input processing
		this.addKeyListener(new mykey());
		
		gamestart();
		
	}
	public void gamestart() {
		spawnfood();
		flag = true;
		timer = new Timer(Delay, this);
		timer.start();
		
	}
	
	public void spawnfood() {
		foodx = random.nextInt(width/unit) * unit;
		foody = random.nextInt(width/unit) * unit;
	}
	
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		draw(graphic);
	}
	
	public void draw(Graphics graphic) {
		if(flag == true) {
			graphic.setColor(Color.RED);
			graphic.fillOval(foodx, Delay, unit, unit);
			
			for(int i = 0; i < length; i++) {
				if(i == 0) {
					graphic.setColor(Color.ORANGE);
				}
				else {
					graphic.setColor(Color.GREEN);
				}
				graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
			}
			
			graphic.setColor(Color.CYAN);
			graphic.setFont(new Font("comic sans", Font.BOLD, 40));
			FontMetrics f = getFontMetrics(graphic.getFont());
			graphic.drawString("Score:"+score, (width-f.stringWidth("Score:"+score))/2, graphic.getFont().getSize());
		}
		else {
			gameover(graphic);
		}
	}
	public void gameover(Graphics graphic) {
		graphic.setColor(Color.CYAN);
		graphic.setFont(new Font("comic sans", Font.BOLD, 40));
		FontMetrics f = getFontMetrics(graphic.getFont());
		graphic.drawString("Score:"+score, (width-f.stringWidth("Score:"+score))/2, graphic.getFont().getSize());
		
		graphic.setColor(Color.CYAN);
		graphic.setFont(new Font("comic sans", Font.BOLD, 80));
		FontMetrics f2 = getFontMetrics(graphic.getFont());
		graphic.drawString("Score:"+score, (width-f2.stringWidth("GAME OVER!"))/2, height/2 );
		
		graphic.setColor(Color.CYAN);
		graphic.setFont(new Font("comic sans", Font.BOLD, 40));
		graphic.drawString("Press R to replay", (width-f.stringWidth("Press R to replay"))/2, height/2 + 150);
		
	}
	
	public void checkhit() {
		if(xsnake[0] < 0) {
			flag = false;
		}
		else if(xsnake[0] > 1200) {
			flag = false;
		}
		else if(ysnake[0] < 0) {
			flag = false;
		}
		else if(ysnake[0] > 600) {
			flag = false;
		}
		
		for(int i = length; i > 0; i--) {
			if((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
				flag = false;
			}
		}
		
		if(!flag) {
			timer.stop();;
		}
	}
	
	public void eat() {
		if((xsnake[0] == foodx) && (ysnake[0] == foody)) {
			length++;
			score++;
			spawnfood();
		}
	}
	
	public void move() {
		//updating all the body parts except the head
		for(int i = length; i > 0; i --) {
			xsnake[i] = xsnake[i-1];
			ysnake[i] = ysnake[i-1];
			
		}
		switch(dir) {
		case 'R':
			xsnake[0] = xsnake[0] + unit;
			break;
			
		case 'L':
			xsnake[0] = xsnake[0] - unit;
			break;
			
		case 'D':
			ysnake[0] = ysnake[0] + unit;
			break;
			
		case 'U':
			ysnake[0] = ysnake[0] - unit;
			break;
		
			}
	}
	
	public class mykey extends KeyAdapter{
		public void keyPressed(KeyEvent evt) {
			switch(evt.getKeyCode()) {
			case KeyEvent.VK_UP:
				if(dir != 'D') {
					dir = 'U';
				}
				break;
				
			case KeyEvent.VK_DOWN:
				if(dir != 'U') {
					dir = 'D';
				}
				break;
				
			case KeyEvent.VK_LEFT:
				if(dir != 'R') {
					dir = 'L';
				}
				break;
				
			case KeyEvent.VK_RIGHT:
				if(dir != 'L') {
					dir = 'R';
				}
				break;
				
			case KeyEvent.VK_R:
				if(!flag) {
					score = 0;
					length = 3;
					dir = 'R';
					Arrays.fill(xsnake, 0);
					Arrays.fill(ysnake, 0);
					
					gamestart();
				}
				break;
			}
		}
	}
	
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		if(flag) {
			move();
			eat();
			checkhit();
		}
		repaint();
	}
}

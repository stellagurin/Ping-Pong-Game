import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.Timer;

public class TennisPanel extends JPanel implements ActionListener, KeyListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean showTitleScreen = true;
    private boolean playing = false;
    private boolean gameOver = false;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private int ballX = 300;
    private int ballY = 300;
    private int diameter = 20;
    private int ballDeltaX = -1;
    private int ballDeltaY = 3;

    private int playerOneX = 5;
    private int playerOneY = 200;
    private int playerOneWidth = 10;
    private int playerOneHeight = 50;

    private int playerTwoX = 767;
    private int playerTwoY = 200;
    private int playerTwoWidth = 10;
    private int playerTwoHeight = 50;

    private int paddleSpeed = 5;

    private int playerOneScore = 0;
    private int playerTwoScore = 0;


    //construct a PongPanel
    public TennisPanel(){
        setBackground(Color.decode("#007b00"));

        //listen to key presses
        setFocusable(true);
        addKeyListener(this);

        //call step() 60 fps
        Timer timer = new Timer(60/60, this);
        timer.start();
    }


    public void actionPerformed(ActionEvent e){
        step();
    }

    public void step(){
        if(playing){
            //move player 1
            if (upPressed) {
                if (playerOneY-paddleSpeed > 0) {
                    playerOneY -= paddleSpeed;
                }
            }
            if (downPressed) {
                if (playerOneY + paddleSpeed + playerOneHeight < getHeight()) {
                    playerOneY += paddleSpeed;
                }
            }

            //move player 2
            if (wPressed) {
                if (playerTwoY-paddleSpeed > 0) {
                    playerTwoY -= paddleSpeed;
                }
            }
            if (sPressed) {
                if (playerTwoY + paddleSpeed + playerTwoHeight < getHeight()) {
                    playerTwoY += paddleSpeed;
                }
            }



            //where will the ball be after it moves?
            int nextBallLeft = ballX + ballDeltaX;
            int nextBallRight = ballX + diameter + ballDeltaX;
            int nextBallTop = ballY + ballDeltaY;
            int nextBallBottom = ballY + diameter + ballDeltaY;

            int playerOneRight = playerOneX + playerOneWidth;
            int playerOneTop = playerOneY;
            int playerOneBottom = playerOneY + playerOneHeight;

            float playerTwoLeft = playerTwoX;
            float playerTwoTop = playerTwoY;
            float playerTwoBottom = playerTwoY + playerTwoHeight;


            //ball bounces off top and bottom of screen
            if (nextBallTop < 0 || nextBallBottom > getHeight()) {
                ballDeltaY *= -1;
            }

            //will the ball go off the left side?
            if (nextBallLeft < playerOneRight) { 
                //is it going to miss the paddle?
                if (nextBallTop > playerOneBottom || nextBallBottom < playerOneTop) {

                    playerTwoScore ++;

                    if (playerTwoScore == 3) {
                        playing = false;
                        gameOver = true;
                    }

                    ballX = 250;
                    ballY = 250;
                }
                else {
                    ballDeltaX *= -1;
                }
            }

            //will the ball go off the right side?
            if (nextBallRight > playerTwoLeft) {
                //is it going to miss the paddle?
                if (nextBallTop > playerTwoBottom || nextBallBottom < playerTwoTop) {

                    playerOneScore ++;

                    if (playerOneScore == 3) {
                        playing = false;
                        gameOver = true;
                    }

                    ballX = 250;
                    ballY = 250;
                }
                else {
                    ballDeltaX *= -1;
                }
            }

            //move the ball
            ballX += ballDeltaX;
            ballY += ballDeltaY;
        }

        //stuff has moved, tell this JPanel to repaint itself
        repaint();
    }

    //paint the game screen
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        g.setColor(Color.decode("#340300"));

        if (showTitleScreen) {
        	g.setColor(Color.decode("#ffffff")); 

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
       //     g.drawOval(0, 0, getWidth(), getHeight());
            g.drawString("Stella Ping Pong Cup", 210, 225);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));

            g.drawString("Press 'P' to play.", 320, 400);
        }
        else if (playing) {


            //draw dashed line down center
            for (int lineY = 0; lineY < getHeight(); lineY += 50) {
            	g.setColor(Color.decode("#ffffff"));
                g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
            }

            
            //draw "goal lines" on each side
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
            g2.draw(new Line2D.Float(0, 0, 0, getHeight()));
            
            Graphics2D g3 = (Graphics2D) g;
            g3.setStroke(new BasicStroke(10));
            g3.draw(new Line2D.Float(getWidth(), 0, getWidth(), getHeight()));

            Graphics2D g4 = (Graphics2D) g;
            g4.setStroke(new BasicStroke(10));
            g4.draw(new Line2D.Float(0, 0, getWidth(), 0));
            
            Graphics2D g5 = (Graphics2D) g;
            g5.setStroke(new BasicStroke(10));
            g5.draw(new Line2D.Float(0, getHeight(), getWidth(), getHeight()));
            
            Graphics2D g6 = (Graphics2D) g;
            g6.setStroke(new BasicStroke(1));
            g6.draw(new Line2D.Float(0, getHeight()/2, getWidth(), getHeight()/2));
            


            
            
            g.setColor(Color.decode("#340300"));

            //draw the scores
        	g.setColor(Color.decode("#ffffff"));
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString(String.valueOf(playerOneScore), getWidth()/4, 100);
            g.drawString(String.valueOf(playerTwoScore), (3 * getWidth())/4, 100);



            //draw the paddles
        	g.setColor(Color.decode("#FF4040"));
            g.fillRect(playerOneX, playerOneY, playerOneWidth, playerOneHeight);
        	g.setColor(Color.decode("#62B1F6"));
            g.fillRect(playerTwoX, playerTwoY, playerTwoWidth, playerTwoHeight);
            
            g.setColor(Color.decode("#340300"));
            //draw the ball
            
            try {
                BufferedImage img = ImageIO.read(new File("ping_pong.png"));
                g.drawImage(img, ballX, ballY, 20, 20, this);
            } catch (Exception ex){
                ex.printStackTrace();
            }
            
            // g.fillOval(ballX, ballY, diameter, diameter);
        }
        else if (gameOver) {
        	g.setColor(Color.decode("#ffffff"));

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            g.drawString(String.valueOf(playerOneScore), getWidth()/4, 100);
            g.drawString(String.valueOf(playerTwoScore), (3 * getWidth())/4, 100);

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 36));
            if (playerOneScore > playerTwoScore) {
                g.drawString("Player 1 Wins!", 275, 200);
            }
            else {
                g.drawString("Player 2 Wins!", 275, 200);
            }

            g.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
            g.drawString("Press space to restart.", 300, 400);
        }
    }



    public void keyTyped(KeyEvent e) {}



    public void keyPressed(KeyEvent e) {
        if (showTitleScreen) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                showTitleScreen = false;
                playing = true;
            }
        }
        else if(playing){
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = true;
            }
            else if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = true;
            }
        }
        else if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                gameOver = false;
                showTitleScreen = true;
                playerOneY = 200;
                playerTwoY = 200;
                ballX = 300;
                ballY = 300;
                playerOneScore = 0;
                playerTwoScore = 0;
            }
        }
    }


    public void keyReleased(KeyEvent e) {
        if (playing) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_W) {
                wPressed = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_S) {
                sPressed = false;
            }
        }
    }

}

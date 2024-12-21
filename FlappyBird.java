import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;
public class FlappyBird extends JPanel implements ActionListener,KeyListener {
    int boardwidth=360;
    int boardheight=640;
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    //bird
    int birdX=boardwidth/8;
    int birdY=boardheight/2;
    int birdwidth=34;
    int birdheight=24;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e){
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY =- 9;
            if(gameover){
                bird.y=birdY;
                velocityY=0;
                pipes.clear();
                score=0;
                gameover=false;
                gameLoop.start();
                placePipesTimer.start();
            }
            }
      
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    class Bird{
        int x=birdX;
        int y=birdY;
        int width=birdwidth;
        int height=birdheight;
        Image img;
        Bird(Image img){
            this.img=img;
        }
    }
    //pipes
    int pipeX=boardwidth;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeight=512;
    class Pipe{
        int x=pipeX;
        int y=pipeY;
        int width=pipeWidth;
        int height=pipeHeight;
        Image img;
        boolean passed=false;
        Pipe(Image img){
            this.img=img;
        }
    }
 
//gamelogic
Bird bird;
int velocityX=-4;
int velocityY=0;
int gravity=1; 
ArrayList<Pipe> pipes;
Random random=new Random();
Timer gameLoop;
Timer placePipesTimer;
boolean gameover=false;
double score=0;
    FlappyBird(){
        setPreferredSize(new Dimension(boardwidth,boardheight));
       // setBackground(Color.BLUE);
        setFocusable(true);
        addKeyListener(this);
        //load image
        backgroundImg=new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg=new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg=new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg=new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        //bird
        bird=new Bird(birdImg);
        pipes=new ArrayList<Pipe>();
        //place pipes times
        placePipesTimer=new Timer(1500,new ActionListener(){
            @ Override
            public void actionPerformed(ActionEvent e){
                placePipes();
            }
        });
        placePipesTimer.start();
        //game timer
        gameLoop=new Timer(1000/60,this);//1000/60=16.0
        gameLoop.start();


    }
    public void placePipes(){
        //(0-1)* pipeheight/2-->(0-256)
        int randomPipeY=(int) (pipeY-pipeHeight/4-Math.random()*(pipeHeight/2));
        int openingSpace=boardheight/4;
        Pipe topPipe=new Pipe(topPipeImg);
        topPipe.y=randomPipeY;
        pipes.add(topPipe);
        Pipe bottomPipe=new Pipe(bottomPipeImg);
        bottomPipe.y=topPipe.y+pipeHeight+openingSpace;
        pipes.add(bottomPipe);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
       
        //background
        g.drawImage(backgroundImg,0,0,boardwidth,boardheight,null);   
    //bird
    g.drawImage(bird.img,bird.x,bird.y,bird.width,bird.height,null);
    for(int i=0;i<pipes.size();i++){
        Pipe pipe=pipes.get(i);
        g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
    }
//score
g.setColor(Color.white);
g.setFont(new Font("Arial",Font.PLAIN,32));
if(gameover){
    g.drawString("Game Over: "+ String.valueOf((int) score),10,35);
}else{
    g.drawString(String.valueOf((int) score),10,35);
}
    }
    public void move(){
        velocityY += gravity;
        bird.y += velocityY;
        bird.y=Math.max(bird.y,0);
    
    //pipes
    for(int i=0;i<pipes.size();i++){
        Pipe pipe=pipes.get(i);
        pipe.x+=velocityX;
        if(!pipe.passed && bird.x>pipe.x +pipe.width){
            pipe.passed=true;
            score+=0.5;
        }
        if(collision(bird,pipe)){
            gameover=true;
        }
    }
    if(bird.y>boardheight){
        gameover=true;
    }
}
public boolean collision(Bird a,Pipe b){
    return a.x<b.x+b.width &&
    a.x+a.width>b.x &&
    a.y<b.y +b.height &&
    a.y +a.height >b.y;


}
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameover){
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
    }

 
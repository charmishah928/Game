//Charmi Shah
//Welcome to Scissor Eating Madness! In this game, you will be a scissor and your goal is to get
//as many orange papers as you can. This will be your "Get" score displayed on the top. But make sure to
//avoid any rocks in your way. This will be your "Avoid" score displayed on the top. Once your "Avoid" 
//reaches to 50, the game is over.

import javax.swing.JOptionPane;
public class Game
{
    private Grid grid;
    private int userRow;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;
    public Game()
    {
        grid = new Grid(5, 10);
        userRow = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, 0), "user1.gif");
    }
    public void play()
    {
        while (!isGameOver())
        {
            grid.pause(100);
            handleKeyPress();
            if(msElapsed % 300 == 0)
            {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
    }

    public void handleKeyPress()
    {
        grid.setImage(new Location(userRow,0),null);
        int key = grid.checkLastKeyPressed();
        if(key==38&&userRow>0)
            userRow--;
        else if(key==40&&userRow<grid.getNumRows()-1)
            userRow++;
        handleCollision(new Location(userRow,0));
        grid.setImage(new Location(userRow,0),"user1.gif");
    }
    
    public void populateRightEdge()
    {
        int get=(int)(Math.random()*grid.getNumRows());
        int avoid=(int)(Math.random()*grid.getNumRows());
        grid.setImage(new Location(get, grid.getNumCols()-1),"get1.gif");
        int abc1 = (int) (Math.random()*grid.getNumRows());
        grid.setImage(new Location(abc1, grid.getNumCols()-1),"avoid1.gif");
    }

    public void scrollLeft()
    {
        for(int r=0;r<grid.getNumRows();r++)
        {
            handleCollision(new Location(userRow,0));
            scrollLeftHelper(r);           
        }    
    }
    
    public void scrollLeftHelper(int r)
    {
        for(int c=0;c<grid.getNumCols();c++)
        {
            String image=grid.getImage(new Location(r,c));
            if(image!=null)
            {
                if(!(image.equals("user1.gif"))&&c>0)
                {
                    grid.setImage(new Location(r,c),null);
                    grid.setImage(new Location(r,c-1),image);
                }
                else if(!(image.equals("user1.gif"))&&c==0)
                {
                    grid.setImage(new Location(r,c),null);
                }
            }
        }    
    }        
    
    public void handleCollision(Location loc)
    {
        if(grid.getImage(loc)!=null)
        {
            if(grid.getImage(loc).equals("get1.gif"))
            {
                timesGet++;
                grid.setImage(loc,null);
            }
            else if(grid.getImage(loc).equals("avoid1.gif"))
            {
                timesAvoid++;
                grid.setImage(loc,null);
            }
        }    
    }
    
    public int getScore()
    {
        if(timesGet<0)
            return 0;
        else
            return timesGet;
    }
    
    public void updateTitle()
    {
        grid.setTitle("Get: "+timesGet+" Avoid: "+timesAvoid);    
    }

    public boolean isGameOver()
    {
        boolean result=false;
        if(timesAvoid==50)
        {
            result=true;
            JOptionPane.showConfirmDialog(grid, "Game Over!\nScore: "+getScore(), "NEW MESSAGE!", JOptionPane.OK_CANCEL_OPTION);
        }
        return result;   
    }
    
    public static void test()
    {
        Game game = new Game();
        game.play();
    }
    
    public static void main(String[] args)
    {
        JOptionPane.showConfirmDialog(null,"WELCOME TO SCISSOR EATING MADNESS!\nPress Ok or Cancel to play!", "Scissor Eatig Madness!", JOptionPane.OK_CANCEL_OPTION);
        test();
        Game.test();  
    }
}
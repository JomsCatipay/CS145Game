

public class Player{
    // PLAYER INFO
    int x,y;

    public Player(){

    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){ return x; }
    public int getY(){ return y; }
    
    public void move(String s, GridMap map){
        if(s.equals("left")
            && x>0
            && map.getCell(x-1, y)!=1) x-=1;
        else if(s.equals("right") 
            && x<map.getWidth()-1
            && map.getCell(x+1, y)!=1) x+=1;
        else if(s.equals("up")
            && y>0
            && map.getCell(x, y-1)!=1) y-=1;
        else if(s.equals("down") 
            && y<map.getHeight()-1
            && map.getCell(x, y+1)!=1) y+=1;
    }	

    public Player kill(){
        //cleanup
        return null;
    }
}
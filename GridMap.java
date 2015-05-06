import java.io.Serializable;

public class GridMap implements Serializable{
	private String id;

	private int width, height;
	private int grid[][];

	public GridMap(int w, int h){
		this.width = w;
		this.height = h;

		grid = new int[w][h];
	}

	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int getCell(int r, int c){ return grid[r][c]; }

	public void changeCell(int r, int c, int s){ grid[r][c] = s; }
}
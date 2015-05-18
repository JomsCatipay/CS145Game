
public class ACard extends Card{
	static int COUNT=0;
	int id;

	public ACard(String in){
		this.value = in;
		this.id = COUNT;
		COUNT++;
		setImage(0);
	}

	public int getID(){ return id;}
}
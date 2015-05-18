
public class QCard extends Card{
	static int COUNT=0;
	int id;

	public QCard(String in){
		this.value = in;
		this.id = COUNT;
		COUNT++;
		setImage(1);
	}

	public int getID(){ return id;}
}
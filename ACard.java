public class ACard extends Card{
	static int COUNT=0;
	int id;

	public ACard(String in){
		this.value = in;

		this.id = COUNT;
		COUNT++;
	}

	public int getID(){ return id;}
}
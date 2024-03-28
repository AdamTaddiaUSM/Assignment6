package accidentpack;

//all of the date and calendar classes I could find are so damn awful that I need to make my own. Sad.
public class MyDate implements Comparable<MyDate>
{
	public MyDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	public int year;
	public int month;
	public int day;
	
	public int compareTo(MyDate md) {
		if (this.year != md.year) {
			return this.year - md.year;
		} else if (this.month != md.month) {
				return this.month - md.month;
		} else if (this.day != md.day) {
			return this.day - md.day;
		} else {
			return 0;
		}
	}

}

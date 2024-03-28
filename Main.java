package accidentpack;
import java.util.TreeMap;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
		
	File f = new File("C:\\Users\\taddi\\Desktop\\Data Structures Class\\Assignment6\\assignment6\\src\\accidentpack\\accidents.csv"); 
	Scanner scnr = new Scanner(f);
	int r = findReportCount(scnr);
	scnr.close();
	
	Scanner scnr1 = new Scanner(f);
	scnr1.nextLine();
	TreeMap<String, TreeMap<MyDate, ArrayList<Report>>> tm = populateAccidents(scnr1, r);

	int accidentCount = menuPrompt(tm);
	
	}
	
	public static int menuPrompt(TreeMap<String, TreeMap<MyDate, ArrayList<Report>>> tm) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Enter the state where you would like to see accident info from, in the two letter format: ");
		String state = scnr.next();
		System.out.println("Enter the date (YYYY/MM/DD, including slashes) where you would like to see all accidents on and after that date: ");
		String dateString = scnr.next();
		int year = Integer.parseInt(dateString.substring(0, 4));
		int month = Integer.parseInt(dateString.substring(5, 7));
		int day = Integer.parseInt(dateString.substring(8, 10));
		MyDate dateForAccidents = new MyDate(year, month, day);
		
		TreeMap<MyDate, ArrayList<Report>> tree = tm.get(state);
		int accidentCount = accidentsAfterOrOnDate(tree, dateForAccidents);
		System.out.println("There are " + accidentCount + " accidents recorded on or after " + dateString + " in " + state);
		return accidentCount;
		
	}
	
	public static int accidentsAfterOrOnDate(TreeMap<MyDate, ArrayList<Report>> tree, MyDate d) {
		long startTime = System.nanoTime();
		int accidentCount = 0;
		for(Map.Entry<MyDate,ArrayList<Report>> entry: tree.entrySet()) {
			if (d.compareTo(entry.getKey()) <= 0) {//if the date of this accident list is on or after the specified date
				accidentCount += entry.getValue().size();
			}
		}
		long endTime = System.nanoTime();
		long lengthMicroSeconds = (endTime - startTime)/(1000);
		System.out.println(lengthMicroSeconds + " microseconds to find " + accidentCount + " entries in a treemap of treemaps.");
		return accidentCount;
	}
	
	public static int getAccidentsInList(ArrayList<Report> list) {
		int val = 0;
		for (Report r : list) {
			val ++;
		}
		
		return val;
	}

	public static TreeMap<String, TreeMap<MyDate, ArrayList<Report>>> populateAccidents(Scanner scnr, int reportCount){
		TreeMap<String, TreeMap<MyDate, ArrayList<Report>>> reportTree = new TreeMap<String, TreeMap<MyDate, ArrayList<Report>>>();
	     //a treemap, where every state has a nested treemap of type Date, Arraylist of reports
		
		long startTime = System.nanoTime(); //start of insertion time
		for (int i = 0; i < reportCount; i++) {
		Report r = Report.createReport(scnr);
		if (!(reportTree.containsKey(r.getState()))) {//if there is not already a treemap for the state
			System.out.println("No tree found for " + r.getState() + ". Making new tree.");
		reportTree.put(r.getState(), new TreeMap<MyDate, ArrayList<Report>>());	
		}
		if (!(reportTree.get(r.getState()).containsKey(r.getDate()))) {//if there is not already a treemap for the date
			reportTree.get(r.getState()).put(r.getDate(), new ArrayList<Report>());
		}
		//adds the report to the arraylist for the appropriate state and date
		reportTree.get(r.getState()).get(r.getDate()).add(r);
	}
		long endTime = System.nanoTime();
		long lengthMicroSeconds = (endTime - startTime)/(1000);
		System.out.println(lengthMicroSeconds + " microseconds to insert " + reportCount + " entries into treemap of treemaps.");
		return reportTree;
	
	}
	
	public static int findReportCount(Scanner scn){//passes the scanner as a parameter to get the number of reports
		int reportCount = -1;//starts at -1 to account for first line
		
		while(scn.hasNextLine()) {
			scn.nextLine();
			reportCount += 1;
		}
		
		return reportCount;
	}

}


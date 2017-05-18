import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
/*
 * Starbucks 
 * @Davey Morse
 * 
 * Questions:
 * 	1. Why does nick have high change of being randomly generated? not actually being abnormally picked--> his name is just repeated a lot on data sheet
 *  2. Why does number of wednesdays and nicks appear as 0?--> because it was sealed off from the main with brackets
 *  3. What is difference between .equals and ==? == is a pointer, asking if memory location is the same, .equals is asking if data is the same within those memory locations
 *  4. Why isn't the program calculating minutes now that i added in endminutes-startminutes? i fixed it
 *  
 * 
 *  BONUSES
 *  2. to answer question "who worked the most," this program answers both in terms of hours (dollars) and number of times
 *   My own three questions:
 *  1. Who works the most consistently?
 *  2. Who's banging who? Which two workers overlap the most time at work? (TAKES 8 SECONDS OR SO TO LOAD)
 *  3. How many weeks are covered on the datasheet?
 *
 */

public class StarbucksTestClassFinaleDM {
	static ArrayList<TimeSheet> l;

	/**
	 * finds the number of entries on which a given day appears
	 * @param day the given day of week that function is searching for
	 * @return thefrequency
	 */
	public static int frequencyDay (String day){
		int thefrequency = 0;
		for (int i = 0; i<l.size(); i++){
			if (l.get(i).getDay().equals(day)){
				thefrequency++;
			}
		}
		return thefrequency;
	}
	/**
	 * finds the number of entries in which a given name appears
	 * @param name the given name that function is searching for
	 * @return thefrequency
	 */
	public static int frequencyName (String name){
		int thefrequency = 0;
		for (int i = 0; i<l.size(); i++){
			if (l.get(i).getName().equals(name)){
				thefrequency++;
			}
		}
		return thefrequency;
	}

	/**
	 * determines the total hours worked for a given employee
	 * @param name the given name that function searches for
	 * @return totalhoursworked
	 */
	public static double determineTotalHoursWorked (String name){
		double totalhoursworked = 0;
		for (int i = 0; i<l.size(); i++){
			if (l.get(i).getName().equals(name)){
				totalhoursworked = totalhoursworked + l.get(i).determineHoursWorked();
			}
		}
		return totalhoursworked;
	}
	/**
	 * creates a list of all the names that appear in a given ArrayList of timesheets
	 * @param q ArrayList of Timesheets that function searches through to create list of names
	 * @return names
	 */
	public static ArrayList <String> createNamesList (ArrayList <TimeSheet> q){
		ArrayList <String> names = new ArrayList();
		boolean alreadyexists = false;
		//for every row
		outer:
			for (int i = 0; i<q.size(); i++){
				//for every name already in names
				for (int j = 0; j<names.size(); j++){
					//if name in row is equal to name in names, new row to look at
					if (q.get(i).getName().equals(names.get(j))){
						alreadyexists = true;
						continue outer;
					}
				}
				//otherwise, if it doesn't continue outer, add name to the array list
				names.add(q.get(i).getName());
			}
		//print out list of names just to check that they are not repeated but all in there
		System.out.println("List of all names in datasheet:");
		for (int i = 0; i<names.size(); i++){
			System.out.println("name " + (i+1) + ": " + names.get(i));
		}
		return names;
	}
	/**
	 * creates a list of the days that appear in a given arraylist of strings
	 * @param q Arraylist that function searches through to look for days
	 * @return days
	 */
	public static ArrayList <String> createDaysList (ArrayList <String> q){
		ArrayList <String> days = new ArrayList();
		boolean alreadyexists = false;
		//for every row
		outer:
			for (int i = 0; i<q.size(); i++){
				//for every name already in names
				for (int j = 0; j<days.size(); j++){
					//if name in row is equal to name in names, new row to look at
					if (q.get(i).equals(days.get(j))){
						alreadyexists = true;
						continue outer;
					}
				}
				//otherwise, if it doesn't continue outer, add name to the array list
				days.add(q.get(i));
			}
		//print out list of names just to check that they are not repeated but all in there
		for (int i = 0; i<days.size(); i++){
			System.out.println("name " + i + ": " + days.get(i));
		}
		return days;
	}

	/**
	 * finds highest value in an array (without using sort method (wrote it before i knew sort function exists))
	 * @param relations the double array of ints for which function is identifying highest value
	 * @return highest
	 */
	public static int findHighest (double[] relations){
		int highest = 0;
		for (int i = 0; i<relations.length; i++){
			//go through names, always keeping the highest number of hours seen so far (binary comparisons, only running through arraylist once)
			//most hours:
			if (relations[i]> relations[highest]){
				highest = i;
			}
		}
		return highest;
	}
	/**
	 * finds the lowest value in an array of doubles (also without sort function)
	 * @param intlist list of doubles in which function is identifying lowest
	 * @return lowest
	 */
	public static int findLowest(double [] intlist){
		int lowest = intlist.length-1;//this variable keeps track of highest number of hours index as we go through the hours and namesfrec arrays
		for (int i = 0; i<intlist.length; i++){
			if (intlist[i] < intlist[lowest]){
				lowest = i;
			}
		}
		return lowest;
	}

	/**
	 * finds the average number of hours worked per entry for a given employee
	 * @param name name function is finding information relevant to
	 * @return (determineTotalHoursWorked(name)/frequencyName(name))
	 */
	public static double findAverageHoursWorked(String name){
		return (determineTotalHoursWorked(name)/frequencyName(name));
	}

	/**
	 * finds the average differences between entries and the average hours per entry for a given employee
	 * @param names arraylist of names function is going through
	 * @return differencesbetweenaverage
	 */
	public static double [] findAverageDifferences(ArrayList <String> names){
		//find average hours worked for each employee
		double averagehoursworked [] = new double [names.size()];
		double [] differencesbetweenaverage = new double [names.size()];
		for(int i = 0; i<names.size(); i++){
			//find average hours worked
			averagehoursworked[i] = findAverageHoursWorked(names.get(i));
			for (int j = 0; j<l.size(); j++){
				if (l.get(i).getName().equals(names.get(i))){
					//find sum of differences
					differencesbetweenaverage[i]= differencesbetweenaverage[i] + Math.abs(l.get(i).determineHoursWorked() - averagehoursworked[i]);
				}
			}
		}
		for (int j = 0; j<differencesbetweenaverage.length; j++){
			differencesbetweenaverage[j] = differencesbetweenaverage[j]/frequencyName(names.get(j));
		}
		return differencesbetweenaverage;
	}

	/**
	 * finds the number of weeks in the arraylist
	 * @return weeks
	 */
	public static double numberweeks(){
		double weeks = 0;
		boolean justmonday = false;
		for (int i = 0; i<l.size(); i++){
			if (l.get(i).getDay().equals("Monday")){
				if (justmonday == false){
					weeks++;
				}
				justmonday = true;
			}
			else{
				justmonday = false;
			}
		}
		return weeks;
	}

	/**
	 * find the index of each weekbeginning in the datasheet
	 * @return weekbeginnings
	 */
	public static ArrayList<Integer> indexweeks(){
		boolean justmonday = false;
		ArrayList <Integer> weekbeginnings = new ArrayList <Integer> (); //keeps track of the number row of the first monday entry of each week
		for (int i = 0; i<l.size(); i++){
			if (l.get(i).getDay().equals("Monday")){
				if (justmonday == false){
					weekbeginnings.add(i);
				}
				justmonday = true;
			}
			else{
				justmonday = false;
			}
		}
		//add fake start week at end of weekbeginnings for counting purposes in for loops below
		weekbeginnings.add(l.size());
		return weekbeginnings;
	}

	/**
	 * finds the total overlap between every pairing of employees in work hours
	 * @param names list of names that function is finding overlaps in
	 * @return totoverlap
	 */
	public static double [][] findtotoverlaps(ArrayList <String> names)
	{
		System.out.println("entering...");

		ArrayList <String> firstnameoccurrences = new ArrayList <String>(); //arraylist of the DAYS within a specified week of the first name's occurrences
		ArrayList <Integer> indexfnocc = new ArrayList <Integer> (); //arraylist of Indexes/number row entries within a certain weeks data that a name occurs
		double [] overlap = new double [4];
		double [][] totoverlap = new double[names.size()][names.size()];
		//for each person 1:
		for(int i = 0; i<names.size(); i++){
			//for each week
			for (int k = 0; k<numberweeks(); k++){
				//for each entry in that week
				for(int c = 0; c<(indexweeks().get(k+1)-indexweeks().get(k)); c++){
					//if first name is there
					if(names.get(i) == l.get(c).getName()){
						firstnameoccurrences.add(l.get(c).getDay());
						indexfnocc.add(c);
					}
				}
				//again, for every day in that same week
				for(int c = 0; c<(indexweeks().get(k+1)-indexweeks().get(k)); c++)
				{
					// for each name 2 that is not name 1
					nametwo: for(int j = 0; j<names.size(); j++){
						if(j>i){
							//if name 2 is here
							if(names.get(j) == l.get(c).getName()){
								//go through each day that week that name 1 has appeared
								for(int d = 0; d<firstnameoccurrences.size(); d++){
									//if they share this day, 
									if(l.get(c).getDay().equals(firstnameoccurrences.get(d))){
										//find the times on that day that they overlap: identify index of first person's entry and of second person's
										totoverlap[i][j] = totoverlap[i][j] + findOverlap((l.get(c).getStarthour() + l.get(c).getStartminutes()/60), (l.get(c).getEndhour() + l.get(c).getEndminutes()/60), (l.get(indexfnocc.get(d)).getStarthour() + l.get(indexfnocc.get(d)).getStartminutes()/60), (l.get(indexfnocc.get(d)).getEndhour() + l.get(indexfnocc.get(d)).getEndminutes()/60));
										continue nametwo;
									}
								}
							}
						}
					}
				}
			}
			firstnameoccurrences.clear();
			indexfnocc.clear();
		}

		System.out.println("exiting...");
		return totoverlap;
	}

	/**
	 * finds overlap between two periods of time - elegance to the max
	 * @param Astart starting time for period A
	 * @param Aend ending time for period A
	 * @param Bstart starting time for period B	
	 * @param Bend ending time for period B
	 * @return overlap
	 */
	public static double findOverlap (double Astart, double Aend, double Bstart, double Bend){
		double overlap = 0;
		double [] times = new double []{Astart, Aend, Bstart, Bend};
		if(times[0]<times[3] && times[2]<times[1]){
			Arrays.sort(times);
			overlap = (times[2]- times[1]);
		}
		return overlap;
	}

	/**
	 * finds the highest value in a double array
	 * @param twodarray the two dimensional array that function is sorting through
	 * @return coord
	 */
	public static int [] findHighestinTwoD(double [][] twodarray){
		double temphighest = 0; //temporary highest value as going through the loop
		int [] coord = new int [2];

		for (int i= 0; i < twodarray.length; i++){
			for(int j = 0; j< twodarray[1].length; j++){
				if (temphighest<twodarray[i][j]){
					temphighest = twodarray[i][j];
					coord[0] = i;
					coord[1] = j;
				}
			}
		}
		return coord;
	}
	//finds sum of all money paid to employees with 7 dollar weekday, 9 dollar hour weekday
	/**
	 * finds the total salary money for the company
	 * @param option time of salary calculation
	 * @return totcompany
	 */
	public static double findTotalCompanyMoola (int option){
		double totcompmoney = 0;
		if (option==1){
			//for every entry
			for (int i = 0; i<l.size(); i++){
				totcompmoney = totcompmoney + l.get(i).payment(9);
			}
		}
		else if(option == 2){
			for (int i = 0; i<l.size(); i++){
				totcompmoney = totcompmoney + l.get(i).roundedUpTime()*9;
			}
		}
		else if (option== 3){
			for (int i = 0; i<l.size(); i++){
				totcompmoney = totcompmoney + l.get(i).fiveHrCall()*9;
			}
		}
		else if (option == 4){
			for (int i = 0; i<l.size(); i++){
				totcompmoney = totcompmoney + l.get(i).eightHrCap()*9;
			}
		}
		return totcompmoney;
	}

	/**
	 * finds the total salary money when doubleing the amount paid during offhours
	 * @return ((paymentcategories[1]*9*1.5)+(paymentcategories[0]*9))
	 * 
	 */
	public static double offHoursIncluded(){
		double [] paymentcategories = new double [2];//1 = weekday tot, 2 = weekday offhour tot, 3 = weekend tot, 4 = weekend offhour tot
		double [] rowcategs = new double[2];
		//go through all entries
		for (int i = 0; i<l.size(); i++){
			rowcategs = l.get(i).timesheetOffHoursIncluded();
			for(int j = 0; j<2; j++){
				paymentcategories[j] = paymentcategories[j] + rowcategs[j];
			}
		}
		//Calculate Total money
		return ((paymentcategories[1]*9*1.5)+(paymentcategories[0]*9));
	}

	/**
	 * finds the total salary payments if weekend hours are doubled
	 * @return totalsalary
	 */ 
	public static double totalSalariesWeekendHoursDouble(){
		double totalsalary = 0;
		for (int i = 0; i<l.size(); i++){
			totalsalary = totalsalary + l.get(i).getPaymentWeekendDouble();
		}
		return totalsalary;
	}

	/**
	 * finds the total salaries paid if two most working people are fired
	 * @param hours array of all hours each employeee works
	 * @param names list of names of employees
	 * @return sum_highesttwo
	 */
	public static double totSalFiringTwo(double [] hours, ArrayList <String> names){
		double sum_highesttwo = 0; //sum of two highest salaries (so that i can subtract them when firing workers)
		int highestindex = 0;
		highestindex = findHighest(hours);
		sum_highesttwo = hours[highestindex]; //adding first salary

		double[] editedhours = hours;
		ArrayList <String> editednameslist = new ArrayList <String> ();
		editednameslist.addAll(names);
		editednameslist.remove(highestindex);
		editedhours[highestindex] = -5;
		sum_highesttwo = sum_highesttwo+ hours[findHighest(editedhours)];//adding second salary

		return sum_highesttwo;
	}
	/**
	 * finds the range of hours worked per employee
	 * @param names list of names of employees
	 * @return workranges 
	 */
	public static double [][] findWorkHourRanges(ArrayList <String> names){
		double [][] workranges = new double [2][names.size()]; //first dimension encapsulates both start and end times, second dimension is for each name
		boolean firsttime = false;
		//for each name
		for (int i = 0; i < names.size(); i++){
			firsttime = true;
			//for each row
			for (int j = 0; j<l.size(); j++){
				//if name is in row
				if (l.get(j).getName().equals(names.get(i))){
					//initialize these spots in array for the first entry of their name
					if (firsttime== true){
						workranges[0][i] = l.get(j).getStartTime();
						workranges[1][i] = l.get(j).getEndTime();
						firsttime = false;
					}
					//if start time is earlier than ever before
					if(l.get(j).getStartTime() <workranges[0][i]){
						workranges[0][i] = l.get(j).getStartTime();
					}
					//if end time is later than ever before
					if(l.get(j).getEndTime() >workranges[1][i]){
						workranges[1][i] = l.get(j).getEndTime();
					}
				}
			}
		}
		return workranges;
	}

	/**
	 * finds which days each employee has worked on at least once
	 * @param names stores names of each employee
	 * @return workranges for each employee, stores which days they have worked on each day of the week
	 */
	public static boolean [][] findWorkDayRanges(ArrayList <String> names){
		boolean workranges [][] = new boolean [7][names.size()];
		int counter = 0;
		//for each name
		for (int i = 0; i < names.size(); i++){
			//for each row
			for (int j = 0; j<l.size(); j++){
				//if name is in row
				if (l.get(j).getName().equals(names.get(i))){
					counter = 0;
					if (l.get(j).getDay().equals("Monday")){
						workranges[counter][i] = true; 
					}
					counter ++;
					if (l.get(j).getDay().equals("Tuesday")){
						workranges[counter][i] = true; 
					}
					counter ++;
					if (l.get(j).getDay().equals("Wednesday")){
						workranges[counter][i] = true; 
					}
					counter ++;
					if (l.get(j).getDay().equals("Thursday")){
						workranges[counter][i] = true; 
					}
					counter ++;
					if (l.get(j).getDay().equals("Friday")){
						workranges[counter][i] = true; 
					}
					counter ++;
					if(l.get(j).getDay().equals("Saturday")){
						workranges[counter][i] = true; 
					}
					counter ++;
					if(l.get(j).getDay().equals("Sunday")){
						workranges[counter][i] = true; 
					}
				}
			}
		}
		return workranges;
	}
	/**
	 * returns a worker's name if he/she only works on weekdays
	 * @param names list of names
	 * @param workday_ranges each employees range of days worked
	 * @return weekday_worker the person who only works during weekdays
	 */
	public static String findWeekdayWorker(ArrayList <String> names, boolean [][] workday_ranges){
		String weekday_worker = new String();
		for (int i = 0; i<workday_ranges[0].length; i++){
			if ((workday_ranges[5][i] == false)&&(workday_ranges[6][i] == false)){
				weekday_worker = names.get(i);
			}
		}
		return weekday_worker;
	}
	/**
	 * returns a worker's name if he/she only works on weekends
	 * @param names list of names of employees
	 * @param workday_ranges ranges of days worked for each employee
	 * @return weekend_worker
	 */ 
	public static String findWeekendWorker (ArrayList <String> names, boolean [][] workday_ranges){
		String weekend_worker = new String();
		for (int i = 0; i<workday_ranges[0].length; i++){
			if ((workday_ranges[0][i] == false)&&(workday_ranges[1][i] == false)&&(workday_ranges[2][i]== false)&&(workday_ranges[3][i]== false)&&(workday_ranges[4][i]== false)){
				weekend_worker = names.get(i);
			}
		}
		return weekend_worker;
	}
	public static double [] fillHoursArray(ArrayList <String> names){
		double hours[] = new double [names.size()];
		for(int i = 0; i<names.size(); i++)
		{
			hours[i] = determineTotalHoursWorked(names.get(i));
		}
		return hours;
	}
	public static double [] fillNameFrecArray(ArrayList <String> names){
		double [] namefrec = new double [names.size()];
		for(int i = 0; i<names.size(); i++)
		{
		namefrec[i] = determineTotalHoursWorked(names.get(i));
		}
		return namefrec;
	}
	/**
	 * rounds numberes to given number of places
	 * function from :http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	 * @author: Jonik
	 * @param value value to convert
	 * @param places number of decimal places you want
	 * @return rounded value
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	/**
	 * main
	 * @param args public static void main parameter
	 */
	public static void main(String[] args) {
		//declarations:
		double money = 0;

		l = new ArrayList<TimeSheet>();
		Random ran = new Random();
		ArrayList <String> names = new ArrayList<String>(); //list of all employees names
		double [][] totoverlaps;
		int [] highestcoords;
		//name of the file to open
		String timesheet = "/Users/student/Documents/starbucksdata.csv";
		try {
			Scanner scan = new Scanner(new BufferedReader(new FileReader(timesheet)));


			//declaring array

			//main loop:
			while(scan.hasNext()) {

				String[] data = scan.nextLine().split(", ");

				TimeSheet t = new TimeSheet(data[0],Integer.parseInt(data[1]),Integer.parseInt(data[2]), Integer.parseInt(data[3]),Integer.parseInt(data[4]), data[5]);
				l.add(t);
			}

			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Simple Questions (Week One):
		System.out.println("WEEK ONE, Simple Functions:");
		//print number of rows in arraylist l?
		System.out.println("Employee Entries in this Datasheet: " + l.size());

		//print out first row
		System.out.println("First Entry: " + l.get(0));

		//print a random item
		System.out.println("Random Entry: " + l.get(ran.nextInt(l.size())));

		//print the hours (timesheet.get(endhour) - timesheet.get(starthour) worked of a random data entry

		System.out.println("Hours Worked in Random Entry: " + l.get(ran.nextInt(l.size())).determineHoursWorked());

		//Week Two Questions (More Complex):
		System.out.println();
		System.out.println("WEEK TWO, More Complex Functions:");

		//	How many Wednesday entries are there?
		System.out.println("Wednesdays: " + frequencyDay("Wednesday"));

		//	How many times did Nick work? How many hours did Nick work, total?
		System.out.println("Times Nick Worked: " + frequencyName("nick"));
		System.out.println("Hours Nick Worked: " + determineTotalHoursWorked("nick"));

		// Assuming $9/hour, how much did Nick make?
		System.out.println("Dollars Nick Made: " + determineTotalHoursWorked("nick")*9);

		// WHO WORKED THE MOST (money, hours, number of times)?
		
		
		System.out.println("EXTRA 1:");
		names = createNamesList(l);
		double [] hours = new double[names.size()];//array of tally of hours per employee (order of which matches with order of names in ArrayList names)
		double [] namefrec = new double[names.size()];//array of tally of instances of work per employee (order is same)

		//interpretation of question A: most and least hours? interpretation of q B: most and least times? most dollars made? tally them up
		//fill namefrec array
		namefrec = fillNameFrecArray(names);
		hours = fillHoursArray(names);
		
		//translate index number into name and print
		System.out.println(names.get(findHighest(hours)) + " worked for " + (hours[findHighest(hours)]) + " hours and made " +(hours[findHighest(hours)]*9) + "dollars, the most out of any employee. " + names.get(findHighest(namefrec)) + " worked " + (namefrec[findHighest(namefrec)]) + " times,  the most of any employee.");
		System.out.println(names.get(findLowest(hours)) + " worked for " + (hours[findLowest(hours)]) + " hours and made " +(hours[findLowest(hours)]*9) + " dollars, the least out of any employee. " + names.get(findLowest(namefrec)) + " worked " + (namefrec[findLowest(namefrec)]) + " times,  the least of any employee.");

		System.out.println("EXTRA 2");
		//WHO works the most consistently? criteria: whose average difference from their average hours worked is highest and lowest (standard deviation)
		System.out.println("Least Consistent: " + names.get(findHighest(findAverageDifferences(names))));
		System.out.println("Most Consistent: " + names.get(findLowest(findAverageDifferences(names))));


		
		//"Who's banging who? which two people are most likely best friends (overlap the most time relative to the amount they spend there)?
		//plan: first, figure out the beginning of each week, so that each day I talk about is within that week for both people being compared, and not the same day during different weeks
		//because weeks are in order, how many weeks are covered in the data sheet?
		System.out.println("EXTRA 3: Number of Weeks Covered: " + numberweeks());

		//find all employee's overlaps in times throughout the three years covered by datasheet	
		totoverlaps = findtotoverlaps(names);

		//find the highest value in totoverlap
		highestcoords = findHighestinTwoD(totoverlaps);

		//so, who's banging?
		System.out.println("EXTRA 4:");
		System.out.println(names.get(highestcoords[0]) + " and " + names.get(findHighestinTwoD(totoverlaps)[1]) + " are most likely banging. they have spent " + totoverlaps[highestcoords[0]][highestcoords[1]] + " hours together- the most time out of any two employees.");
		
		
		//WEEK 3
		System.out.println("WEEK 3, Object Methods \n Testing the methods:");
		
		// void printStartTime() //in pretty time
		l.get(0).printStartTime();
		
		//void printEndTime() //in pretty time
		l.get(0).printEndTime();
		
		//int getStartTime() //in minutes from 0
		System.out.println((l.get(0).getStartTime())*60);
		
		//int getEndTime() //in minutes from 0
		System.out.println(l.get(0).getEndTime()*60);
		
		//int gettimeWorked() //in minutes
		System.out.println(l.get(0).getTimeWorked());
		
		//double payment(double rate)	//payment for the day, given rate
		System.out.println(l.get(0).payment(9) + " dollars");
		
		//double payment()	//payment for the day, assuming weekdays $7, weekends $9
		System.out.println(l.get(0).getPaymentDif() + "dollars");
		
		//char gender()	//’m’ or ‘f’
		System.out.println(l.get(0).gender());
		
		//WEEK 4
		System.out.println();
		System.out.println("WEEK FOUR, Union Negotiations");
		
		//How much more would the following cost the company
		//total money paid to employees (without more being payed on off hours
		double totalcompmoney = round(findTotalCompanyMoola(1),2);
		System.out.println("Total Company Money Spent: " + totalcompmoney);

		//using 9 per hour weekend, 7 per hour weekday
		System.out.println(round(l.get(0).getPaymentDif(), 2));

		//HOW MUCH MORE IF...
		//1.5 more paid on off hours
		money = offHoursIncluded();
		System.out.println(round((money-totalcompmoney),2) + "$ more would be spent if employees would be payed 1.5 as much on offhours.");

		//double on weekends
		double totalhoursweekenddouble = totalSalariesWeekendHoursDouble();
		System.out.println(round((totalhoursweekenddouble- totalcompmoney),2) + "$ more would be spent if weekend hours were double");

		//all minutes rounded up
		System.out.println(round((findTotalCompanyMoola(2) - totalcompmoney),2) + "$ more would be spent if all entries were rounded up to nearest hour");

		//treat all entries as at least five hours long
		System.out.println(round((findTotalCompanyMoola(3) - totalcompmoney),2) + "$ more would be spent if all entries were treated as at least five hrs long");

		//fire the two people who work the most hours
		System.out.println(round((totSalFiringTwo(hours, names)*9),2) + "$ less would be spent if two workers with most hours are fired.");

		// maximum of 8 hours paid per day
		System.out.println(round(Math.abs(findTotalCompanyMoola(4) - totalcompmoney),2) + "$ less would be spent if all entries were treated as at most eight hrs long");

		//if employment was on basis of salaried positions, not hours listed in datasheet
		double wage = 9;
		double salariedmoney = names.size()*8*5*numberweeks()*wage;
		System.out.println((salariedmoney-totalcompmoney) + "$ more would be payed if payment was based off salary system with 8 hour workdays, five days a week. (all of the ten definitely are not all working fulltime at this job)");

		//WEEK FIVE
		System.out.println("\nWEEK FIVE, work with Lists (arraylists are already coded for previous functions");
		//1) one worker only works in the evenings- who? --> more generalized approach: print out range of everyone's work hours
		//plan:	for each entry with a given name, see if the start time is lowest yet, and if end time is highest yet. at the end, it should've stored the earliest and latest start and end times respectively for each worker
		//for each worker
		double [][]	workhour_ranges = findWorkHourRanges(names);
		System.out.println("Hour Ranges: ");
		for (int i = 0; i<names.size(); i++){
			System.out.println(names.get(i) + ": " + (int)workhour_ranges[0][i] + " to " + (int)workhour_ranges[1][i]);
		}
		//print out person who only works in the p.m.
		System.out.println("only works after 12:" +  names.get(findHighest(workhour_ranges[0])));

		//2) find person who only works on weekdays/3) person who only works on weekends
		boolean [][] workday_ranges = findWorkDayRanges(names);
		//print out the array
		for (int i = 0; i<names.size(); i++){
			System.out.println();
			System.out.print(names.get(i) + ": ");
			for (int j = 0; j<7; j++){
				if (workday_ranges[j][i] == true){
					System.out.print(j + ", ");
				}
			}
		}
		System.out.println();
		//identify which people only do weekends or weekdays
		System.out.println("The person who only works on weekdays is " + findWeekdayWorker(names, workday_ranges));
		System.out.println("The person who only works on weekends is " + findWeekendWorker(names, workday_ranges));
	}
}

import java.util.Arrays;


public class TimeSheet {
	String name;
	double starthour;
	double startminute;
	double endhour;
	double endminute;
	String day;

	
	/**
	 * description: constructor
	 * @param name  name in the entry
	 * @param starthour starthour in the entry
	 * @param startminute startminute in the entry
	 * @param endhour endhour in the entry
	 * @param endminute endminute in the entry
	 * @param day day in the entry
	 */
	public TimeSheet (String name, int starthour, int startminute, int endhour, int endminute, String day){
		this.name = name;
		this.starthour = starthour;
		this.startminute = startminute;
		this.endhour = endhour;
		this.endminute = endminute;
		this.day = day;
	}
/**
 * gets name
 * @return name
 */
	public String getName() {
		return name;
	}
	/**
	 * sets name
	 * @param name name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * gets starthour
	 * @return starthour
	 */
	public double getStarthour() {
		return starthour;
	}
	
	/**
	 * sets starthour
	 * @param starthour starthour
	 */
	public void setStarthour(int starthour) {
		this.starthour = starthour;
	}
	
/**
 * gets startiminutes
 * @return startminute
 */
	public double getStartminutes() {
		return startminute;
	}
	
/**
 * sets startminutes
 * @param startminutes startmins
 */
	public void setStartminutes(int startminutes) {
		this.startminute = startminutes;
	}
	/**
	 * gets endhour
	 * @return endhour
	 */
	public double getEndhour() {
		return endhour;
	}
	/**
	 * sets endhour
	 * @param endhour endhour in the entry
	 */
	public void setEndhour(int endhour) {
		this.endhour = endhour;
	}
	/**
	 * gets endminutes
	 * @return endminute endminute
	 */
	public double getEndminutes() {
		return endminute;
	}
	/**
	 * sets endminutes
	 * @param endminutes endminutes
	 */
	public void setEndminutes(int endminutes) {
		this.endminute = endminutes;
	}
	/**
	 * gets day
	 * @return day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * sets day
	 * @param day day
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * converts entry to printable string
	 * return (name + ", " + starthour + ", "+  startminute + ", " + endhour + ", " + endminute + ", " + day)
	 */
	public String toString() {
		return name + ", " + starthour + ", "+  startminute + ", " + endhour + ", " + endminute + ", " + day;
	}
	/**
	 * prints start time
	 */
	public void printStartTime(){
		System.out.println(((int) getStarthour()) + ":" + ((getStartminutes() < 10 ? "0" : "") + ((int) getStartminutes())));
	}
	/**
	 * prints end time
	 */
	public void printEndTime(){
		System.out.println(((int)getEndhour()) + ":" + ((getEndminutes() < 10 ? "0" : "") + ((int)getEndminutes())));
	}
	/**
	 * gets starttime
	 * @return (starthour + startminute/60)
	 */
	public double getStartTime(){
		return (starthour + startminute/60);
	}	
	/**
	 * gets endtime 
	 * @return (endhour + endminute/60)
	 */
	public double getEndTime(){
		return endhour + endminute/60;
	}
	/**
	 * gets time worked
	 * @return (this.getEndTime()- this.getStartTime())
	 */
	public double getTimeWorked(){
		return (this.getEndTime()- this.getStartTime());
	}
	/**
	 * gets payment given a rate of payment
	 * @param rate rate
	 * @return this.getTimeWorked()*rate
	 */
	public double payment(double rate){
		return this.getTimeWorked()*rate;
	}
	/**
	 * gets payment calculating weekend and weekday hours differently
	 * @return return this.getTimeWorked()*9 OR return this.getTimeWorked()*7
	 */
	public double getPaymentDif(){
		if ((this.getDay().equals("Saturday")) || this.getDay().equals("Sunday")){
			return this.getTimeWorked()*9;
		}
		else{
			return this.getTimeWorked()*7;
		}
	}
	/**
	 * determines hours worked for given employee
	 * @return hoursworked
	 */
	public double determineHoursWorked (){
		double hoursworked = 0;
		hoursworked = this.getEndhour() - this.getStarthour() + (this.getEndminutes() - this.getStartminutes())/60;
		return hoursworked;
	}
/**
 * determines gender of employee 
 * gender
 * @return gender
 */
	public char gender(){
		char gender;
		if((getName().equals("sarah")) || (getName().equals("wanda")) || (getName().equals("lori")) || (getName().equals("jennifer")) || (getName().equals("princess"))){
			gender = 'f';
		}
		else{
			gender = 'm';
		}
		return gender;
	}
	/**
	 * categorizes hours into off hours and normal times 
	 * @return rowpaymentcategories
	 */
	public double [] timesheetOffHoursIncluded(){
		double [] rowpaymentcategories = new double [2];//0 = normal pay (nonoffhours) tot, 1 = offhour  tot
		double temp_night_overlap = 0;
		double temp_morning_overlap = 0;
		//find overlap btw offhour range and this entry's range
		temp_night_overlap = findOverlap(this.getStartTime(),this.getEndTime(), 20, 24);
		//add all off hours in this entry to 3
		rowpaymentcategories[1] = temp_night_overlap;

		//b) for morning off hours
		//find overlap with morning offhours
		temp_morning_overlap = findOverlap(this.getStartTime(), this.getEndTime(), 0, 6);
		//add all off hours in this entry to 3
		rowpaymentcategories[1] = temp_morning_overlap;
		//add all normal weekend hours to 2
		rowpaymentcategories[0] = this.determineHoursWorked() - temp_morning_overlap - temp_night_overlap;
		for(int j = 0; j<2; j++){
		}
		return rowpaymentcategories;
	}
	///: overlap- a beauty
	/**
	 * Calculates overlap between two periods of time, given both their start and end points
	 * @param Astart
	 * @param Aend
	 * @param Bstart
	 * @param Bend
	 * @return overlap
	 */
	double findOverlap(double Astart, double Aend, double Bstart, double Bend){
		double overlap = 0;
		double [] times = new double []{Astart, Aend, Bstart, Bend};
		if(times[0]<times[3] && times[2]<times[1]){
			Arrays.sort(times);
			overlap = (times[2]- times[1]);
		}
		return overlap;
	}
	//double
	/**
	 * gets payment if weekend hours are double
	 * @return (this.determineHoursWorked()*18) OR return (this.determineHoursWorked()*9)
	 */
	double getPaymentWeekendDouble (){

		if ((this.getDay().equals("Saturday")) || this.getDay().equals("Sunday")){
			return (this.determineHoursWorked()*18);
		}
		else{
			return (this.determineHoursWorked()*9);
		}
	}
	//get rounded up time worked
	/**
	 * rounds up times to hours
	 * @return 	return (this.getEndhour() - this.getStarthour() + binary);
	 */
	double roundedUpTime(){
		int binary = 0;
		if (this.getEndminutes()>this.getStartminutes()){
			binary = 1;
		}
		else{
			binary = 0;
		}
		return (this.getEndhour() - this.getStarthour() + binary);
		
	}
	/**
	 * highers the floor of minimum hours
	 * @return 
	 */
	//round up small entries to five hours
	double fiveHrCall(){
		if (this.determineHoursWorked()< 5){
			return 5;
		}
		else{
			return this.determineHoursWorked();
		}
	}
	/**
	 * lowers ceiling of max hours
	 * @return * or determineHoursWorked()
	 */
	double eightHrCap(){
		if(this.determineHoursWorked()>8){
			return 8;
		}
		else{
			return this.determineHoursWorked();
		}
	}
	
}
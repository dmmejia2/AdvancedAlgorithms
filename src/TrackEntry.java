/**
 * Author: Daniel Mejia
 * Last Modified: May 2016
 * Program to compare two algorithms,
 * Dynamic Programming and Greedy Algorithms
 */
import java.util.*;
import java.io.*;
public class TrackEntry {
	public TrackEntry() {
	}

	public static void main(String[] args) {


		/**
		 * Creates several lists:
		 * Event List - List of All events from txt file
		 * Athlete List - List of all athletes from txt file
		 * dynamicEventList - List of all events from txt file (Dynamic Programming approach)
		 * dynamicAthleteList - List of all athletes from txt file (Dynamic Programming Approach)
		 * checkAgain - Queue to add athletes into so that they can be re-evaluated (Dynamic Programming)
		 * Lists are made separate to show complete disjoint between the two approaches.
		 */
		List<Event> eventList = new ArrayList<Event>();
		List<Athlete> athleteList = new ArrayList<Athlete>();
		List<Event> dynamicEventList = new ArrayList<Event>();
		List<Athlete> dynamicAthleteList = new ArrayList<Athlete>();
		Queue<Athlete> checkAgain = new LinkedList<Athlete>();
		try {
			/**
			 * Two scanner readers for athletes and events
			 */
			String athleteLine="";
			String eventLine="";
			BufferedReader athleteReader = new BufferedReader(new FileReader("/Users/danielmejia/Documents/workspace/Advanced Algorithms/src/athletes4.txt"));
			BufferedReader eventReader = new BufferedReader(new FileReader("/Users/danielmejia/Documents/workspace/Advanced Algorithms/src/events.txt"));
			/**
			 * Adds all athletes to the athletes list/Based on the text file
			 * assigns the athlete a skill (determined by coach)
			 * They are added to the queue for the DP approach so that each athlete can
			 * be evalutated
			 * */

			while((athleteLine = athleteReader.readLine())!=null){
				Athlete currentAthlete = new Athlete();
				String[] skill = athleteLine.split(",");
				currentAthlete.setName(skill[0]);
				currentAthlete.setSpeed(Integer.parseInt(skill[1]));
				currentAthlete.setEndurance(Integer.parseInt(skill[2]));
				currentAthlete.setcoordination(Integer.parseInt(skill[3]));
				currentAthlete.setStrength(Integer.parseInt(skill[4]));
				athleteList.add(currentAthlete);
				dynamicAthleteList.add(currentAthlete);
				checkAgain.add(currentAthlete);
			}
			/**
			 * Adds all of the events to the event list
			 * It also sets the optimal skill levels to 
			 * do well in that event (subjective to the coach) (txt file)
			 */
			while((eventLine = eventReader.readLine())!=null){
				String[] eventAttributes = eventLine.split(";");
				Event currentEvent = new Event();

				currentEvent.setName(eventAttributes[0]);
				currentEvent.setSpeedImp(Double.parseDouble(eventAttributes[1]));
				currentEvent.setEnduranceImp(Double.parseDouble(eventAttributes[2]));
				currentEvent.setStrengthImp(Double.parseDouble(eventAttributes[3]));
				currentEvent.setCoordinationImp(Double.parseDouble(eventAttributes[4]));
				currentEvent.setRelay(Integer.parseInt(eventAttributes[5]));
				eventList.add(currentEvent);
				dynamicEventList.add(currentEvent);

			}
			eventReader.close();
			athleteReader.close();
		}catch(IOException e){
			System.out.println("ERROR: "+e.getMessage());
		}

		try{
			//Gives the user ability to choose whichg approach they want to do
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Type 1 for Dynamic Approach");
			System.out.println("Type 2 for Greedy Approach");
			int selection = Integer.parseInt(br.readLine());
			System.out.println("Type 1 to print athlete roster");
			System.out.println("Type 2 if you do not want a roster");
			int roster = Integer.parseInt(br.readLine());
			br.close();
			if(roster==1){
				printRoster(athleteList);
			}
			//DP Approach
			//Empirical running time 
			if(selection==1){
				long startTime = System.nanoTime();
				executeDynamic(dynamicAthleteList,dynamicEventList,checkAgain);
				long endTime = System.nanoTime();
				printDynamic(dynamicAthleteList,dynamicEventList);
				long totalTime = endTime-startTime;
				System.out.println("The running time took "+totalTime +" nanoseconds; "+totalTime/1000000000.0+" seconds.");

				//Greedy Approach
			}else if(selection==2){
				long startTime = System.nanoTime();
				executeGreedy(athleteList, eventList);
				long endTime = System.nanoTime();
				printGreedy(eventList);
				long totalTime = endTime-startTime;
				System.out.println("The running time took "+totalTime +" nanoseconds; "+totalTime/1000000000.0+" seconds.");
			}else{
				System.out.println("Please select either dynamic or greedy appraches");
			}


		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Method determines if an athlete has already been entered in an event,
	 * if so, they should not be added to the event again, thus that event
	 * will not be evaluated for that athlete
	 * @param currentAthlete
	 * @param currentEvent
	 * @return boolean value
	 */
	public static boolean alreadyInEvent(Athlete currentAthlete, Event currentEvent){
		for(int i=0; i<currentEvent.myAthlete.length;i++){
			if(currentAthlete == currentEvent.myAthlete[i]){
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param currentEvent
	 * @return an array of the athlete with the lowest
	 * skill value in the event
	 */
	public static double[] getArrayMin(Event currentEvent){
		double MIN = Double.MAX_VALUE;
		double index = -1;
		double isFilled = 0;
		double [] tempValues = new double[3];
		for(int i=0; i<currentEvent.myAthlete.length;i++){
			if(currentEvent.myAthlete[i]!=null){
				isFilled=i;
				double currentAthleteIndex = computeEventSkill(currentEvent.myAthlete[i], currentEvent);
				if(currentAthleteIndex<MIN){
					MIN=currentAthleteIndex;
					index=i;
				}
			}
		}
		tempValues[0] = MIN;
		tempValues[1] = index;
		if(isFilled==currentEvent.myAthlete.length-1){
			tempValues[2] = 1;
		}else{
			tempValues[2] = 0;
		}
		return tempValues;
	}

	/**
	 * Simple sorting algorithm to sort the athlete positions in the list from highest
	 * skill level to the lowest skill level
	 * @param dynamicEventList
	 */
	public static void sortAthletePositions(List<Event> dynamicEventList){

		for(int i=0;i<dynamicEventList.size();i++){
			Athlete [] tempList = new Athlete[dynamicEventList.get(i).myAthlete.length];
			int index = dynamicEventList.get(i).myAthlete.length-1;
			double [] athleteInfo = new double[3];
			athleteInfo = getArrayMin(dynamicEventList.get(i));
			while(index>=0){
				athleteInfo = getArrayMin(dynamicEventList.get(i));
				tempList[index] = dynamicEventList.get(i).myAthlete[(int)athleteInfo[1]];
				dynamicEventList.get(i).myAthlete[(int)athleteInfo[1]]=null;
				index--;
			}
			for(int j=0; j<tempList.length;j++){
				dynamicEventList.get(i).myAthlete[j] = tempList[j];
			}
			System.out.println("");
		}

	}

	/**
	 * Method to set each athletes overall skill level
	 * for each event.
	 * @param athleteList
	 * @param eventList
	 * @return tempSkills
	 */
	public static double[][] setSkillList(List<Athlete> athleteList, List<Event> eventList){
		double [][] tempSkills = new double[eventList.size()][athleteList.size()];
		for(int i=0; i<tempSkills.length;i++){
			for(int j=0;j<tempSkills[i].length;j++){
				tempSkills[i][j] = computeEventSkill(athleteList.get(j),eventList.get(i));
			}
		}
		return tempSkills;
	}

	/**
	 * Method to compute each athletes overall skill level for
	 * each event based on the level (percentage) of importance in
	 * the event list.
	 * @param currentAthlete
	 * @param currentEvent
	 * @return athleteIndex
	 */
	public static double computeEventSkill(Athlete currentAthlete, Event currentEvent){
		double athleteIndex=0;
		athleteIndex +=currentAthlete.speed * currentEvent.speedImportance;
		athleteIndex +=currentAthlete.endurance * currentEvent.enduranceImportance;
		athleteIndex +=currentAthlete.strength * currentEvent.strengthImportance;
		athleteIndex +=currentAthlete.coordination * currentEvent.coordinationImportance;
		return athleteIndex;
	}

	/**
	 * Finds the highest rated athlete in an event
	 * @param skill
	 * @param eventIndex
	 * @return maxIndex
	 */
	public static int findHighestAthlete(double[][] skill, int eventIndex){
		double MAX = Double.MIN_VALUE;
		int maxIndex=0;
		for(int j=0;j<skill[eventIndex].length;j++){
			if(MAX<skill[eventIndex][j]){
				MAX=skill[eventIndex][j];
				maxIndex=j;
			}
		}
		//If all have already been looked at, no more athletes remain for this event
		if(MAX==-1){
			return -2;
		}
		skill[eventIndex][maxIndex]=-1;
		return maxIndex;
	}

	/**
	 * Compute a reasonable point score for all of the 
	 * events and athletes in the event.  This is based on a
	 * static threshold for each place.  If an athlete reaches a 
	 * certain skill level for an event and it is greater than
	 * an individual place, they will be assumed to score that many
	 * points (points per place) in actual competition.
	 * @param event
	 */
	public static void computeExpectedPoints(Event event){
		double[] eventPlacement;
		int pointTotal=0;

		eventPlacement = new double[event.myAthlete.length];
		for(int j=0;j<event.myAthlete.length;j++){
			eventPlacement[j] = computeEventSkill(event.myAthlete[j],event);
		}
		for(int j=0; j<eventPlacement.length;j++){
			if(event.isRelay==true){
				double relayAttribute=0;
				for(int k=0;k<4;k++){
					relayAttribute +=eventPlacement[k];
				}
				relayAttribute = relayAttribute/4;
				eventPlacement[eventPlacement.length-1] = relayAttribute;
				j=eventPlacement.length-1;

			}
			if(eventPlacement[j]>=event.first&&event.firstScored==false){
				pointTotal+=event.firstPoints;
				event.firstScored=true;
			}else if(eventPlacement[j]>=event.second&&event.secondScored==false){
				pointTotal+=event.secondPoints;
				event.secondScored=true;
			}else if(eventPlacement[j]>=event.third&&event.thirdScored==false){
				pointTotal+=event.thirdPoints;
				event.thirdScored=true;
			}else if(eventPlacement[j]>=event.fourth&&event.fourthScored==false){
				pointTotal+=event.fourthPoints;
				event.fourthScored=true;
			}else if(eventPlacement[j]>=event.fifth&&event.fifthScored==false){
				pointTotal+=event.fifthPoints;
				event.fifthScored=true;
			}else if(eventPlacement[j]>=event.sixth&&event.sixthScored==false){
				pointTotal+=event.sixthPoints;
				event.secondScored=true;
			}
			if(event.isRelay==true){
				pointTotal*=2;
			}

			event.pointsScored = pointTotal;
		}
	}

	/**
	 * Dynamic Programming algorithm
	 * @param dynamicAthleteList
	 * @param dynamicEventList
	 * @param checkAgain
	 */
	public static void executeDynamic(List<Athlete> dynamicAthleteList, List<Event> dynamicEventList, Queue<Athlete> checkAgain){

		//Every athlete is evaluated against each event to determine their skill set
		for(int i=0; i<dynamicAthleteList.size();i++){
			for(int j=0; j<dynamicEventList.size();j++){

				double tempIndex = computeEventSkill(dynamicAthleteList.get(i),dynamicEventList.get(j));
				dynamicAthleteList.get(i).addToAllEvents(tempIndex, dynamicEventList.get(j));
			}
		}

		//While the queue of athletes is not empty
		while(!checkAgain.isEmpty()){
			//Each athlete is checked for every event and placed
			//In the event that they are best suited for and will give
			//The team the best chance to score points
			Athlete current = new Athlete();
			current = checkAgain.remove();
			for(int i=0;i<current.allEvents.length;i++){
				for(int j=0; j<dynamicEventList.size();j++){

					//Compare the current event
					if(current.allEvents[j].eventName==dynamicEventList.get(i).eventName){
						//get smallest current value in the dynamicEventList.get(i).myAthletes
						double [] tempValues = new double[3];
						tempValues = getArrayMin(dynamicEventList.get(i));
						//tempValues[0] - minimum value of athleteIndex currently in array
						//tempValues[1] - minimum values index, if -1, no items exist
						int isFilled = (int)tempValues[2];

						//If an athlete is already in an event they will not be added again
						//additional restrictions are also made for certain events that they cannot be in at once
						//Removes the possible number of running (or field) events remaining for an athlete
						if(tempValues[1]==-1){
							if(((current.runningRemaining>0)&&(alreadyInEvent(current,dynamicEventList.get(i))==false))&&(dynamicEventList.get(i).eventName.contains("hundred")||dynamicEventList.get(i).eventName.contains("Hundred")||dynamicEventList.get(i).eventName.contains("relay")||dynamicEventList.get(i).eventName.contains("Relay"))){
								dynamicEventList.get(i).myAthlete[0] = current;
								current.runningRemaining--;
							}else if(current.fieldRemaining>0&&(alreadyInEvent(current,dynamicEventList.get(i))==false)){
								dynamicEventList.get(i).myAthlete[0] = current;
								current.fieldRemaining--;
							}
							//If the event is filled, then other athletes are checked against that event still
							//If someone is checked and better suited for the event the lowest ranking athlete will be removed,
							//And the current athlete will be added
							//If an athlete is removed, they are added to the queue to check what their next best event will be
						}else if(tempValues[0]<current.allEventsIndex[j]&&isFilled==1){
							if(((current.runningRemaining>0)&&(alreadyInEvent(current,dynamicEventList.get(i))==false))&&(dynamicEventList.get(i).eventName.contains("hundred")||dynamicEventList.get(i).eventName.contains("Hundred")||dynamicEventList.get(i).eventName.contains("relay")||dynamicEventList.get(i).eventName.contains("Relay"))){

								Athlete removed = new Athlete();
								int removeFromListIndex = (int)tempValues[1];
								removed = dynamicEventList.get(i).myAthlete[removeFromListIndex];
								removed.runningRemaining++;
								dynamicEventList.get(i).myAthlete[removeFromListIndex] = current;
								current.runningRemaining--;
								if(!checkAgain.contains(removed)){
									checkAgain.add(removed);
								}

							}else if((current.fieldRemaining>0)&&(alreadyInEvent(current,dynamicEventList.get(i))==false)){
								Athlete removed = new Athlete();
								int removeFromListIndex = (int)tempValues[1];
								removed = dynamicEventList.get(i).myAthlete[removeFromListIndex];
								removed.fieldRemaining++;
								dynamicEventList.get(i).myAthlete[removeFromListIndex] = current;
								current.fieldRemaining--;
								if(!checkAgain.contains(removed)){
									checkAgain.add(removed);
								}
							}

							//If there is no one in the event, the athlete will be added to the current event
						}else if(isFilled==0){
							if(((current.runningRemaining>0)&&(alreadyInEvent(current,dynamicEventList.get(i))==false))&&(dynamicEventList.get(i).eventName.contains("hundred")||dynamicEventList.get(i).eventName.contains("Hundred")||dynamicEventList.get(i).eventName.contains("relay")||dynamicEventList.get(i).eventName.contains("Relay"))){

								dynamicEventList.get(i).addToEvent(current);
								current.runningRemaining--;
							}else{
								if((current.fieldRemaining>0)&&(alreadyInEvent(current,dynamicEventList.get(i))==false)){
									dynamicEventList.get(i).addToEvent(current);
									current.fieldRemaining--;
								}
							}
						}
					}
				}
			}
		}
		//The athletes will be ranked per event in order of skill set from high to low
		sortAthletePositions(dynamicEventList);
	}

	/**
	 * Prints the list of events and the athletes that should be in the event
	 * @param dynamicAthleteList
	 * @param dynamicEventList
	 */
	public static void printDynamic(List<Athlete> dynamicAthleteList, List<Event> dynamicEventList){
		//TESTING DYNAMIC
		int dynamicTeam = 0;
		for(int i=0;i<dynamicEventList.size(); i++){

			//Computes the expected point values
			computeExpectedPoints(dynamicEventList.get(i));
			System.out.println(dynamicEventList.get(i).eventName);
			for(int j=0;j<dynamicEventList.get(i).myAthlete.length;j++){
				if(dynamicEventList.get(i).myAthlete[j]!=null){
					System.out.println(dynamicEventList.get(i).myAthlete[j].name);
				}
			}
			//Total team points are calculated
			dynamicTeam+=dynamicEventList.get(i).pointsScored;
			System.out.println("Maximum reasonable points scored in "+dynamicEventList.get(i).eventName+": "+dynamicEventList.get(i).pointsScored);
			System.out.println("");
		}
		System.out.println("Reasonable final team total points: "+dynamicTeam);
	}


	/**
	 * Method computes athlete entries using a greedy algorithm.
	 * The highest rated athlete will be added to the event if they have not met
	 * their maximum event limit.
	 * @param athleteList
	 * @param eventList
	 */
	public static void executeGreedy(List<Athlete> athleteList, List<Event> eventList){
		double [][] skillSet = new double[athleteList.size()][eventList.size()];
		skillSet = setSkillList(athleteList,eventList);

		//Set athlete entries for each event
		int highestAttribute;
		for(int i=0; i<eventList.size();i++){

			//Gets the highest rated athlete for each event
			for(int j=0;j<eventList.get(i).myAthlete.length;j++){
				highestAttribute=-1;
				while(highestAttribute==-1){
					//Does not allow an athlete to be entered into anymore events
					//If they have met their limit
					highestAttribute = findHighestAthlete(skillSet,i);
					if(athleteList.get(highestAttribute).runningRemaining==0){
						highestAttribute=-1;
					}else if(athleteList.get(highestAttribute).fieldRemaining==0){
						highestAttribute=-1;
					}else if(highestAttribute==-2){
						break;
					}
				}
				//Adds athletes to the event list
				if((eventList.get(i).eventName.contains("hundred")||eventList.get(i).eventName.contains("Hundred")||eventList.get(i).eventName.contains("Relay")||eventList.get(i).eventName.contains("Relay"))){

					if(athleteList.get(highestAttribute).runningRemaining>0){
						eventList.get(i).myAthlete[j]=athleteList.get(highestAttribute);
						athleteList.get(highestAttribute).runningRemaining--;						
					}

				}else{
					if(athleteList.get(highestAttribute).fieldRemaining>0){
						eventList.get(i).myAthlete[j]=athleteList.get(highestAttribute);
						athleteList.get(highestAttribute).fieldRemaining--;						
					}

				}
			}
		}
	}
	/**
	 * Prints all of the elements in the event list using the greedy algorithm
	 * @param eventList
	 */
	public static void printGreedy(List<Event> eventList){
		//TESTING
		int teamTotal = 0;
		for(int i=0;i<eventList.size(); i++){

			//Computes expected points based on the event entry list
			computeExpectedPoints(eventList.get(i));
			System.out.println(eventList.get(i).eventName);
			for(int j=0;j<eventList.get(i).myAthlete.length;j++){
				if(eventList.get(i).myAthlete[j]!=null){
					System.out.println(eventList.get(i).myAthlete[j].name);
				}
			}
			teamTotal+=eventList.get(i).pointsScored;
			System.out.println("Maximum reasonable points scored in "+eventList.get(i).eventName+": "+eventList.get(i).pointsScored);
			System.out.println("");
		}
		System.out.println("Reasonable final team total points: "+teamTotal);
	}

	/**
	 * Prints the roster of athletes
	 * @param athleteList
	 */
	public static void printRoster(List<Athlete> athleteList){
		System.out.println("Athlete Roster");
		for(int i=0; i<athleteList.size();i++){
			System.out.println(athleteList.get(i).name);
		}
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
		System.out.println("**************************************************");
	}

}

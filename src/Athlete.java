/**
 * 
 * @author Daniel Mejia
 * Last Modified: May 2016
 * Structure of an Athlete
 *
 */
public class Athlete {
	//Attributes of an athlete
	public int speed;
	public int endurance;
	public int strength;
	public int coordination;
	public String name;
	public int runningRemaining = 3;
	public int fieldRemaining = 2;

	//All of the events for an athlete
	Event [] allEvents = new Event [17];
	double [] allEventsIndex = new double[17];

	//Sets attributes
	public void setName(String myName){
		this.name=myName;
	}
	public void setSpeed(int speed){
		this.speed = speed;
	}
	public void setEndurance(int endurance){
		this.endurance = endurance;
	}
	public void setStrength(int strength){
		this.strength = strength;
	}
	public void setcoordination(int coordination){
		this.coordination = coordination;
	}

	//Adds the athletes to all of the events
	public void addToAllEvents(double athleteIndex, Event currentEvent){
		if(allEventsIndex[0]==-1){
			allEventsIndex[0] = athleteIndex;
			allEvents[0] = currentEvent;
		}else{
			for(int i=0; i<allEvents.length;i++){
				if(athleteIndex>allEventsIndex[i]){
					double temp = allEventsIndex[i];
					Event eventTemp = new Event();
					eventTemp = allEvents[i];
					allEventsIndex[i]=athleteIndex;
					allEvents[i] = currentEvent;
					athleteIndex = temp;
					currentEvent = eventTemp;
				}
			}
		}
	}



	public Athlete() {
		for(int i=0;i<allEventsIndex.length;i++){
			this.allEventsIndex[i]=-1;
		}
		// TODO Auto-generated constructor stub
	}

}

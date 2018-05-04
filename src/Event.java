/**
 * 
 * @author Daniel Mejia
 * Last Modified: May 2016
 * Definition of Event to be used in TrackEntry.java
 *
 */
public class Event {
	//Attributes for the event
	//Skill to achieve at least these places
	public int first = 95;
	public int second = 90;
	public int third = 80;
	public int fourth = 70;
	public int fifth = 65;
	public int sixth = 60;

	//Points per place
	public int firstPoints = 10;
	public int secondPoints = 8;
	public int thirdPoints = 6;
	public int fourthPoints = 4;
	public int fifthPoints = 2;
	public int sixthPoints = 1;
	public int pointsScored = 0;
	
	//Boolean to determine that points are scored
	public boolean firstScored = false;
	public boolean secondScored = false;
	public boolean thirdScored =false;
	public boolean fourthScored = false;
	public boolean fifthScored = false;
	public boolean sixthScored = false;
	//Attribute level (percentage) of importance
	//of skill to be successful in an event
	public double speedImportance;
	public double enduranceImportance;
	public double strengthImportance;
	public double coordinationImportance;
	public String eventName;
	public boolean isRelay;
	
	//Athletes in the event
	Athlete [] myAthlete;
	

	public Event(){

	}
	void setName(String name){
		this.eventName = name;
	}

	void setSpeedImp(double speedPercent){
		this.speedImportance=speedPercent;

	}
	void setEnduranceImp(double endurancePercent){
		this.enduranceImportance = endurancePercent;

	}
	void setStrengthImp(double strengthPercent){
		this.strengthImportance = strengthPercent;

	}
	void setCoordinationImp(double coordPercent){
		this.coordinationImportance = coordPercent;

	} 
	//Set relay events
	void setRelay(int isRelay){
		if(isRelay==0){
			this.myAthlete = new Athlete[3];
			this.isRelay=false; 
		}else{
			this.myAthlete = new Athlete[4];
			this.isRelay=true;
		}
	}
	
	//Add points to the event
	void addPoints(int points){
		this.pointsScored+=points;
	}
	
	//Add athlete to the event
	void addToEvent(Athlete currentAthlete){
		for(int i=0;i<myAthlete.length;i++){
			if(myAthlete[i]==null){
				myAthlete[i] = currentAthlete;
			}
		}
	}




}

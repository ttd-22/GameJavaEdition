package GameJavaEdition;
import java.util.*;
import java.io.*;

public class GameMethods {
	/* Methods are divided into 5 sections: Input, Army Creation, Army Misc, and Gameplay */
	
	//TODO: if performance becomes a priority, ArmyIsAlive() can be replaced by an int that ++ every
	//time a unit dies, takes O(1) instead of 0(n) time
	//TODO: add ticker to while loop and to keep track of turn number
	
	// Input Methods
	
	public static int getInput(String str, int lower, int upper){
		/* returns int only if int is between lower and upper */
		
		System.out.println(str);
		Scanner scanner = new Scanner(System.in);
		int input;
		
		while (true) {
			if (scanner.hasNextInt()){
				input = scanner.nextInt();
				if ((lower <= input && input <= upper)) 
					return input;
				else
					System.out.println("Input out of bounds");
			}else {
				System.out.println("Not an integer");
				scanner.next();
			}
		}
	}

	public static String getInput(String str) {
		System.out.println(str);
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	public static boolean playerContinues(String str){ 
		/* asks if user wants to continue  
		 * y returns true, n returns false */
		
		Scanner scanner = new Scanner(System.in);
		System.out.println(str + " (y/n)");
	
		while(true) {
			String input = scanner.nextLine();
			if (input.equals("y"))
				return true;
			else if (input.equals("n"))
				return false;
			else
				System.out.println("Invalid input");
		}
	}
	
	//Army Creation Methods
	
	public static Unit[] generateUnits(String unit, int numUnits) {
		/* generates numUnits amount of infantry, cavalry, or archers */
		
		Unit unitArray[] = new Unit[numUnits];
		switch(unit) {
			case "infantry":
				for (int i = 0; i < numUnits; i++) {
					Infantry infantry = new Infantry();
					unitArray[i] = infantry;
				}
				break;
			case "cavalry":
				for (int i = 0; i < numUnits; i++) {
					Cavalry cavalry = new Cavalry();
					unitArray[i] = cavalry;
				}
				break;
			case "archers":
				for (int i = 0; i < numUnits; i++) {
					Archer archer = new Archer();
					unitArray[i] = archer;
				}
				break;
		} return unitArray;
	}
	
	public static void nameUnits(Unit[][] army, String suffix) {
		/* numbers units and specifies to which army a unit belongs to
		 * for example, first infantry in player 1's army becomes Infantry1p1 */
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < army[i].length; j++) {
				String currentName = army[i][j].toString();
				army[i][j].setName(currentName + (j+1) + " of the " + suffix); // j+1 because index starts at 0 but unit names start at 1
			}
		}
	}
	
	public static Unit[][] createArmy(String suffix) {
		/* creates an array of array containing arrays of infantry, archers, and cavalry */
	
		String unitNames[] = {"infantry", "cavalry", "archers"};
		Unit Army[][] = new Unit[3][]; // one row for each type of unit
		
		for (int i = 0; i < 3; i++) {
			int numUnits = getInput("How many " + unitNames[i] + " do you want?", 0, 10);
			Army[i] = generateUnits(unitNames[i], numUnits);
		}
		nameUnits(Army, suffix); // assign proper names to all units in army
		return Army;
	}
	
	public static void printArmy(Unit[][] army) {
		/* prints out all living units in an army */
		
		String unitNames[] = {"Infantry", "Cavalry", "Archers"};
		for (int i = 0; i < 3; i++) {
			System.out.println(unitNames[i] + ":");
			for (Unit unit : army[i]) {
				if (unit.isAlive()) 
					System.out.println(unit + "---" + unit.getHealth() + "HP" + (unit.isTurnEnded() ? " (already moved)" : ""));
			}
			System.out.println("------------");
		}
	}
	
	// Misc Army Methods
	
	public static boolean armyIsAlive(Unit[][] army) {
		/* checks if army has any living units */
		return Arrays.stream(army)
				.flatMap(Arrays::stream)
				.filter(unit -> unit.isAlive())
				.count() > 0;
	}
	
	public static boolean armyTurnNotEnded(Unit[][] army) {
		/* checks to see if army has any units that haven't had their turn yet */	
		return Arrays.stream(army)
				.flatMap(Arrays::stream)
				.filter(unit -> unit.isAlive())
				.filter(unit -> !unit.isTurnEnded())
				.count() > 0;
	}
	
	public static void resetTurnEnded(Unit[][] army) {	
		/* makes all living units actionable once it is their turn again*/
		Arrays.stream(army)
		.flatMap(Arrays::stream)
		.filter(unit -> unit.isAlive())
		.forEach(unit -> unit.setTurnEnded(false));
	}
	
	// Gameplay Methods
	
	public static Unit selectUnit(Unit[][] army) {
		/* selects a unit from an army */
		// int unitRow = getInput("Enter 1, 2, or 3 to select an infantry, cavalry, or archer", 1, 3);
		// int unitNumber = getInput("Enter the unit number of the unit you wish to select "
									//+ "i.e. to select infantry5 enter '5'", 1, 10);
		// -1 because army array rows & columns start at 0 while input starts at 1
		// Unit selectedUnit = army[unitRow - 1][unitNumber - 1]; 
		// return selectedUnit;
		
		ArrayList<String> letters = new ArrayList<String>(Arrays.asList("i", "c", "a"));
		
		while(true) {
			String input = getInput("To select a unit, enter the first letter of the unit plus the unit number"
					+ "\n(i.e. enter i5 or c3 to select Infantry5 or Cavalry3)");
			
			if (input.equals("end")) return null; // instantly ends turn

			
			try {
				String row = input.substring(0, 1);
				String column = input.substring(1);
				
				int unitRow = letters.indexOf(row);
				int unitColumn = Integer.parseInt(column);
				
				Unit unit = army[unitRow][unitColumn - 1];
				// make sure unit can be selected
				if (!unit.isTurnEnded() && unit.isAlive())
					return unit; 
				else
					System.out.println("Not a valid unit to select");
				
			}catch(Exception e) {
				System.out.println("Invalid input. Try again.");
			}
		}
	}
	
	public static void playTurn(Unit[][] army1, Unit[][] army2) {
		/* Recursive function that pits an army against another until one side is dead or every unit has moved */
		
			while(armyTurnNotEnded(army1) && armyTurnNotEnded(army2)) {
				
				System.out.println("Enter a unit that you want to attack with. Type 'end' to instantly end turn.");
				printArmy(army1);
				Unit attacker = selectUnit(army1);
				
				if (attacker == (null)) break; // end army's turn
				
				System.out.println("Enter the enemy unit you want to attack");
				printArmy(army2);
				Unit defender = selectUnit(army2);
				
				if (defender == (null)) break; // end army's turn
				
				attacker.attack(defender);
		}
			resetTurnEnded(army1);
			System.out.println("End of turn\n");
			if(armyIsAlive(army1) && armyIsAlive(army2)) // continue game if no side has won
				playTurn(army2, army1);
			else
				System.out.println("Game over");
	}
	
	public static void writeBattleHistory(String str){
		/* WIP: write down if unit was killed, write down when battle starts maybe? and write down winner of whole game */
		try {
			FileWriter fw = new FileWriter("BattleHistory.txt");
			fw.write(str);
			fw.close();
		}catch (Exception e) {
			e.getMessage();
		}
	}
}
package GameJavaEdition;

/* a specialized type of unit that can fire two arrows at other units from a distance */
public class Archer extends Unit {
	
	@Override
	void attack(Unit defender) {
		/* simulates an archer shooting two arrows
		 * with every unit of distance (Li) odds of a successful hit decrease */
		
		this.setTurnEnded(true); // unit has used its turn
		int liCheck = GameMethods.getInput("How many Li away is the enemy?", 0, 10);
		liCheck = Math.max(4, 6 - liCheck); // min odds of successful hit is 4/6
		
		for (int i = 1; i < 3; i++) {
			System.out.print("Shot " + i + ": ");
			if (rollDie() <= liCheck) { // must roll 1-4 to hit (pass the 'li check')
				System.out.println("Successful hit for 3 damage");
				this.applyDamage(defender);
			}
			else {
				System.out.println("Target Missed");
			}	
			if (!defender.isAlive()) // check to see if first shot killed target and end turn if so
				break;
			else
				System.out.println("Defender now has " + defender.getHealth() + " health");
		}
	}
	
	public Archer() {
		super(4, 3, "Archer");
	}
}
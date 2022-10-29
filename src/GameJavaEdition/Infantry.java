package GameJavaEdition;

/* a specialized type of unit that can initiate simple battles with other units */
public class Infantry extends Unit {
	
	@Override
	public void attack(Unit defender) {
	/* starts an infantry battle between attacking infantry and defending defender */
		
		//the battle state (adv = 1, neutral = 0, disadv = -1) for the attacking infantry
		//the battle state describes 1 of 3 positions the attacking defender can be in 
		//in battle state 1, the attacking defender does damage upon a successful (1-3) roll
		//in battle state 0, both units can damage each other upon a successful roll
		//in battle state -1, the attacking defender must roll a 1-3 to return the state to neutral
		//rolling unsuccessfully in a adv state does not result in damage, just the reset to neutral
		
		this.setTurnEnded(true); // unit has used its turn
		int battleState = 0;
		
		while (this.isAlive() && defender.isAlive()){
			
			int roll = rollDie();
	
			System.out.println("\nRolling...");
			if (roll <= 3 && battleState >= 0) { // good roll + good/neutral position
				battleState = 1;
				System.out.println("Roll is " + roll
					+ "\ndefender is hit for " + this.getAttack() + " damage"
					+ "\ndefender now has " + (defender.getHealth() - getAttack()) + " health");
				this.applyDamage(defender);
				
			} else if (roll > 3 && battleState <= 0) { // bad roll + bad/neutral position
				battleState = -1;
				System.out.println("Roll is " + roll
					+ "\nAttacker is hit for " + defender.getAttack() + " damage"
					+ "\nAttacker now has " + (getHealth() - defender.getAttack()) + " health");
				defender.applyDamage(this);
				
			} else { // return to neutral
				battleState = 0;
				System.out.println("Roll is " + roll + "\nReturn to neutral state");
			}
		}
	}
	
	public Infantry() {
		super(10, 3, "Infantry"); // default infantry has 10 hp and 3 atk
	}
}

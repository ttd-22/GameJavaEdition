package GameJavaEdition;

/* a specialized type of unit that can conduct hit-and-run "cavalry raids" */
public class Cavalry extends Unit {
	
	@Override
	void attack(Unit defender) {

		// end cavalry attack if either unit dies or player wishes to stop
		while(this.isAlive() && defender.isAlive() && GameMethods.playerContinues("Attempt a cavalry raid?")) {			
			
			if (rollDie() <= 5) { // 5/6 chance of successful raid hit
				this.applyDamage(defender);
				System.out.println("Successful raid, the target was hit for " + this.getAttack()
								 + " damage and now has " + (defender.getHealth() - getAttack()) + " health.");
			}else {
				System.out.println("Unsuccessful raid, attacking cavalry was hit for " + defender.getAttack()
								 + "damage and now has " + (getHealth() - defender.getAttack()) + " health."); 
				defender.applyDamage(this);
				}
		}
		this.setTurnEnded(true); // unit has used its turn
		System.out.println("Cavalry raid over");
	}		
	
	public Cavalry() {
		super(6, 4, "Cavalry"); // gives units names like Infantry5 and Cavalry1 
	}
}

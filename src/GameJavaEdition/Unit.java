package GameJavaEdition;

import java.util.Random;

/* represents a 'unit' in game, something that has health/attack and can fight other units */
public abstract class Unit {
	private int health;
	private int attack;	
	private String name;
	
	private int numKills = 0;
	private boolean turnEnded = false; // if a unit attacks another then that unit's turn ends 
	
	abstract void attack(Unit defender); // each unit has a unique method of battle
	
	public void applyDamage(Unit recipient) { 
		/* deals damage to a recipient based on Unit's attack
		 * also checks to see if recipient is killed and increments attacker's kill count */
		int newHealth = recipient.getHealth() - this.getAttack();
		recipient.setHealth(newHealth);
		
		if(newHealth <= 0) {
			System.out.println("\n!!!" + recipient + " has been killed by " + this + "!!!\n");
			GameMethods.writeBattleHistory(this + " killed " + recipient + '\n');
			this.incrementNumKills();
		}
	}
	
	public int rollDie() {
		/* simulates rolling a 1-6 die. Attacking unit rolls die to determine if attack is successful. 
		 * May someday be relocated to GameMethods if casting a die is used for purposes outside of battle */
		Random random = new Random();
		return random.nextInt(1, 7);
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public boolean isTurnEnded() { // turn is ended after attacking
		return turnEnded;
	}

	public void setTurnEnded(boolean turnEnded) {
		this.turnEnded = turnEnded;
	}

	public void incrementNumKills() {
		this.numKills += 1;
	}
	
	public int getNumKills() {
		return numKills;
	}
	
	public String toString() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAlive() {
		return (this.health > 0);
	}

	public Unit(int health, int attack, String name) {
		this.health = health;
		this.attack = attack;
		this.name = name;
	}
}

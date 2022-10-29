package GameJavaEdition;

public class Board {

	public static void main(String[] args) {
		System.out.println("Creating army 1: ");
		Unit[][] army1 = GameMethods.createArmy("Kwantung Army");

		System.out.println("Creating army 2: ");
		Unit[][] army2 = GameMethods.createArmy("Eighth Route Army");
			
		GameMethods.playTurn(army1, army2);
	}
}
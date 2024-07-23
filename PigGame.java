package JuegoPIg;
import java.util.Scanner;
public class PigGame {
	// Resultado ganador
	public static final int TARGET_SCORE = 100;

    public static void main(String[] args) {
    	// Variables 
        Scanner scanner = new Scanner(System.in);
        int[] scores = {0, 0}; // Puntuaciones de los jugadores
        int currentPlayer = 0; // Jugador actual
        int aiDifficulty = selectDifficulty(scanner);

        System.out.println("¡Bienvenido al juego de Pig!");
        
        // Menu de decisiones
        while (!isGameOver(scores)) {
            System.out.println("\nTurno del jugador " + (currentPlayer + 1));
            System.out.println("Puntuación del jugador 1: " + scores[0]);
            System.out.println("Puntuación del jugador 2: " + scores[1]);
            System.out.println("Puntuación objetivo: " + TARGET_SCORE);

            playTurn(currentPlayer, scores, scanner, aiDifficulty);

            currentPlayer = (currentPlayer + 1) % 2;
        }

        int winner = (scores[0] >= TARGET_SCORE) ? 1 : 2;
        System.out.println("¡Felicidades! ¡El jugador " + winner + " ha ganado!");

        scanner.close();
    }

    // Seleccion de dificultad
    public static int selectDifficulty(Scanner scanner) {
        System.out.println("Selecciona la dificultad de la IA:");
        System.out.println("1. Fácil");
        System.out.println("2. Medio");
        System.out.println("3. Difícil");
        System.out.print("Ingrese el número correspondiente: ");

        int choice = scanner.nextInt();
        if (choice < 1 || choice > 3) {
            System.out.println("Opción inválida. Se seleccionará la dificultad fácil por defecto.");
            return 1;
        }
        return choice;
    }
    
    // Fin del juego
    public static boolean isGameOver(int[] scores) {
        return scores[0] >= TARGET_SCORE || scores[1] >= TARGET_SCORE;
    }

    //  Mecanismo del juego
    public static void playTurn(int currentPlayer, int[] scores, Scanner scanner, int aiDifficulty) {
        int turnTotal = 0;
        boolean turnOver = false;

        while (!turnOver) {
            char choice;

            if (currentPlayer == 0) {
                System.out.print("Presiona 't' para tirar o 'q' para quedarte: ");
                choice = scanner.next().charAt(0);
            } else {
                // IA controlada por computadora
                choice = getAIChoice(aiDifficulty);
                System.out.println("El jugador 2 elige: " + ((choice == 't') ? "Tirar" : "Quedarse"));
            }

            if (choice == 't') {
                int diceRoll = rollDice();
                System.out.println("Has sacado un " + diceRoll);

                if (diceRoll == 1) {
                    System.out.println("¡Sacaste un 1! Pierdes tus puntos acumulados en este turno.");
                    turnTotal = 0;
                    turnOver = true;
                } else {
                    turnTotal += diceRoll;
                    System.out.println("Puntos en este turno: " + turnTotal);
                }
            } else if (choice == 'q') {
                scores[currentPlayer] += turnTotal;
                System.out.println("Te quedas con " + turnTotal + " puntos en este turno.");
                turnOver = true;
            }
        }
    }

    // Dificultad de la IA
    public static char getAIChoice(int aiDifficulty) {
        double threshold;

        switch (aiDifficulty) {
            case 1:
                threshold = 0.5; // Fácil
                break;
            case 2:
                threshold = 0.7; // Medio
                break;
            case 3:
                threshold = 0.9; // Difícil
                break;
            default:
                threshold = 0.5; // Valor por defecto
                break;
        }

        return (Math.random() < threshold) ? 't' : 'q';
    }

    // Tirada del dado
    public static int rollDice() {
        return (int) (Math.random() * 6) + 1;

	}

}

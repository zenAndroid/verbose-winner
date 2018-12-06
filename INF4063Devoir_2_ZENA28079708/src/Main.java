import java.util.Stack;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		String resultat = "";
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("input.txt"), Charset.defaultCharset());
		} catch (IOException | SecurityException e) {
			System.out.println(
					"Erreur dans le fichier d'entrée, veuillez entrez le code source dans le fichier input.txt");
		}
		for (String string : lines) {
			resultat += generateurCodeObjet(convertirEnPostfix(string)) + "\r\n";
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
			bw.write(resultat);
		}
		System.out.println("Resultat écrit dans output.txt");
	}

	public static int priorite(char item) {
		// reçois un opérateur et retourne sa priorité
		switch (item) {
		case '=':
			return 0;
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		default: // Si ce n'est pas les opérateurs précédent, alors c'est l'opérateur
					// d'exponentiation.
			return 3;
		}
	}

	public static boolean estUnOperateur(char item) {
		return item == '+' || item == '-' || item == '*' || item == '/' || item == '^' || item == '=';
	}

	public static String instructionObjet(char item) {
		// Convertit un opérateur en son instruction objet
		String instruction = null;
		switch (item) {
		case '+':
			instruction = "ADD";
			break;
		case '-':
			instruction = "SOU";
			break;
		case '*':
			instruction = "MUL";
			break;
		case '/':
			instruction = "DIV";
			break;
		case '^':
			instruction = "EXP";
			break;
		case '=':
			instruction = "AFF";
			break;
		}
		return instruction;
	}

	public static boolean estUnRegistre(String arg) {
		return arg.length() == 2;
	}

	public static String convertirEnPostfix(String ExpInf) {
		Stack<String> pile = new Stack<String>();
		String ExpPost = "";
		for (char item : ExpInf.toCharArray()) {
			if (item == '(') {
				pile.push("" + item);
			} else if (Character.isLetter(item)) {
				ExpPost += item;
			} else if (estUnOperateur(item)) {
				while (!pile.empty() && pile.peek().charAt(0) != '('
						&& (priorite(item) <= priorite(pile.peek().charAt(0)))) {
					ExpPost += pile.pop();
				}
				pile.push("" + item);
			} else if (item == ')') {
				while (pile.peek().charAt(0) != '(') {
					ExpPost += pile.pop();
				}
				pile.pop();
			}
		}
		while (!pile.empty()) {
			ExpPost += pile.pop();
		}
		return ExpPost;
	}

	public static String generateurCodeObjet(String ExpressionPostfix) {
		int i = -1; // Variable utilisée pour savoir les registres disponibles, puisque les
					// registres sont R[i] ou i est un entier qui s'incrémente.
		Stack<String> pileResultat = new Stack<String>(); // Stockera les symboles des variables et les registres;
		String Resultat = "";
		for (char item : ExpressionPostfix.toCharArray()) {
			if (Character.isLetter(item)) { // Si l'item est un opérande
				pileResultat.push("" + item);
			}
			if (estUnOperateur(item)) { // Si l'item est un opérateur
				// On sait que tout les opérateur sont binaires, donc on dépile deux arguments.
				String ARG1 = pileResultat.pop();
				String ARG2 = pileResultat.pop();
				if (ARG1.length() == 1 && ARG2.length() == 1) {
					i++;
				}
				if (item != '=') {
					String ARGS = "," + ARG2 + "," + ARG1;
					String REG = "R" + Integer.toString(i);
					pileResultat.push(REG);
					String OP = instructionObjet(item);
					Resultat += OP + " " + REG + ARGS + "\r\n";
				} else {
					String ARGS = " " + ARG2 + "," + ARG1;
					String OP = "AFF";
					Resultat += OP + ARGS + "\r\n";
				}
			}
		}
		Resultat = Resultat.substring(0, Resultat.length() - 1);
		return Resultat;
	}

}
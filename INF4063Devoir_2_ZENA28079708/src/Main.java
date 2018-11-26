import java.util.Stack;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		ArrayList<String> Resultat = new ArrayList<String>(); // Resultat final
		System.out.println(convertirEnPostfix("A = B + F * D"));
//		try (BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"))) {
//			bw.write("abc");
//		}

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

	public static void generateurCodeObjet(String ExpressionPostfix) {
		int i = 0; // Variable utilisée pour savoir les registres disponibles, puisque les
					// registres sont R[i] ou i est un entier qui s'incrémente.
		Stack<String> pileResultat = new Stack<String>(); // Stockera les symboles des variables et les registres;
		String Resultat = "";
		String temporaire = null; // Resultat temporaire
		for (char item : ExpressionPostfix.toCharArray()) {
			if (Character.isLetter(item)) { // Si l'item est un opérande
				pileResultat.push("" + item);
			}
			if (estUnOperateur(item)) { // Si l'item est un opérateur
				// On sait que tout les opérateur sont binaires, donc on dépile les arguments.
				String ARG1 = pileResultat.pop();
				String ARG2 = pileResultat.pop();
				if (item != '=') {
					String ARGS = "," + ARG2 + "," + ARG1;
					String REG = "R" + Integer.toString(i);
					i++;
					String OP = instructionObjet(item);
					Resultat += OP + REG + ARGS + '\n';
				} else {
					String ARGS = " " + ARG2 + "," + ARG1;
					String OP = "AFF";
					Resultat += OP + ARGS + '\n';
				}
			}
		}
	}

	public static String instructionObjet(char item) {
		// Convertit un opérateur en son instruction objet
		String instruction = null;
		switch (item) {
		case '+':
			instruction = "ADD";
		case '-':
			instruction = "SOU";
		case '*':
			instruction = "MUL";
		case '/':
			instruction = "DIV";
		case '^':
			instruction = "EXP";
		case '=':
			instruction = "AFF";
		}
		return instruction;
	}
}

////...other class code
//List<String> lines = null;
//try{
//    lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
//}catch(IOException | SecurityException e){
//    //problem with the file
//}
/*
 * import java.io.*;
 * 
 * public class Test {
 * 
 * public static void main(String[] args) throws IOException { try
 * (BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"))) {
 * bw.write("abc"); } } }
 */
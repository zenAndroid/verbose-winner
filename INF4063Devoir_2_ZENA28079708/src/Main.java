import java.util.Stack;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.io.IOException;
import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println(convertirEnPostfix("A = B + F * D"));
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"))) {
            bw.write("abc");
        }
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
		ArrayList<String> Resultat = new ArrayList<String>(); // Resultat final
		for(char item: ExpressionPostfix.toCharArray()) {
			
		}
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
import java.io.*;

public class Test {
 
    public static void main(String[] args) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"))) {
            bw.write("abc");
        }
    }
}*/
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		System.out.println(convertirEnPostfix("V = B * A * D + A / D"));
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
		Stack<Character> pile = new Stack<Character>();
		String ExpPost = "";
		for (char item : ExpInf.toCharArray()) {
			if (item == '(') {
				pile.push(new Character(item));
			} else if (Character.isLetter(item)) {
				ExpPost += item;
			} else if (estUnOperateur(item)) {
				while (!pile.empty() && pile.peek().charValue() != '('
						&& (priorite(item) <= priorite(pile.peek().charValue()))) {
					ExpPost += pile.pop();
				}
				pile.push(new Character(item));
			} else if (item == ')') {
				while (pile.peek().charValue() != '(') {
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
		
	}
}

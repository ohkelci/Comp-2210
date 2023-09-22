import java.util.Arrays;
import java.util.Comparator;
/**
 * Autocomplete.
 */
public class Autocomplete {

	private Term[] termArray;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
	public Autocomplete(Term[] terms) {
		if (terms == null) {
			throw new java.lang.NullPointerException();
		}

		Arrays.sort(terms);
		termArray = terms;
	}
	/**
	 * Returns all terms that start with the given prefix, in descending order of weight.
	 * This method throws a NullPointerException if prefix is null.
	 */
	public Term[] allMatches(String prefix) {

		if (prefix == null) {
			throw new NullPointerException();
		}

		Term temp = new Term(prefix, 0);

		int i = BinarySearch.firstIndexOf(termArray, temp, Term.byPrefixOrder(prefix.length()));
		int j = BinarySearch.lastIndexOf(termArray, temp, Term.byPrefixOrder(prefix.length()));
		if (i == - 1 || j == - 1) {
			throw new java.lang.NullPointerException();
		}

		int length = prefix.length();
		Term term = new Term(prefix, 0);
		Comparator<Term> comp = termArray[0].byPrefixOrder(length);
		int firstIndex = BinarySearch.<Term>firstIndexOf(termArray, term, comp);
		int lastIndex = BinarySearch.<Term>lastIndexOf(termArray, term, comp);
		if (firstIndex == -1 && lastIndex == -1) {
			return new Term[0];
		}
		int matchesSize = (lastIndex - firstIndex) + 1;
		Term[] allMatches = new Term[matchesSize];

		allMatches = Arrays.copyOfRange(termArray, firstIndex, lastIndex+1);
		Comparator<Term> comp2 = allMatches[0].byDescendingWeightOrder();
		Arrays.sort(allMatches, comp2);
		return allMatches;
	}

	public static void main(String[] args) {

		Term[] terms = new Term[8];
		terms[0] = new Term("auburn football", 1000);
		terms[1] = new Term("auburn university", 700);
		terms[2] = new Term("auburn football schedule", 800);
		terms[3] = new Term("auburn university", 500);
		terms[4] = new Term("auburn hair", 1000);
		terms[5] = new Term("aubuchon", 150);
		terms[6] = new Term("auburn ca", 500);
		terms[7] = new Term("auburn mall", 250);
		Autocomplete a = new Autocomplete(terms);
		Term[] results = new Term[8];
		results = a.allMatches("aubu");
		for (Term b : results) {
			System.out.println(b);
		}
	}
}

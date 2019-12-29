package tk.valoeghese.motjin.util;

public final class Tuple<A, B> {
	public Tuple(A left, B right) {
		this.left = left;
		this.right = right;
	}

	public final A left;
	public final B right;
	
	public static <A, B> Tuple<A, B> of(A left, B right) {
		return new Tuple<>(left, right);
	}
}

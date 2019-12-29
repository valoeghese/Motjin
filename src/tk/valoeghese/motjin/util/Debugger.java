package tk.valoeghese.motjin.util;

public class Debugger {
	private Debugger(int count, String name) {
		this.counter = count;
		this.title = name + ": ";
	}

	private int counter = 0;
	private final String title;

	public void listen(String[] arr) {
		if (this.counter > 0) {
			this.counter--;
			System.out.println(this.title);

			for (String s : arr) {
				System.out.println(s);
			}
		}
	}

	public void listen(String s) {
		if (this.counter > 0) {
			this.counter--;
			System.out.print(this.title);
			System.out.println(s);
		}
	}

	public static Debugger of(int count, String name) {
		return new Debugger(count, name);
	}
}

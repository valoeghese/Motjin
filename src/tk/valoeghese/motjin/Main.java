package tk.valoeghese.motjin;

import tk.valoeghese.motjin.map.parser.ObfuscationMap;

public class Main {
	public static MainArgs options;

	public static void main(String[] args) {
		options = MainArgs.of(args);

		ObfuscationMap mojmap = ObfuscationMap.parseProguard(options.mojMap);
		System.out.println("done");
		System.out.println(mojmap.getClassEntry("bme"));
	}
}

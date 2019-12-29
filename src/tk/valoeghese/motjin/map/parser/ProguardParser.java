package tk.valoeghese.motjin.map.parser;

import tk.valoeghese.motjin.map.ClassEntry;
import tk.valoeghese.motjin.map.Descriptor;

public class ProguardParser extends ObfuscationMap {
	private ClassEntry recent;

	@Override
	protected void parseLine(String line) {
		char firstChar = line.charAt(0);

		if (firstChar != '#') {
			if (firstChar == ' ') {
				String lineTrim = line.trim();

				if (Character.isDigit(lineTrim.charAt(0))) {
					parseMethod(lineTrim.split(":")[2].split(" "));
				} else {
					parseField(lineTrim.split(" "));
				}
			} else {
				this.parseClass(line.trim().split(" "));
			}
		}
	}

	private void parseField(String[] in) {
		String descriptor = Descriptor.of(proguardToTiny(in[0]));
		String mappedName = in[1].trim();
		String obfName = in[2].trim();

		this.recent.addField(obfName, mappedName, descriptor);
	}

	private void parseMethod(String[] in) {
		String returnType = Descriptor.of(proguardToTiny(in[0]));
		String[] methodSplit = in[1].split("(");

		String mappedName = methodSplit[0].trim();
		String signature = parseMethodSignature(returnType, methodSplit[1]);
		String obfName = in[3].trim();

		this.recent.addMethod(obfName, mappedName, signature);
	}

	private String parseMethodSignature(String returnType, String parameters) {
		String[] paramArr = parameters.substring(0, parameters.length()).split(",");
		StringBuilder descriptor = new StringBuilder("(");

		for (String proguardParam : paramArr) {
			descriptor.append(Descriptor.of(proguardToTiny(proguardParam)));
		}

		descriptor.append(returnType);
		return descriptor.toString();
	}

	private void parseClass(String[] in) {
		String obfName = proguardToTiny(in[2]);
		obfName = obfName.substring(0, obfName.length() - 2); // remove ":" character

		ClassEntry classEntry = new ClassEntry.Builder()
				.obfName(obfName)
				.mappedName(proguardToTiny(in[0])) 
				.build();

		this.entries.put(obfName, classEntry);
		this.recent = classEntry;
	}

	private static String proguardToTiny(String in) {
		// convert proguard "." for package separation to "/"
		return in.trim().replace('.', '/');
	}
}

package tk.valoeghese.mojtin.map.parser;

import tk.valoeghese.mojtin.map.ClassEntry;
import tk.valoeghese.mojtin.map.Descriptor;

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
				}
			} else {
				this.parseClass(line.trim().split(" "));
			}
		}
	}

	private void parseMethod(String[] in) {
		String returnType = Descriptor.of(proguardToTiny(in[0]));
		String[] methodSplit = in[1].split("(");

		String mappedName = methodSplit[0].trim();
		String descriptor = parseMethodDescriptor(returnType, methodSplit[1]);
		String obfName = in[3];
	}

	private String parseMethodDescriptor(String returnType, String string) {
		// TODO Auto-generated method stub
		return null;
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

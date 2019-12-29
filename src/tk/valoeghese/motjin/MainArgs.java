package tk.valoeghese.motjin;

import java.util.HashMap;
import java.util.Map;

public class MainArgs {
	private MainArgs(TempArgs temp) {
		mojMap = temp.getString("mojmap", "./client.txt");
		intermediaryMap = temp.getString("intermediary", "./mappings.tiny");
		outputFile = temp.getString("o", "./mojmap.tiny");
	}

	public final String mojMap;
	public final String intermediaryMap;
	public final String outputFile;

	public static MainArgs of(String[] args) {
		Map<String, String> valueMap = new HashMap<>();
		boolean valueFlag = false;
		String keyCache = "";

		for (String s : args) {

			if (valueFlag) {
				valueFlag = false;
				valueMap.put(keyCache, s);
			} else {

				if (s.startsWith("-")) {

					if (s.startsWith("--")) {
						valueMap.put(s.substring(2), "true");
					} else {
						valueFlag = true;
						keyCache = s.substring(1);
					}
				}
			}
		}

		return new MainArgs(new TempArgs(valueMap));
	}
}

class TempArgs {
	final Map<String, String> valueMap;

	TempArgs(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}


	boolean getBoolean(String key) {
		String result = valueMap.getOrDefault(key, "false");

		try {
			return Boolean.valueOf(result);
		} catch (ClassCastException e) {
			return false;
		}
	}

	String getString(String key, String defaultValue) {
		return valueMap.getOrDefault(key, defaultValue);
	}
}

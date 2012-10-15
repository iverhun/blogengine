package ua.com.blogengine.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TransliterationUtil {

    private static Map<String, String> rules = new HashMap<String, String>();

    static {
        rules.put("а", "a");
        rules.put("б", "b");
        rules.put("в", "v");
        rules.put("г", "h");
        rules.put("ґ", "g");
        rules.put("д", "d");
        rules.put("е", "e");
        rules.put("є", "ye");
        rules.put("ж", "zh");
        rules.put("з", "z");
        rules.put("и", "y");
        rules.put("\u0456", "i");
        rules.put("ї", "yi");
        rules.put("й", "y");
        rules.put("к", "k");
        rules.put("л", "l");
        rules.put("м", "m");
        rules.put("н", "n");
        rules.put("о", "o");
        rules.put("п", "p");
        rules.put("р", "r");
        rules.put("с", "s");
        rules.put("т", "t");
        rules.put("у", "u");
        rules.put("ф", "f");
        rules.put("х", "h");
        rules.put("ц", "ts");
        rules.put("ч", "ch");
        rules.put("ш", "sh");
        rules.put("щ", "shch");
        rules.put("ь", "");
        rules.put("ю", "yu");
        rules.put("я", "ya");

        rules.put("А", "A");
        rules.put("Б", "B");
        rules.put("В", "V");
        rules.put("Г", "H");
        rules.put("Д", "G");
        rules.put("Е", "D");
        rules.put("Є", "Ye");
        rules.put("Ж", "Zh");
        rules.put("З", "Z");
        rules.put("И", "Y");
        rules.put("\u0406", "I");
        rules.put("Ї", "Yi");
        rules.put("Й", "Y");
        rules.put("К", "K");
        rules.put("Л", "L");
        rules.put("М", "M");
        rules.put("Н", "N");
        rules.put("О", "O");
        rules.put("П", "P");
        rules.put("Р", "R");
        rules.put("С", "S");
        rules.put("Т", "T");
        rules.put("У", "U");
        rules.put("Ф", "F");
        rules.put("Х", "H");
        rules.put("Ц", "Ts");
        rules.put("Ч", "Ch");
        rules.put("Ш", "Sh");
        rules.put("Щ", "Shch");
        rules.put("Ю", "Yu");
        rules.put("Ь", "");
        rules.put("Я", "Ya");

        rules.put(",", "");
        rules.put(":", "");
        rules.put(";", "");
    }

    public static String toLatin(String cyrylic) {
        String latin = cyrylic.replaceAll("\\s+", "-");

        for (Entry<String, String> rule : rules.entrySet()) {
            latin = latin.replaceAll(rule.getKey(), rule.getValue());
        }

        return latin;
    }

    public static void main(String... args) {
        System.out.println(toLatin("абабAгаламага Їжачок    ЮРІЙ Кицька Test English text ура :)"));
    }
}

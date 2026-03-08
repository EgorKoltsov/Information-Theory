import java.util.Arrays;

public abstract class Cipher
{
    static protected Character[] alphabet;

    final static Character[] RussianAlphabet = new Character[]
            {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т',
                    'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'};
    final static Character[] EnglishAlphabet = new Character[]
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    abstract String Encrypt(String text, String key);
    abstract String Decrypt(String text, String key);

    Cipher(Character[] alphabet)
    {
        Cipher.alphabet = alphabet;
    }

    boolean ValidateInput(String text, String key)
    {
        return !text.isEmpty() && !key.isEmpty();
    }

    public static String filterByAlphabet(String inputText)
    {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < inputText.length(); i++)
            if (Arrays.stream(alphabet).toList().contains(inputText.charAt(i)))
                res.append(inputText.charAt(i));
        return res.toString();
    }

    public static String truncateKey(String key, String inputText)
    {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < Math.min(key.length(), inputText.length()); i++)
        {
            res.append(key.charAt(i));
        }
        return res.toString();
    }
}
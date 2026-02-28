import java.util.Arrays;

public abstract class Cipher
{
    static protected Character[] alphabet;

    final static Character[] RussianAlphabet = new Character[]
            {'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т',
                    'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', ' '};

    abstract String Encrypt(String text, String key);
    abstract String Decrypt(String text, String key);

    Cipher(Character[] alphabet)
    {
        Cipher.alphabet = alphabet;
    }

    boolean ValidateInput(String text, String key)
    {
        for (char c : text.toCharArray())
        {
            if (!Arrays.asList(alphabet).contains(c))
                return false;
        }

        for (char c : key.toCharArray())
        {
            if (!Arrays.asList(alphabet).contains(c))
                return false;
        }
        return true;
    }
}
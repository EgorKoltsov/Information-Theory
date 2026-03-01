import java.util.Arrays;

public class VignereCipher extends Cipher
{
    VignereCipher(Character[] alphabet)
    {
        super(alphabet);
    }

    String Encrypt(String text, String key){
        StringBuilder newKey = new StringBuilder(key);
        if (key.length() < text.length())
            newKey.append(text, 0, text.length() - key.length());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < newKey.length(); i++)
            result.append(alphabet[(Arrays.asList(alphabet).indexOf(newKey.charAt(i)) + Arrays.asList(alphabet).indexOf(text.charAt(i))) % alphabet.length]);
        return result.toString();
    }

    String Decrypt(String text, String key) {
        StringBuilder newKey = new StringBuilder(key);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++)
        {
            int keyCharIndex = Arrays.asList(alphabet).indexOf(newKey.charAt(i));
            int textCharIndex = Arrays.asList(alphabet).indexOf(text.charAt(i));
            result.append(alphabet[textCharIndex >= keyCharIndex ?
                    (textCharIndex - keyCharIndex) % alphabet.length : alphabet.length - (keyCharIndex - textCharIndex)]);
            newKey.append(result.charAt(i));
        }
        return result.toString();
    }
}
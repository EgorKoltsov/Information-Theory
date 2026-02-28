import java.util.Arrays;

public class VignereCipher extends Cipher
{
    Character[][] Table;

    VignereCipher(Character[] alphabet)
    {
        super(alphabet);

        // Генерируем таблицу Вижнера для задаваемого алфавита
        Table = new Character[alphabet.length][];
        for (int i = 0; i < Table.length; i++)
            Table[i] = new Character[alphabet.length];
        for (int i = 0; i < alphabet.length; i++)
        {
            System.arraycopy(alphabet, i, Table[i], 0, alphabet.length - i);
            System.arraycopy(alphabet, 0, Table[i], alphabet.length - i, i);
        }
    }

    String Encrypt(String text, String key){
        StringBuilder newKey = new StringBuilder(key);
        if (key.length() < text.length())
            newKey.append(text, 0, text.length() - key.length());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
        {
            result.append(Table[Arrays.asList(alphabet).indexOf(newKey.charAt(i))][Arrays.asList(alphabet).indexOf(text.charAt(i))]);
        }
        return result.toString();
    }

    String Decrypt(String text, String key) {
        int index = 0;

        StringBuilder newKey = new StringBuilder(key);
        StringBuilder result = new StringBuilder();

        while (index < text.length())
        {
            for (int i = 0; i < Math.min(index + newKey.length(), text.length()); i++)
            {
                for (int j = 0; j < Table[0].length; j++)
                    if (Table[Arrays.asList(alphabet).indexOf(newKey.charAt(i))][j] == text.charAt(index))
                    {
                        result.append(alphabet[j]);
                        newKey.append(alphabet[j]);
                        break;
                    }
                index++;
            }
        }
        return result.toString();
    }
}
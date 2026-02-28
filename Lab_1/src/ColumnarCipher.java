public class ColumnarCipher extends Cipher
{
    ColumnarCipher(Character[] alphabet)
    {
        super(alphabet);
    }

    String Encrypt(String text, String key){
        Character[][] matrix = new Character[text.length() / key.length() + (text.length() % key.length() != 0 ? 1 : 0)][];

        // Выделяем память под содержимое строк матрицы
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = new Character[key.length()];

        // Последовательно заполняем матрицу шифруемым текстом
        for (int i = 0; i < text.length(); i++)
            matrix[i / key.length()][i % key.length()] = text.charAt(i);

        // Забиваем оставшееся место пробелами
        for (int i = text.length(); i < matrix.length * key.length(); i++)
            matrix[matrix.length - 1][i % key.length()] = ' ';

        StringBuilder result = new StringBuilder();

        for (Character c : alphabet)
        {
            for (int i = 0; i < key.length(); i++)
            {
                if (key.charAt(i) == c)
                {
                    for (Character[] characters : matrix)
                        result.append(characters[i]);
                }
            }
        }
        return result.toString();
    }

    String Decrypt(String text, String key) {
        Character[][] matrix = new Character[text.length() / key.length() + (text.length() % key.length() != 0 ? 1 : 0)][];

        // Выделяем память под содержимое строк матрицы
        for (int i = 0; i < matrix.length; i++)
            matrix[i] = new Character[key.length()];

        int currIndex = text.length() - 1;
        for (int k = alphabet.length - 1; k >= 0; k--)
        {
            for (int i = key.length() - 1; i >= 0; i--)
            {
                if (key.charAt(i) == alphabet[k])
                {
                    for (int j = matrix.length - 1; j >= 0; j--)
                        matrix[j][i] = text.charAt(currIndex--);
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (Character[] characters : matrix)
            for (Character c : characters)
                result.append(c);
        return result.toString();
    }
}
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Form extends JFrame implements ActionListener {
    JRadioButtonMenuItem rBtnColumnarCipher;
    JRadioButtonMenuItem rBtnVignereCipher;
    boolean ColumnarAlgorithmChoosed = true;
    JButton btnInputFileOpen;
    JButton btnOutputFileSave;
    JButton btnEncrypt;
    JButton btnDecrypt;
    JTextArea inputTextArea;
    JTextField keyTextField;

    JTextArea outputTextArea;

    public Form()
    {
        super("Lab_1");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(null);
        setResizable(false);

        setSize(850, 550);

        JLabel txtAlgoChoosement = new JLabel("Выбор алгоритма шифрования:");
        txtAlgoChoosement.setBounds(10, 10, 300, 20);
        txtAlgoChoosement.setFont(new Font("Serif", Font.BOLD, 20));
        add(txtAlgoChoosement);

        JLabel txtInputText = new JLabel("Исходный текст:");
        txtInputText.setBounds(10, 120, 300, 30);
        txtInputText.setFont(new Font("Serif", Font.BOLD, 20));
        add(txtInputText);

        inputTextArea = new JTextArea();
        inputTextArea.setBounds(10, 150, 300, 300);
        add(inputTextArea);

        JLabel txtOutputText = new JLabel("Обработанный текст:");
        txtOutputText.setBounds(500, 120, 300, 30);
        txtOutputText.setFont(new Font("Serif", Font.BOLD, 20));
        add(txtOutputText);

        outputTextArea = new JTextArea();
        outputTextArea.setBounds(500, 150, 300, 300);
        add(outputTextArea);

        btnInputFileOpen = new JButton("Открыть");
        btnInputFileOpen.setBounds(10, 460, 200, 20);
        btnInputFileOpen.addActionListener(this);
        add(btnInputFileOpen);

        btnOutputFileSave = new JButton("Сохранить");
        btnOutputFileSave.setBounds(500, 460, 200, 20);
        btnOutputFileSave.addActionListener(this);
        add(btnOutputFileSave);

        btnEncrypt = new JButton("Шифровать");
        btnEncrypt.setBounds(320, 180, 170, 20);
        btnEncrypt.addActionListener(this);
        add(btnEncrypt);

        btnDecrypt = new JButton("Дешифровать");
        btnDecrypt.setBounds(320, 250, 170, 20);
        btnDecrypt.addActionListener(this);
        add(btnDecrypt);

        JLabel lblKey = new JLabel("Ключ:");
        lblKey.setBounds(320, 300, 170, 20);
        add(lblKey);

        keyTextField = new JTextField();
        keyTextField.setBounds(320, 320, 170, 20);
        add(keyTextField);

        ButtonGroup btnGrp = new ButtonGroup();
        rBtnColumnarCipher = new JRadioButtonMenuItem("Столбоцовый алгоритм шифрования");
        rBtnColumnarCipher.setBounds(10, 40, 400, 20);
        rBtnColumnarCipher.addActionListener(this);
        rBtnColumnarCipher.doClick();
        rBtnColumnarCipher.setFont(new Font("Serif", Font.BOLD, 20));
        add(rBtnColumnarCipher);

        rBtnVignereCipher = new JRadioButtonMenuItem("Алгоритм шифрования Вижнера");
        rBtnVignereCipher.setBounds(10, 70, 400, 20);
        rBtnVignereCipher.addActionListener(this);
        rBtnVignereCipher.setFont(new Font("Serif", Font.BOLD, 20));
        add(rBtnVignereCipher);

        btnGrp.add(rBtnColumnarCipher);
        btnGrp.add(rBtnVignereCipher);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rBtnColumnarCipher)
        {
            ColumnarAlgorithmChoosed = true;
        }
        else if (e.getSource() == rBtnVignereCipher)
        {
            ColumnarAlgorithmChoosed = false;
        }
        else if (e.getSource() == btnInputFileOpen)
        {
            JFileChooser fileOpener = new JFileChooser();
            fileOpener.showOpenDialog(this);

            File file = fileOpener.getSelectedFile();
            if (file != null)
            {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    inputTextArea.setText("");

                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        inputTextArea.append(line);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if (e.getSource() == btnOutputFileSave)
        {
            JFileChooser fileSaver = new JFileChooser();
            int result = fileSaver.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileSaver.getSelectedFile();
                FileWriter fw;

                try {
                    fw = new FileWriter(file);
                    fw.write(outputTextArea.getText());
                    fw.flush();
                    fw.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        else if (e.getSource() == btnEncrypt || e.getSource() == btnDecrypt)
        {
            String key = keyTextField.getText();
            String inputText = inputTextArea.getText();

            Cipher cipher;
            if (ColumnarAlgorithmChoosed)
                cipher = new ColumnarCipher(Cipher.RussianAlphabet);
            else
                cipher = new VignereCipher(Cipher.RussianAlphabet);

            if (cipher.ValidateInput(inputText, key))
            {
                if (e.getSource() == btnEncrypt)
                    outputTextArea.setText(cipher.Encrypt(inputText, key));
                else
                {
                    // Дополнительная проверка (если дешифруем столбцовым алгоритмом, то длина текста должна быть кратна длине ключа)
                    if (ColumnarAlgorithmChoosed) {
                        if (inputText.length() % key.length() == 0)
                            outputTextArea.setText(cipher.Decrypt(inputText, key));
                        else
                            outputTextArea.setText("Ошибка: при дешифрации текста столбцовым \nалгоритмом длина текста должна быть кратна \nдлине ключа");
                    }
                    else
                        outputTextArea.setText(cipher.Decrypt(inputText, key));
                }
            }
            else
                outputTextArea.setText("Ошибка: ключ и шифруемый текст должны \nсодержать только буквы русского алфавита \nв верхнем регистре и пробелы");
        }
    }
}

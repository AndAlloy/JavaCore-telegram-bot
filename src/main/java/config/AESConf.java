package config;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class AESConf {
    private static SecretKeySpec secretKey;
    public static synchronized SendMessage sendEncryptedMessage(String chat_id, String originalString){
        var secretKey = aplhaNumericString();
        if (originalString!=null) {
            String encryptedString = encrypt(originalString, secretKey) ;
            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("⛓ *Зашифроване повідомлення:* ` \n\n\t" +encryptedString+"`"+"\n\n"+" \uD83D\uDD11 *Ключ:*\n\n\t `"+secretKey+"`")
                    .build(); // Sending our message object to user
        }else{

            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("Помилка введення")
                    .build();
        }
    }
    //@SneakyThrows
    public static synchronized SendMessage sendDecryptedMessage(String chat_id, String encryptedString, String secretKey){
        if (encryptedString!=null) {
            String decryptedString = decrypt(encryptedString, secretKey) ;
            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("⌨️ *Розшифроване повідомлення:* ` \n\n\t" +decryptedString+"`")
                    .build(); // Sending our message object to user
        }else{
            return SendMessage // Create a message object object
                    .builder()
                    .chatId(chat_id)
                    .parseMode("Markdown")
                    .text("Помилка введення")
                    .build();
        }
    }
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            var cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    public static String aplhaNumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        var random = new Random();

        var generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        generatedString += "=";
        return generatedString;
    }
    public static synchronized void setAesButton(SendMessage sendMessage) {
        // Create a keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        // Create a list of keyboard rows
        List<KeyboardRow> keyboard = new ArrayList<>();
        // First keyboard row
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardFirstRow2 = new KeyboardRow();
        // Add buttons to the keyboard row
        KeyboardButton key1 = new KeyboardButton("Зашифрувати");
        KeyboardButton key2 = new KeyboardButton("Розшифрувати");
        key1.getRequestLocation();
        keyboardFirstRow.add(key1);
        keyboardFirstRow2.add(key2);
        // Add all of the keyboard rows to the list
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardFirstRow2);

        // and assign this list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}

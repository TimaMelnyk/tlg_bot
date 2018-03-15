package com;

/**
 * Created by yaoun on 17.08.2017.
 */
import com.controllers.StartController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new StartController());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
/*

if (message != null && (message.hasText() || update.getMessage().hasPhoto())) {
        if (update.hasMessage() && update.getMessage().hasPhoto()) {
        // Message contains photo
        // Set variables
        long chat_id = update.getMessage().getChatId();

        // Array with photo objects with different sizes
        // We will get the biggest photo from that array
        List<PhotoSize> photos = update.getMessage().getPhoto();
        // Know file_id
        String f_id = photos.stream()
        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
        .findFirst()
        .orElse(null).getFileId();
        // Know photo width
        int f_width = photos.stream()
        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
        .findFirst()
        .orElse(null).getWidth();
        // Know photo height
        int f_height = photos.stream()
        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
        .findFirst()
        .orElse(null).getHeight();
        // Set photo caption
        String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
        SendPhoto msg = new SendPhoto()
        .setChatId(chat_id)
        .setPhoto(f_id)
        .setCaption(caption);
        try {
        sendPhoto(msg); // Call method to send the photo with caption
        } catch (TelegramApiException e) {
        e.printStackTrace();
        }
        }
        else if (message.getText().equals("/help"))
        sendMsg(message, "Привет, я робот");
        else if (message.getText().equals("/markup")) {
        SendMessage sendMessage = new SendMessage() // Create a message object object
        .setChatId(message.getChatId())
        .setText("Here is your keyboard");
        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        // Create another keyboard row
        row = new KeyboardRow();
        // Set each button for the second line
        row.add("Row 2 Button 1");
        row.add("Row 2 Button 2");
        row.add("Row 2 Button 3");
        // Add the second row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        // Add it to the message
        sendMessage.setReplyMarkup(keyboardMarkup);
        try {
        execute(sendMessage); // Sending our message object to user
        } catch (TelegramApiException e) {
        e.printStackTrace();
        }
        }

        else if (message.getText().equals("Row 1 Button 1")) {
        SendPhoto msg = new SendPhoto()
        .setChatId(message.getChatId())
        .setPhoto("AgADAgADL6gxG7f2gEsRguWJwhj2WzLWMg4ABB_j0MB4QoZfewIAAgI")
        .setCaption("Photo");
        try {
        sendPhoto(msg); // Call method to send the photo
        } catch (TelegramApiException e) {
        e.printStackTrace();
        }
        }
        else
        sendMsg(message, "Я не знаю что ответить на это");
        } */
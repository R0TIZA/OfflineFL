package ru.rotiza.offlinefl.srvice.factory;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardFactory {

    public InlineKeyboardMarkup getInlineKeyboard(
            @NotNull List<String> text,
            @NotNull List<String> data,
            @NotNull List<Integer> configuration
    ){
        if(text.size()!= data.size()) throw new IllegalArgumentException("Parameters text and data must be the same length");
        else {
            int counter = 0;
            for(Integer buttonsAmount : configuration) counter += buttonsAmount;
            if (text.size()!=counter) throw new IllegalArgumentException("Amount of buttons must be equals to text/data length");
        }

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        int globalIndex = 0;
        for(Integer buttonsAmount : configuration){
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int buttonIndex = 0; buttonIndex < buttonsAmount; buttonIndex++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText(text.get(globalIndex));
                button.setCallbackData(data.get(globalIndex));
                row.add(button);
                globalIndex++;
            }
            keyboard.add(row);
        }
        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    public ReplyKeyboardMarkup getReplyKeyboard(
            @NotNull List<String> text,
            @NotNull List<Integer> configuration
    ){
        int counter = 0;
        for(Integer buttonsAmount : configuration) counter += buttonsAmount;
        if (text.size()!=counter) throw new IllegalArgumentException("Amount of buttons must be equals to text length");

        List<KeyboardRow> keyboard = new ArrayList<>();
        int globalIndex = 0;
        for(Integer buttonsAmount : configuration){
            KeyboardRow row = new KeyboardRow();
            for (int buttonIndex = 0; buttonIndex < buttonsAmount; buttonIndex++) {
                KeyboardButton button = new KeyboardButton();
                button.setText(text.get(globalIndex));
                row.add(button);
                globalIndex++;
            }
            keyboard.add(row);
        }
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

}

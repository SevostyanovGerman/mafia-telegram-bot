import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ButtonsHandler {
	public final static InlineKeyboardButton AUTH_BUTTON = new InlineKeyboardButton().setText("Начать игру").setCallbackData("/startGame");
}

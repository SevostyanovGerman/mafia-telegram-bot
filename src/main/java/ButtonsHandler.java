import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class ButtonsHandler {
	public final static InlineKeyboardButton OUT_BUTTON = new InlineKeyboardButton().setText("Выход из системы").setCallbackData("/out");
	public final static InlineKeyboardButton CANCEL_BUTTON = new InlineKeyboardButton().setText("Отмена").setCallbackData("/cancel");
	public final static InlineKeyboardButton CHOOSE_TABLE_BUTTON = new InlineKeyboardButton().setText("Выберите стол для посадки клиентов").setCallbackData("/chooseTable");
	public final static InlineKeyboardButton AUTH_BUTTON = new InlineKeyboardButton().setText("Аунтификация").setCallbackData("/auth");
	public final static InlineKeyboardButton CHANGE_SEAT_TIME_BUTTON = new InlineKeyboardButton().setText("Изменить время").setCallbackData("/change");
	public final static InlineKeyboardButton LEAVE_SEAT_TIME_BUTTON = new InlineKeyboardButton().setText("Оставить текущее время").setCallbackData("/leave");
	public final static InlineKeyboardButton ADD_TABLE_DESCRIPTION_BUTTON = new InlineKeyboardButton().setText("Добавить описание стола").setCallbackData("/yes");
	public final static InlineKeyboardButton SKIP_TABLE_DESCRIPTION_BUTTON = new InlineKeyboardButton().setText("Пропустить описание стола").setCallbackData("/no");
}

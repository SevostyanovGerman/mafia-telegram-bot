import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;

public class MafiaTelegramBot extends TelegramLongPollingBot {

	private static List<String> context = Lists.newArrayList();
	private static Map<Long, Game> gameMap = new HashMap<>(); //ключ - chatID, на 1 юзера 1 игра

	private final static Logger logger = LogManager.getRootLogger();

	//в этом методе порядок строк влияет на работоспособность приложения.
	@Override
	public void onUpdateReceived(Update update) {

		if (update.hasMessage() && update.getMessage().hasText()) {

			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();

			if (messageText.equalsIgnoreCase("/start")) {
				printHelloOnStart(chatId);
				return;
			}

			String lastAction = Iterables.getLast(context, "");

			switch (lastAction) {  //default value != null => предупреждение обработано
				case "startGame":
					if ((NumberUtils.isDigits(messageText))) {
						gameMap.put(chatId, new Game(Integer.parseInt(messageText)));
						sendMessageWithText(chatId, "Роли на эту игру, пожалуйста, раздайте краты участникам");
					} else {
						sendMessageWithText(chatId, "Введите целое число участников");
					}
			}

		} else if (update.hasCallbackQuery()) {
			String callData = update.getCallbackQuery().getData();
			long chatId = update.getCallbackQuery().getMessage().getChatId();

			if (callData.equals("/startGame")) {
				context.add("startGame");
				sendMessageWithText(chatId, "Введите количество участников");
				return;
			}

		}
	}


//			if (context.contains("/auth") && !authDetails.containsKey("username")) {
//				sendMessageWithText(chatId, "Введите пароль");
//				authDetails.put("username", messageText);
//				return;
//			}
//
//			if (context.contains("/auth") && authDetails.containsKey("username")) {
//				authDetails.put("password", messageText);
//				sessionHandler.setAuthDetails(authDetails);
//				User cafeUser = sessionHandler.hasUserWithThisLoginOnServer();
//				if (isEmptyUser(cafeUser)) {
//					sendMessageWithText(chatId, cafeUser.getFirstName() + ", здравствуйте!");
//					printMainMenu(chatId);
//					context.clear();
//				} else {
//					sendMessageWithTextAndInlineKeyboard(chatId, "Вы ввели неправильный логин/пароль попробуйте войти заново \n", AUTH_BUTTON);
//					authDetails.clear();
//					context.clear();
//				}
//				return;
//			}
//
//			if (context.contains("/chooseTable") && !tableDetails.containsKey("boardId")) {
//				try {
//					sendMessageWithTextAndInlineKeyboard(chatId, "Ок, сколько человек будет сидеть за столом " + sessionHandler.getBoardById(messageText) + "\n", CANCEL_BUTTON);
//				} catch (IOException e) {
//					sendMessageWithText(chatId, "Смена еще не началась");
//					return;
//				}
//				tableDetails.put("boardId", messageText);
//				return;
//			}
//
//			if (tableDetails.containsKey("boardId") &&
//					!tableDetails.containsKey("number") &&
//					!(messageText.equals("/yes") ||
//							messageText.equals("/no"))) {
//
//				sendMessageWithTextAndInlineKeyboard(chatId, "Хотите добавить описание стола? \n", ADD_TABLE_DESCRIPTION_BUTTON, SKIP_TABLE_DESCRIPTION_BUTTON, CANCEL_BUTTON);
//				tableDetails.put("number", messageText);
//				return;
//			}
//
//			if (context.contains("/chooseTable") &&
//					tableDetails.containsKey("boardId") &&
//					tableDetails.containsKey("number") &&
//					context.contains("/yes") &&
//					!context.contains("/change")) {
//
//				sendMessageWithTextAndInlineKeyboard(chatId, "Хотите изменить время посадки? \n", CHANGE_SEAT_TIME_BUTTON, LEAVE_SEAT_TIME_BUTTON, CANCEL_BUTTON);
//				tableDetails.put("description", messageText);
//				return;
//			}
//
//
//
//			if (context.contains("/chooseTable") &&
//					tableDetails.containsKey("boardId") &&
//					tableDetails.containsKey("number") &&
//					context.contains("/change")) {
//
//				tableDetails = parceInputTime(messageText, tableDetails);
//				if (tableDetails.containsKey("hours") && tableDetails.containsKey("minutes")) {
//					sessionHandler.sendRequestOnAddTable(tableDetails);
//					sessionHandler.sendEditClientTimeStart(tableDetails);
//					sendMessageWithText(chatId, "Счёт добавлен в систему");
//					//выходим из меню, но остаётся аунтификация
//					context.clear();
//					tableDetails.clear();
//					printMainMenu(chatId);
//				} else {
//					sendMessageWithTextAndInlineKeyboard(chatId, "Вы ввели время в неправильном формате или указали неправильное время попробуйте еще раз изменить время \n", CHANGE_SEAT_TIME_BUTTON);
//					context.remove("/change");
//				}
//			}
//		} else if (update.hasCallbackQuery()) {
//
//			String callData = update.getCallbackQuery().getData();
//			long chatId = update.getCallbackQuery().getMessage().getChatId();
//
//			if (callData.equals("/auth")) {
//				sendMessageWithText(chatId, "Введите логин");
//				context.add(callData);
//				return;
//			}
//			if (callData.equals("/out")) {
//				context.clear();
//				authDetails.clear();
//				tableDetails.clear();
//				printOut(chatId);
//				return;
//			}
//			if (callData.equals("/cancel")) {
//				context.clear();
//				tableDetails.clear();
//				printMainMenu(chatId);
//				return;
//			}
//			if (callData.equals("/auth") && authDetails.containsKey("username") && authDetails.containsKey("password")) {
//				sendMessageWithTextAndInlineKeyboard(chatId, "Вы уже авторизованы, выйдите из системы чтобы зайти под другим пользователем \n", OUT_BUTTON);
//				return;
//			}
//			//если у нас нет записи о том что мы заходили в меню аунтификации
//			if (!(context.contains("/auth")) && callData.equals("/auth")) {
//				sendMessageWithText(chatId, "Введите логин");
//				context.add(callData);
//				return;
//			}
//
//			//если у нас есть пароль, логин и нас просят добавить стол, а так же мы еще не заходили в это меню
//			if (authDetails.containsKey("username") &&
//					authDetails.containsKey("password") &&
//					callData.equals("/chooseTable") &&
//					!context.contains("/chooseTable")) {
//
//
//				try {
//					List boardList = sessionHandler.getBoardListName();
//					if (!boardList.isEmpty()) {
//						sendMessageWithTextAndInlineKeyboard(chatId, "Выберите стол \n", boardList, sessionHandler.getBoardListId());
//					} else {
//						sendMessageWithTextAndInlineKeyboard(chatId, "В кафе нет созданных столов!\n");
//					}
//				} catch (IOException e) {
//					sendMessageWithText(chatId, "Смена еще не началась");
//					return;
//				}
//				context.add(callData);
//				return;
//			}
//
//			if (context.contains("/chooseTable") && !tableDetails.containsKey("boardId")) {
//				try {
//					sendMessageWithTextAndInlineKeyboard(chatId, "Ок, сколько человек будет сидеть за столом " + sessionHandler.getBoardById(callData) + "\n", CANCEL_BUTTON);
//				} catch (IOException e) {
//					sendMessageWithText(chatId, "Смена еще не началась");
//					return;
//				}
//				tableDetails.put("boardId", callData);
//				return;
//			}
//
//			if (tableDetails.containsKey("boardId") &&
//					tableDetails.containsKey("number") &&
//					callData.equals("/yes")) {
//
//				sendMessageWithTextAndInlineKeyboard(chatId, "Введите описание стола", CANCEL_BUTTON);
//				context.add("/yes");
//				return;
//			}
//
//			if (context.contains("/chooseTable") &&
//					tableDetails.containsKey("boardId") &&
//					tableDetails.containsKey("number") &&
//					callData.equals("/no")) {
//
//				sendMessageWithTextAndInlineKeyboard(chatId, "Хотите изменить время посадки? \n", CHANGE_SEAT_TIME_BUTTON, LEAVE_SEAT_TIME_BUTTON, CANCEL_BUTTON);
//				tableDetails.put("description", "");
//				return;
//			}
//
//			if (context.contains("/chooseTable") &&
//					tableDetails.containsKey("boardId") &&
//					tableDetails.containsKey("number") &&
//					callData.equals("/change")) {
//
//				sendMessageWithText(chatId, "Введите новое время в формате Часы:Минуты");
//				context.add("/change");
//				return;
//			}
//
//			if (context.contains("/chooseTable") &&
//					tableDetails.containsKey("boardId") &&
//					tableDetails.containsKey("number") &&
//					callData.equalsIgnoreCase("/leave")) {
//
//				sessionHandler.sendRequestOnAddTable(tableDetails);
//				sendMessageWithText(chatId, "Счёт добавлен в систему");
//				//выходим из меню, но остаётся аунтификация
//				context.clear();
//				tableDetails.clear();
//				printMainMenu(chatId);
//				return;
//			}

//}


	private void sendMessageWithText(Long chatId, String text) {

		SendMessage message = new SendMessage()
				.setChatId(chatId)
				.setText(text);
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			logger.error("Error sending message" + e.getLocalizedMessage());
		}
	}

//	private void sendMessageWithTextAndInlineKeyboard(Long chatId, String messageText, List<String> inlineKeyboardText, List<String> callbackData) {
//
//		SendMessage message = new SendMessage()
//				.setChatId(chatId)
//				.setText(messageText);
//		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
//		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
//		try {
//			for (int i = 0; i < inlineKeyboardText.size(); i++) {
//				List<InlineKeyboardButton> rowInline = new ArrayList<>();
//				rowInline.add(new InlineKeyboardButton().setText(inlineKeyboardText.get(i)).setCallbackData(callbackData.get(i)));
//				rowsInline.add(rowInline);
//			}
//		} catch (ArrayIndexOutOfBoundsException e) {
//			logger.error("Не правильно заполнен лист для кнопок: " + e.getLocalizedMessage());
//		}
//		markupInline.setKeyboard(rowsInline);
//		message.setReplyMarkup(markupInline);
//		try {
//			sendMessage(message);
//		} catch (TelegramApiException e) {
//			logger.error("Error sending message" + e.getLocalizedMessage());
//		}
//	}


	private void sendMessageWithTextAndInlineKeyboard(Long chatId, String messageText, InlineKeyboardButton... buttons) {

		SendMessage message = new SendMessage()
				.setChatId(chatId)
				.setText(messageText);
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		try {
			for (InlineKeyboardButton button : buttons) {
				List<InlineKeyboardButton> rowInline = new ArrayList<>();
				rowInline.add(button);
				rowsInline.add(rowInline);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error("Не правильно заполнен лист для кнопок: " + e.getLocalizedMessage());
		}
		markupInline.setKeyboard(rowsInline);
		message.setReplyMarkup(markupInline);
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			logger.error("Error sending message" + e.getLocalizedMessage());
		}
	}

//	private void printMainMenu(Long chatId) {
//
//		sendMessageWithTextAndInlineKeyboard(chatId, "Вы находитесь в главном меню" + "\n"
//				+ "Вам доступны следующие команды: " + "\n", CHOOSE_TABLE_BUTTON, OUT_BUTTON);
//	}

	private void printHelloOnStart(Long chatId) {
		sendMessageWithTextAndInlineKeyboard(chatId, "Для началы игры нажмите кнопку ", ButtonsHandler.AUTH_BUTTON);
	}

//	private void printOut(Long chatId) {
//
//		sendMessageWithTextAndInlineKeyboard(chatId, "Все настройки сброшены чтобы начать работу авторизуйтесь: ", AUTH_BUTTON);
//	}

	@Override
	public String getBotUsername() {

		return "MafiaPacmanBot";
	}

	@Override
	public String getBotToken() {

		return "368977130:AAFqS_rOyTkI9n0BV2k8u9Qnl67sDBXDoJc";
	}

}
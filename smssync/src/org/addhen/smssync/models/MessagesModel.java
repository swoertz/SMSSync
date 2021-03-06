package org.addhen.smssync.models;

import java.util.ArrayList;
import java.util.List;

import org.addhen.smssync.MainApplication;
import org.addhen.smssync.database.Database;
import org.addhen.smssync.util.Util;

import android.database.Cursor;

/**
 * Class to handle set and getters.
 * 
 * @author eyedol
 */
public class MessagesModel extends Model {

	private String message;

	private String messageFrom;

	private String messageDate;

	private int messageId;

	public List<MessagesModel> listMessages;

	/**
	 * Set the content of the message. More like the body of the SMS message.
	 * 
	 * @param String
	 *            messageBody - The content of the SMS message.
	 * @return void
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the content of the message.
	 * 
	 * @return String
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Set the address of the SMS message.
	 * 
	 * @param String
	 *            messageFrom
	 * @return void
	 */
	public void setMessageFrom(String messageFrom) {
		this.messageFrom = messageFrom;
	}

	/**
	 * Get the address of the SMS Message
	 * 
	 * @return String
	 */
	public String getMessageFrom() {
		return this.messageFrom;
	}

	/**
	 * Set the date of the message.
	 * 
	 * @param String
	 *            messageDate - The timestamp of the message. To be changed into
	 *            human readable.
	 * @return void
	 */
	public void setMessageDate(String messageDate) {
		try {
			this.messageDate = Util.formatDateTime(Long.parseLong(messageDate),
					"MMM dd, yyyy 'at' hh:mm a");

		} catch (NumberFormatException e) {
			this.messageDate = messageDate;
		}

	}

	/**
	 * Get the message date
	 * 
	 * @return String
	 */
	public String getMessageDate() {
		return this.messageDate;
	}

	/**
	 * Set the message ID.
	 * 
	 * @param int messageId - The message ID.
	 * @return void
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	/**
	 * Get the message ID.
	 * 
	 * @return int
	 */
	public int getMessageId() {
		return this.messageId;
	}

	@Override
	public boolean load() {
		listMessages = new ArrayList<MessagesModel>();
		Cursor cursor;
		cursor = MainApplication.mDb.fetchAllMessages();

		String messagesFrom;
		String messagesDate;
		String messagesBody;
		int messageId;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				int messagesIdIndex = cursor
						.getColumnIndexOrThrow(Database.MESSAGES_ID);
				int messagesFromIndex = cursor
						.getColumnIndexOrThrow(Database.MESSAGES_FROM);
				int messagesDateIndex = cursor
						.getColumnIndexOrThrow(Database.MESSAGES_DATE);

				int messagesBodyIndex = cursor
						.getColumnIndexOrThrow(Database.MESSAGES_BODY);

				do {

					MessagesModel messages = new MessagesModel();

					messageId = Util.toInt(cursor.getString(messagesIdIndex));
					messages.setMessageId(messageId);

					messagesFrom = cursor.getString(messagesFromIndex);
					messages.setMessageFrom(messagesFrom);

					messagesDate = cursor.getString(messagesDateIndex);
					messages.setMessageDate(messagesDate);

					messagesBody = cursor.getString(messagesBodyIndex);
					messages.setMessage(messagesBody);
					listMessages.add(messages);

				} while (cursor.moveToNext());

			}
			cursor.close();
			return true;
		}

		return false;
	}

	@Override
	public boolean save() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Delete all pending messages.
	 * 
	 * @return boolean
	 */
	public boolean deleteAllMessages() {
		return MainApplication.mDb.deleteAllMessages();
	}

	/**
	 * Delete messages by id
	 * 
	 * @param int messageId - Message to be deleted ID
	 * @return boolean
	 */
	public boolean deleteMessagesById(int messageId) {
		return MainApplication.mDb.deleteMessagesById(messageId);
	}

}

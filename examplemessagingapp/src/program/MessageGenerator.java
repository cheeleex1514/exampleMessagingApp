package program;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import models.GuestClient;

public class MessageGenerator {
	private GuestClient selectedGuest;
	private String message;
	
	public MessageGenerator(GuestClient selectedGuest, String message) {
		this.selectedGuest = selectedGuest;
		this.message = message;
	}
	
	public String generateMessage() {
		Class<? extends GuestClient> ts = selectedGuest.getClass();
		Field[] fields = ts.getDeclaredFields();
		List<Method> methods = new ArrayList<Method>(Arrays.asList(ts.getDeclaredMethods()));
		
		List<String> messageList = new ArrayList<String>(Arrays.asList(this.message.split(" ")));
		
		for(Field field : fields) {			
			for(int x = 0; x <= messageList.size()-1; x++) {
				if(messageList.get(x).contains("{@timeSetting}")) {
					messageList.set(x, messageList.get(x).replaceAll("(\\{@[A-Za-z0-9._%-]+\\})", this.returnTimeSetting()));
				}
				
				if(messageList.get(x).contains("{@"+field.getName()+"}")) {
					String value = ""; 
					
					for(Method method : methods) {
						if (method.getName().toLowerCase().contains(field.getName().toLowerCase()) && method.getName().toLowerCase().contains("get")) {
							try {
								try {
									value = method.invoke(this.selectedGuest).toString();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								}
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}

					messageList.set(x, messageList.get(x).replaceAll("(\\{@[A-Za-z0-9._%-]+\\})", value));
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(String word : messageList) {
			sb.append(word + " ");
		}
		
		return sb.toString();
	}
	
	private String returnTimeSetting() {
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("HH:mm");
		String timeNow = ft.format(dNow);
		String replacementWord = "";
		
		if( (timeNow.compareTo("00:00") >= 0) && (timeNow.compareTo("06:00") <= 0) ) {
			replacementWord = "Good morning";
		}
		
		if( (timeNow.compareTo("06:01") >= 0) && (timeNow.compareTo("12:00") <= 0) ) {
			replacementWord = "Good afternoon";
		}
		
		if( (timeNow.compareTo("12:01") >= 0) && (timeNow.compareTo("23:59") <= 0) ) {
			replacementWord = "Good evening";
		}
		
		return replacementWord;
	}
}

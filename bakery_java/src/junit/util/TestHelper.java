package junit.util;
import java.util.Date;

import org.bakery.server.domain.accounting.Address;
import org.bakery.server.domain.production.Unit;

public class TestHelper {
	public static String[] words =
		{"Hill", "Denominator", "Year", "Bubble", "Cat",
		 "Dog", "Zomby", "Tarzan", "Chuck", "Pattern",
		 "Mouse", "Sun", "Moon", "Getto", "Soldier",
		 "Jumper", "Juggler", "Ball", "Portret", "Portfel",
		 "Grass", "Apple", "Monitor"
		 
		};
	public  static String[] descWords = 
		{"Red", "Orange", "Yellow", "Green", "Blue",
		 "Violet", "White", "Black", "Gray", "Dark",
		 "Fast", "Slow", "Smiling", "Roaring", "Singing",
		 "Cold", "Hot", "Soft", "Norris"
		};
	public static  String[] nameWords = {
		"Chuck", "Joe", "Dmitry", "Alex", "Peter",
		"Andy", "Randy", "Oliver", "John", "George",
		"Sergiy", "Alexey", "Buddah"
	};
	public static String addTime(String source) {
		String testTime = new Date().getTime()+"";
		testTime = ("["+testTime+"]"+source);
		return testTime.substring(0, Math.min(testTime.length(), 30));
	}
	public static  Unit generateRandomUnit() {
		String result = "UN_";
		while (Math.random() < 0.7) {
			double rnd = Math.random();
			result = result + "O";
			if (rnd < 0.1) {
				result = result + "C";
			} else if (rnd < 0.2) {
				result = result + "B";
			} else if (rnd < 0.3) {
				result = result + "D";
			} else if (rnd < 0.4) {
				result = result + "F";
			} else if (rnd < 0.5) {
				result = result + "K";
			} else if (rnd < 0.6) {
				result = result + "I";
			} else if (rnd < 0.7) {
				result = result + "N";
			} else if (rnd < 0.8) {
				result = result + "K";
			}
		}
		return new Unit(addTime(limitLen(result)));
	}
	
	public static  Address generateRandomAddress() {
		return new Address(addTime(limitLen(getOneFromArray(descWords) + " "
				+ getOneFromArray(descWords)
				+ " "
				+ getOneFromArray(words)
				+" Str., "
				+getRndInt(67))));
	}

	public static  String generateRandomName() {
		return limitLen(addTime("Mr. "
				+ getOneFromArray(descWords) + " "
				+ getOneFromArray(descWords)
				+ " "
				+ getOneFromArray(nameWords)));
	}
	public static String limitLen(String s){
		return s.substring(1, Math.min(s.length(), 40));
	}
	public  static int getRndInt(int max) {
		return Math.round(((float) Math.random()*max));
	}
	public  static String getOneFromArray(String[] arr){
		return arr[Math.round(((float) Math.random()*(arr.length-1)))];
	}
	
}

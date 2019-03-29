package st;

import java.math.BigInteger;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

public class Parser {
	public static final int INTEGER = 1;
	public static final int BOOLEAN = 2;
	public static final int STRING = 3;
	public static final int CHAR = 4;
	
	private OptionMap optionMap;
	
	public Parser() {
		optionMap = new OptionMap();
	}
	
	public void add(String option_name, String shortcut, int value_type) {
		optionMap.store(option_name, shortcut, value_type);
	}
	
	public void add(String option_name, int value_type) {
		optionMap.store(option_name, "", value_type);
	}
	
	public List<Integer> getIntegerList(String option){
		String value = getString(option); 
		List<Integer> parsed_integer_list = new ArrayList<Integer>(); //define return object
		if(option == null || option == "") { //if argument is illegal, return the empty list
			return parsed_integer_list;
		}
		if(value == null || value == "") { //if value is empty, return empty list
			return parsed_integer_list;
		}
		value = value.replaceAll("[^0-9-]+", ",");
		//System.out.println(value);
		String[] split_values = value.split(","); //split on the special characters, and alphabet
		for(String current:split_values) {
			if (current.isEmpty()) {
				System.out.println("Zero Length String");
			}
			else if(current.charAt(current.length()-1)=='-') { //check for illegal suffix, and don't add to return if found
				//illegal suffix!!
				System.out.println("Illegal Suffix Reached");
			}
			else if (!current.contains("-") || !current.substring(1).contains("-")) { //if it's not a range, just add to arraylist
				parsed_integer_list.add(Integer.parseInt(current));
				//System.out.println("Single integer recognised");
				//System.out.println("Printing the arrayList " + parsed_integer_list);
			}
						
			else {
				
				//System.out.println("Range recognised");
				int occurences = 0; //ascertain number of hyphens in range
				for ( int i = 0; i < current.length(); i++) {
					if (current.charAt(i) == '-') {
						occurences++;
					}
				}
				String[] startEnd = {"",""}; //if there's 1 hyphen, both numbers are positive
				if (occurences == 1) {
					startEnd = current.split("-");
				} else if(occurences == 2) { //if 2, then 1 negative and 1 positive
					if(current.contains("--")) {
						int x = current.indexOf("-");
						startEnd[0] = current.substring(0, x);
						startEnd[1] = current.substring(x+1);
					}
					else {
						//System.out.println("abc");
						int x = current.lastIndexOf("-");
						//System.out.println(x);
						startEnd[0] = current.substring(0, x);
						startEnd[1] = current.substring(x+1);
					}
				} else if(occurences == 3) { //both numbers are negative
					int x = current.lastIndexOf("-");
					startEnd[0] = current.substring(0, x-1);
					startEnd[1] = current.substring(x);
				}
				
				Integer[] bothRange = {Integer.parseInt(startEnd[0]),Integer.parseInt(startEnd[1])}; //convert our string numbers to integers
				for(int i = Math.min(bothRange[0], bothRange[1]); i <= Math.max(bothRange[0], bothRange[1]); i++) { //add them to the list, in ascending order
					
					parsed_integer_list.add(i);
				}
			}
		}
		Collections.sort(parsed_integer_list); //sort the list so the numbers are in ascending order
		System.out.println(parsed_integer_list);
		return parsed_integer_list; //return list object
		
	}

	public int getInteger(String option) {
		String value = getString(option);
		int type = getType(option);
		int result;
		switch (type) {
		case INTEGER:
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
		        try {
		            new BigInteger(value);
		        } catch (Exception e1) {
		            result = 0;
		        }
		        result = 0;
		    }
			break;
		case BOOLEAN:
			if (getBoolean(option) == false) {
				result = 0;
			} else {
				result = 1;
			}
			break;
		case STRING:
		    int power = 1;
		    result = 0;
		    char c;
		    for (int i = value.length() - 1; i >= 0; --i){
		        c = value.charAt(i);
		        if (!Character.isDigit(c)) return 0;
		        result = result + power * (c - '0');
		        power *= 10;
		    }
		    break;
		case CHAR:
			result = (int)getChar(option);
			break;
		default:
			result = 0;
		}
		return result;
	}
	
	public boolean getBoolean(String option) {
		String value = getString(option);
		boolean result;
		if (value.toLowerCase().equals("false") || value.equals("0") || value.equals("")) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
	
	public String getString(String option) {
		String result = optionMap.getValue(option);
		return result;
	}
	
	public char getChar(String option) {
		String value = getString(option);
		char result;
		if (value.equals("")) {
			result = '\0';
		} else {
			result = value.charAt(0);
		}
		return result;
	}
	
	public int parse(String command_line_options) {
		if (command_line_options == null) {
			return -1;
		}
		int length = command_line_options.length();
		if (length == 0) {
			return -2;
		}
		
		int char_index = 0;
		while (char_index < length) {
			char current_char = command_line_options.charAt(char_index);

			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (current_char != ' ') {
					break;
				}
				char_index++;
			}
			
			boolean isShortcut = true;
			String option_name = "";
			String option_value = "";
			if (current_char == '-') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
				if (current_char == '-') {
					isShortcut = false;
					char_index++;
				}
			} else {
				return -3;
			}
			current_char = command_line_options.charAt(char_index);
			
			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (Character.isLetterOrDigit(current_char) || current_char == '_') {
					option_name += current_char;
					char_index++;
				} else {
					break;
				}
			}
			
			boolean hasSpace = false;
			if (current_char == ' ') {
				hasSpace = true;
				while (char_index < length) {				
					current_char = command_line_options.charAt(char_index);
					if (current_char != ' ') {
						break;
					}
					char_index++;
				}
			}

			if (current_char == '=') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
			}
			if ((current_char == '-'  && hasSpace==true ) || char_index == length) {
				if (getType(option_name) == BOOLEAN) {
					option_value = "true";
					if (isShortcut) {
						optionMap.setValueWithOptioShortcut(option_name, option_value);
					} else {
						optionMap.setValueWithOptionName(option_name, option_value);
					}
				} else {
					return -3;
				}
				continue;
			} else {
				char end_symbol = ' ';
				current_char = command_line_options.charAt(char_index);
				if (current_char == '\'' || current_char == '\"') {
					end_symbol = current_char;
					char_index++;
				}
				while (char_index < length) {
					current_char = command_line_options.charAt(char_index);
					if (current_char != end_symbol) {
						option_value = option_value + current_char;
						char_index++;
					} else {
						break;
					}
				}
			}
			
			if (isShortcut) {
				optionMap.setValueWithOptioShortcut(option_name, option_value);
			} else {
				optionMap.setValueWithOptionName(option_name, option_value); 
			}
			char_index++;
		}
		return 0;
	}
	
	private int getType(String option) {
		int type = optionMap.getType(option);
		return type;
	}
	
	@Override
	public String toString() {
		return optionMap.toString();
	}

}


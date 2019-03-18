import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task1_Functional {
	
	private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	@Test
	public void is_initialised() {
		assertTrue(parser instanceof Parser);
	}
	
	//Section for parser.add() with shortcut
	
	@Test
	public void test_add_shortcut() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("o"), "output.txt");
		
		parser.add("root_privelege", "r", Parser.BOOLEAN);
		parser.parse("--root_privelege=true");
		assertEquals(parser.getBoolean("r"), true);
		
		parser.add("num_of_loops", "n", Parser.INTEGER);
		parser.parse("--num_of_loops=20");
		assertEquals(parser.getInteger("n"), 20);
		
		parser.add("verbose", "v", Parser.CHAR);
		parser.parse("--verbose=v");
		assertEquals(parser.getChar("v"), 'v');
	}
	@Test
	public void test_repeat_addition_shortcut() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=true");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void illegal_argument_special_char_shortcut() {
		try {
			parser.add("root_privele**e", "r", Parser.BOOLEAN);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", "r", Parser.BOOLEAN);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("root_privele**e", "r", Parser.STRING);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", "r", Parser.STRING);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("root_privele**e", "r", Parser.INTEGER);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", "r", Parser.INTEGER);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("root_privele**e", "r", Parser.CHAR);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", "r", Parser.CHAR);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
	}
	
	@Test
	public void test_add_shortcut_case_sensitive() {
		//same type
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=output.txt");
		parser.add("order", "O", Parser.STRING);
		parser.parse("--order=1,3,2");
		assertEquals(parser.getString("o"), "output.txt");
		assertEquals(parser.getString("O"), "1,3,2");
		
		//different type
		parser.add("root_privelege", "r", Parser.BOOLEAN);
		parser.parse("--root_privelege=true");
		parser.add("red", "R", Parser.CHAR);
		parser.parse("--red=T");
		assertEquals(parser.getBoolean("r"), true);
		assertEquals(parser.getChar("R"),'T');
	}
	
	@Test
	public void test_shortcut_name_same_as_option() {
		//same type
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=output.txt");
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o=1,3,2");
		assertEquals(parser.getString("o"), "output.txt");
		assertEquals(parser.getString("o"), "1,3,2");
	}
	
	@Test
	public void test_boolean_assignment_shortcut() {
		parser.add("root_privelege", "r", Parser.BOOLEAN);
		parser.parse("--root_privelege=true");
		assertEquals(parser.getBoolean("r"), true);
		
		parser.parse("--root_privelege=0");
		assertEquals(parser.getBoolean("r"), false);
		
		parser.parse("--root_privelege=trilla");
		assertEquals(parser.getBoolean("r"), true);
		
		parser.parse("--root_privelege=FaLsE");
		assertEquals(parser.getBoolean("r"), false);
		
		parser.parse("--root_privelege=00");
		assertEquals(parser.getBoolean("r"), true);
		
		parser.parse("--root_privelege");
		assertEquals(parser.getBoolean("r"), true);
		
		parser.parse("-r");
		assertEquals(parser.getBoolean("r"), true);
	}
	
	
	
	//Section for Parser.add() with no shortcut
	
	
	@Test
	public void test_add_no_shortcut() {
		parser.add("output",Parser.STRING);
		parser.parse("--output=output.txt");
		assertEquals(parser.getString("output"),"output.txt");
		
		parser.add("root_privelege", Parser.BOOLEAN);
		parser.parse("--root_privelege=true");
		assertEquals(parser.getBoolean("root_privelege"), true);
		
		parser.add("num_of_loops", Parser.INTEGER);
		parser.parse("--num_of_loops=20");
		assertEquals(parser.getInteger("num_of_loops"), 20);
		
		parser.add("verbose", Parser.CHAR);
		parser.parse("--verbose=v");
		assertEquals(parser.getChar("verbose"), 'v');
	}
	
	@Test
	public void test_repeat_addition() {
		parser.add("output",Parser.STRING);
		parser.parse("--output=output.txt");
		parser.add("output",Parser.BOOLEAN);
		parser.parse("--output=true");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void illegal_argument_special_char() {
		try {
			parser.add("root_privele**e", Parser.BOOLEAN);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", Parser.BOOLEAN);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("root_privele**e", Parser.STRING);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", Parser.STRING);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("root_privele**e", Parser.INTEGER);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", Parser.INTEGER);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("root_privele**e", Parser.CHAR);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
		try {
			parser.add("4root_privelege", Parser.CHAR);
		}catch (RuntimeException re){
			assertTrue(re instanceof RuntimeException);
		}
	}
	
	@Test
	public void test_add_case_sensitive() {
		//same type
		parser.add("output", Parser.STRING);
		parser.parse("--output=output.txt");
		parser.add("OUTPUT", Parser.STRING);
		parser.parse("--OUTPUT=1,3,2");
		assertEquals(parser.getString("output"), "output.txt");
		assertEquals(parser.getString("OUTPUT"), "1,3,2");
		
		//different type
		parser.add("root_privelege", Parser.BOOLEAN);
		parser.parse("--root_privelege=true");
		parser.add("ROOT_PRIVELEGE", Parser.CHAR);
		parser.parse("--ROOT_PRIVELEGE=T");
		assertEquals(parser.getBoolean("root_privelege"), true);
		assertEquals(parser.getChar("ROOT_PRIVELEGE"),'T');
	}
	
	@Test
	public void test_boolean_assignment() {
		parser.add("root_privelege", Parser.BOOLEAN);
		parser.parse("--root_privelege=true");
		assertEquals(parser.getBoolean("root_privelege"), true);
		
		parser.parse("--root_privelege=0");
		assertEquals(parser.getBoolean("root_privelege"), false);
		
		parser.parse("--root_privelege=trilla");
		assertEquals(parser.getBoolean("root_privelege"), true);
		
		parser.parse("--root_privelege=FaLsE");
		assertEquals(parser.getBoolean("root_privelege"), false);
		
		parser.parse("--root_privelege=00");
		assertEquals(parser.getBoolean("root_privelege"), true);
		
		parser.parse("--root_privelege");
		assertEquals(parser.getBoolean("root_privelege"), true);
		
		parser.parse("-r");
		assertEquals(parser.getBoolean("root_privelege"), true);
	}
	
	//Test Complete parsing
	
	@Test
	public void parse_input_file_and_output_file() {
		parser.add("input", Parser.STRING);
		parser.add("output", Parser.STRING);
		parser.parse("--input=123.txt --output=abc.txt");
		assertTrue(parser.getString("input").equals("123.txt") && parser.getString("output").equals("abc.txt"));
	}
	
	@Test
	public void parse_bool_and_file() {
		parser.add("input", Parser.STRING);
		parser.add("root","r", Parser.BOOLEAN);
		parser.parse("--input=123.txt -r=0");
		assertTrue(parser.getString("input").equals("123.txt") && !parser.getBoolean("r"));
	}
	
	@Test
	public void parse_bool_and_file_with_space() {
		parser.add("input", Parser.STRING);
		parser.add("root","r", Parser.BOOLEAN);
		parser.parse("--input 123.txt -r 0");
		assertTrue(parser.getString("input").equals("123.txt") && !parser.getBoolean("r"));
	}
	
	@Test
	public void parse_bool_and_file_with_quotation() {
		parser.add("input", Parser.STRING);
		parser.add("root","r", Parser.BOOLEAN);
		parser.parse("--input=\"123.txt\" -r=\'0\'");
		assertTrue(parser.getString("input").equals("123.txt") && !parser.getBoolean("r"));
	}
	
	@Test
	public void parse_option_multiple_times() {
		parser.add("root","r", Parser.BOOLEAN);
		parser.parse("--root 123.txt -r 0 --root=true -r=0");
		assertTrue(!parser.getBoolean("r") && !parser.getBoolean("root"));
	}
	
	@Test
	public void parse_empty_options() {
		parser.add("input", Parser.STRING);
		parser.add("root","r", Parser.BOOLEAN);
		parser.add("output", Parser.INTEGER);
		parser.add("verbose","v", Parser.CHAR);
		parser.parse("--input 123.txt -r 0");
		assertTrue(parser.getString("input").equals("123.txt") && !parser.getBoolean("r") && parser.getChar("v")==('\0') && parser.getInteger("output") == 0);
	}
	
	@Test
	public void parse_double_parse() {
		parser.add("input", Parser.STRING);
		parser.add("root","r", Parser.BOOLEAN);
		parser.add("output", Parser.INTEGER);
		parser.add("verbose","v", Parser.CHAR);
		parser.parse("--input 123.txt -r 0");
		assertTrue(parser.getString("input").equals("123.txt") && !parser.getBoolean("r") && parser.getChar("v")==('\0') && parser.getInteger("output") == 0);
		parser.parse("--output 4 -v v");
		assertTrue(parser.getString("input").equals("123.txt") && !parser.getBoolean("r") && parser.getChar("v")==('v') && parser.getInteger("output") == 4);
	}
	
	//Test parser.get* functions .. they have been used above but additional testing required
	
	@Test 
	public void test_get_functions() {
		parser.add("root","r", Parser.BOOLEAN);
		assertEquals(parser.getBoolean("r"), false);
		parser.parse("-r xyz");
		assertEquals(parser.getBoolean("r"), true);
		
		parser.add("input","i", Parser.STRING);
		assertEquals(parser.getString("i"), "");
		parser.parse("-i 0");
		assertEquals(parser.getString("i"), "0");
		
		parser.add("loop","l", Parser.INTEGER);
		assertEquals(parser.getInteger("loop"), 0);
		parser.parse("--loop 70");
		assertEquals(parser.getInteger("loop"), 70);
		
		parser.add("jump","j", Parser.CHAR);
		assertEquals(parser.getChar("j"), '\0');
		parser.parse("--jump a");
		assertEquals(parser.getChar("jump"), 'a');
	}
	
}










































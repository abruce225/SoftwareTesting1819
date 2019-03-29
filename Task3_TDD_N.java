import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;


@SuppressWarnings("serial")
public class Task3_TDD_N {
	
	private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	@Test
	public void is_initialised() {
		assertTrue(parser instanceof Parser);
	}
	
	//first round of TDD, write tests to fit the spec provided in New Specifications. Black box testing style (functional).

	
	@Test
	public void test_shortcut_name_same_as_option() { //spec #1, this is actually handled in the option map class
		//same type
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o=1,3-4");
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=output.txt");
		List<Integer> l = new ArrayList<Integer>() {{add(1);add(3);add(4);}};
		assertEquals(parser.getIntegerList("o"), l);
		
	}
	
	@Test
	public void check_illegal_option() { //spec #2
		List<Integer> l = new ArrayList<Integer>();
		assertEquals(parser.getIntegerList(null), l);
		assertEquals(parser.getIntegerList(""), l);
	}
	
	@Test
	public void test_special_characters() { //spec #3
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o=1]3{4<78.9");
		List<Integer> l = new ArrayList<Integer>() {{add(1);add(3);add(4);add(9);add(78);}};
		assertEquals(parser.getIntegerList("o"), l);
		parser.parse("--o=1a3y4K78,9"); //added these extra lines in round 2 of TDD
		assertEquals(parser.getIntegerList("o"), l);
		parser.parse("--o=}{}{}]]1a3y4K78,9");
		assertEquals(parser.getIntegerList("o"), l);
		parser.parse("--o='         1   3    4     78                9          '");//single quotes allow the parser to understand this input
		assertEquals(parser.getIntegerList("o"), l);
	}
	
	@Test
	public void test_ranges_positive() { //spec 4, test range inputs
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=1-4");
		List<Integer> l = new ArrayList<Integer>() {{add(1);add(2);add(3);add(4);}};
		assertEquals(parser.getIntegerList("o"), l);
		parser.parse("-o=4-1");
		assertEquals(parser.getIntegerList("o"), l);
	}
	
	@Test
	public void test_ranges_negative() { //cont
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>() {{add(-1);add(0);add(1);add(2);}};
		parser.parse("-o=-1-2");
		assertEquals(parser.getIntegerList("o"), l);
		parser.parse("-o=2--1");
		assertEquals(parser.getIntegerList("o"), l);
	}
	
	@Test
	public void test_ranges_negative_to_negative() { //cont
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>() {{add(-4);add(-3);add(-2);add(-1);}};
		parser.parse("-o=-1--4");
		assertEquals(parser.getIntegerList("o"), l);
	}
	
	@Test
	public void test_ranges_self_to_self() {
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>() {{add(4);}};
		parser.parse("-o=4-4");
		assertEquals(parser.getIntegerList("o"), l);
		l = new ArrayList<Integer>() {{add(-5000);}};
		parser.parse("-o=-5000--5000");
		assertEquals(parser.getIntegerList("output"), l);
	}
	
	@Test
	public void test_ranges_negative_to_negative_ascending() { //cont
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>() {{add(-4);add(-3);add(-2);add(-1);}};
		parser.parse("-o=-4--1");
		assertEquals(parser.getIntegerList("o"), l);
	}
	
	@Test
	public void test_ascending_order() { //make sure the list returned is coming back in ascending order,not just ranges but every value
		parser.add("val", Parser.STRING); 
		parser.parse("--val=4,3,]2,1,,,9,8,150,1");
		List<Integer> l = new ArrayList<Integer>() {{add(1);add(1);add(2);add(3);add(4);add(8);add(9);add(150);}};
		assertEquals(parser.getIntegerList("val"),l);
		parser.parse("--val=4-1,,,9-8,150,1"); //assert it is correct with ranges aswell
		assertEquals(parser.getIntegerList("val"),l);
	}
	
	//stage two of TDD, these tests filled bits of specification that we missed before
	
	@Test
	public void test_negative_single() {
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>() {{add(-1);}};
		parser.parse("-o=-1");
		assertEquals(parser.getIntegerList("o"), l);
	}
	@Test
	public void test_positive_single() {
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>() {{add(200);}};
		parser.parse("-o=200");
		assertEquals(parser.getIntegerList("o"), l);
	}
	
	//stage 3, suffix issue which we hadn't noticed prior
	
	@Test
	public void test_bad_suffix() { //this is spec 6, didn't notice it earlier
		parser.add("output", "o", Parser.STRING);
		List<Integer> l = new ArrayList<Integer>();
		parser.parse("-o=-1-");
		assertEquals(parser.getIntegerList("o"), l);
		parser.parse("-o=-1--4-");
		assertEquals(parser.getIntegerList("o"), l);
		l = new ArrayList<Integer>() {{add(2);add(3);add(4);}}; //make sure it still parses other arguments
		parser.parse("-o=-1-,2,3,4");
		assertEquals(parser.getIntegerList("o"), l);
	}
}

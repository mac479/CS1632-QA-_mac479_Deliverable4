import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	MainPanel panel;

	@Before
	public void setUp() {
		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:
		 * Game_of_life_blinker.gif Start from the vertical bar on a 5X5 matrix as shown
		 * in the GIF.
		 */

		panel = new MainPanel(5);
		Cell[][] tempData = new Cell[5][5];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				tempData[x][y] = Mockito.mock(Cell.class); // Fills data with mock cells as cells arent being tested
															// here.
				if (y == 2 && x > 0 && x < 4) { // Setting up blinker pattern
					Mockito.when(tempData[x][y].getAlive()).thenReturn(true); // Marked as alive
				} else {
					Mockito.when(tempData[x][y].getAlive()).thenReturn(false); // Marked as dead
				}
			}
		}
		panel.setCells(tempData);
	}

	@After
	public void tearDown() {
	}

	/* TODO: Write the three pinning unit tests for the three optimized methods */

	// Methods to test
	// 1 MainPanel.iterateCell
	// 2 Cell.ToString
	// 3 MainPanel.calculateNextIteration


	/**
	 * Test case for if a living cell will die under right conditions
	 * Preconditions: Panel is set up in a horizontal blinker pattern
	 * Execution steps: Iterate the specific cell
	 * Postconditions: Cell has passed.
	 */
	@Test
	public void iterateCellDeadTest() {
		Boolean result = panel.iterateCell(1, 2);
		assertFalse("Cell should be dead but is still alive!", result);
	}


	/**
	 * Test case for if a dead cell stays dead under right conditions
	 * Preconditions: Panel is set up in a horizontal blinker pattern.
	 * Execution steps: Iterate the specific cell
	 * Postconditions: Cell is still marked as dead.
	 */
	@Test
	public void iterateCellSameDeadTest() {
		Boolean result = panel.iterateCell(1, 1);
		assertFalse("Cell should be dead but is turned alive!", result);
	}


	/**
	 * Test case for if a dead cell will become alive under right conditions.
	 * Preconditions: Panel is set up in a horizontal blinker pattern
	 * Execution steps: Iterate the specific cell
	 * Postconditions: Cell has revived.
	 */
	@Test
	public void iterateCellAliveTest() {
		Boolean result = panel.iterateCell(2, 1);
		assertTrue("Cell should be alive but is still dead!", result);
	}


	/**
	 * Test case to make sure a living cell does not die under right conditions
	 * Preconditions: Panel is set up in a horizontal blinker pattern.
	 * Execution steps: Iterate the specific cell
	 * Postconditions: Cell is still marked as alive.
	 */
	@Test
	public void iterateCellSameAliveTest() {
		Boolean result = panel.iterateCell(2, 2);
		assertTrue("Cell should be alive but is turned dead!", result);
	}


	/**
	 * Test case for if a living cell returns the correct ToString response "X"
	 * Preconditions: Cell is marked as alive with the proper text.
	 * Execution steps: Call toString method on the cell
	 * Postconditions: toString should return "X"
	 */
	@Test
	public void LiveCellToStringTest() {
		// Unlike the other tests this requires a real cell to test
		Cell testCell = new Cell();
		testCell.setText("X");
		assertTrue("Did not return the live marker (X)", "X".equals(testCell.toString()));
	}


	/**
	 * Test case for if a dead cell returns the correct toString response "."
	 * Preconditions: Cell is marked as dead with proper text
	 * Execution steps: Call toString method on cell
	 * Postconditions: toString should return "."
	 */
	@Test
	public void DeadCellToStringTest() {
		// Unlike the other tests this requires a real cell to test
		Cell testCell = new Cell();
		testCell.setText(" ");
		assertTrue("Did not return the dead marker (.)", ".".equals(testCell.toString()));
	}

	//Fake cell class designed to only swap between alive and dead and report condition
	private class FakeCell extends Cell {
		public boolean alive=false;

		public void setAlive(boolean a) {
			alive = a;
		}
		public boolean getAlive() {
			return alive;
		}
	}

	/**
	 * Test case for iterating the whole board.
	 * Preconditions: Panel is in a horizontal blinker pattern
	 * Execution steps: run calculateNextIteration
	 * Postconditions: Verify the board is now in a vertical blinker pattern
	 */
	@Test
	public void CalculateNextIterationTest() {
		//setup
		Cell[][] tempData = new Cell[5][5];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				tempData[x][y] = new FakeCell();
				if (y == 2 && x > 0 && x < 4) { // Setting up blinker pattern
					tempData[x][y].setAlive(true); // Marked as alive
				} else {
					tempData[x][y].setAlive(false); // Marked as dead
				}
			}
		}
		panel.setCells(tempData);	//Import cells before iterations
		
		//Execution
		panel.calculateNextIteration();
		
		//Post
		tempData=panel.getCells();	//Export cells after calculating iteration
		for (int x = 0; x < panel.getCellsSize(); x++) {
			for (int y = 0; y < panel.getCellsSize(); y++) {
				if (x == 2 && y < 4 && y > 0)
					assertTrue("Cell["+x+"]["+y+"] was marked dead when it should be alive!",tempData[x][y].getAlive());
				else
					assertFalse("Cell["+x+"]["+y+"] was marked alive when it should be dead!",tempData[x][y].getAlive());
			}
		}
	}

}

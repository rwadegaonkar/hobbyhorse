import java.util.ArrayList;
import java.util.Date;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.spring.datasource.Lesson;
import com.spring.manager.*;

public class LessonsTest extends TestCase {

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	/*
	 * Test the total number of users
	 */
	public void testGetAllLessonsByLessonTypeId() throws Exception {
		LessonManager o = new LessonManager();
		ArrayList<Lesson> res = o.getLessonsByLessonTypeId(2);
		assertEquals(0, res.size());
	}
}
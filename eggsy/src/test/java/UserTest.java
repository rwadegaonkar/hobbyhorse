import java.util.ArrayList;
import java.util.Date;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import com.spring.datasource.User;
import com.spring.manager.*;

public class UserTest extends TestCase {
	private static final Date CURRENT_TIMESTAMP = new DateTime().toDate();

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
	public void testGetUserByUserId() throws Exception {
		System.out.println("Test class");
		UserManager o = new UserManager();
		ArrayList<User> res = o.getUserByUserId(2);
		//assertEquals(1, res.size());
	}

	@Test
	/*
	 * Test the save, getuserbyusername, deleteuser functionality
	 */
	public void testSaveAndGetUserByUserNameAndDeleteUser() throws Exception {
		UserManager o = new UserManager();
		User user = makeTestUserBean();
		o.saveUser(user);
		ArrayList<User> res = o.getUserByUsername(user.getUsername());
		assertNotNull(res.get(0));
		assertEquals("Radhika", res.get(0).getName());
		o.deleteUser(o.getUserByUsername(user.getUsername()).get(0)
				.getUsername());
	}

	/*
	 * Create a test user object
	 */
	private User makeTestUserBean() {
		User user = new User();
		user.setName("Radhika");
		user.setDescription("Test User");
		user.setCreateDate(CURRENT_TIMESTAMP);
		user.setLastUpdateDate(CURRENT_TIMESTAMP);
		user.setCreatedBy("admin");
		user.setLastUpdatedBy("admin");
		user.setEmail("radhika_k@gmail.com");
		user.setIsDeleted(0);
		user.setHobbies("Guitar, dancing");
		user.setSkills("dance");
		user.setLocation("Mountain View");
		user.setUsername("r_k");
		user.setLoginTypeId(1);
		return user;
	}
}
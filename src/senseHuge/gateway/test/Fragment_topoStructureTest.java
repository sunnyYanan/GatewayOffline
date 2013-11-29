package senseHuge.gateway.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import senseHuge.gateway.model.NodeTree;
import senseHuge.gateway.ui.Fragment_topoStructure;
import senseHuge.gateway.ui.MainActivity;
import android.test.AndroidTestCase;

public class Fragment_topoStructureTest extends AndroidTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MainActivity.serialPortConnect = true;
		List<String> path = new ArrayList<String>();
		path.add("0005");
		path.add("0002");
		NodeTree tree = new NodeTree();
		tree.insert(path);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testOnCreateViewLayoutInflaterViewGroupBundle() {
		fail("Not yet implemented");
		Fragment_topoStructure dd = new Fragment_topoStructure();
	}

}

package datameer.das.plugin;

import static org.junit.Assert.*;

import org.junit.Test;

import datameer.dap.sdk.importjob.TextBasedFileType;

public class CustomTransactionFileTypeTest {

    private TextBasedFileType _customTransactionFileType;

    public CustomTransactionFileTypeTest() {
        _customTransactionFileType = new CustomTransactionFileType();

    }

    @Test
    public void testGetId() throws Exception {
        assertEquals("datameer.das.plugin.CustomTransactionFileType", _customTransactionFileType.getId());
    }

    @Test
    public void testName() throws Exception {
        assertEquals("Cloak", _customTransactionFileType.getName());
    }

    @Test
    public void testCanAutoMergeNewFields() throws Exception {
       assertEquals(true, _customTransactionFileType.canAutoMergeNewFields());
    }

}

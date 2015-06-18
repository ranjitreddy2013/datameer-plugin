package datameer.das.plugin;

import static org.junit.Assert.*;

import org.junit.Test;

import datameer.dap.sdk.common.Field;
import datameer.dap.sdk.importjob.NoDataRecordSchemaDetector;

public class CustomTransactionRecordSchemaDetectorTest {

    @Test
    public void testDetectFields() throws Exception {
        NoDataRecordSchemaDetector<?> schemaDetector = new CustomTransactionRecordSchemaDetector();
        Field[] fields = schemaDetector.detectFields();
        assertNotNull(fields);
        assertEquals(3, fields.length);
        assertEquals(CustomTransactionRecordSchemaDetector.TRANSACTION_PREFIX, fields[0].getName());
        assertEquals(CustomTransactionRecordSchemaDetector.CUSTOMER, fields[1].getName());
        assertEquals(CustomTransactionRecordSchemaDetector.PRODUCT_ID, fields[2].getName());
    }

}
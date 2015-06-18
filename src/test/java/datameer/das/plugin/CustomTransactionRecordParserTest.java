package datameer.das.plugin;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import datameer.com.google.common.collect.Lists;

import datameer.dap.sdk.common.Field;
import datameer.dap.sdk.common.RawRecord;
import datameer.dap.sdk.importjob.ListRawRecordCollector;
import datameer.dap.sdk.importjob.MultipleSourceRecordParser;
import datameer.dap.sdk.importjob.ParseConfiguration;
import datameer.dap.sdk.importjob.RecordParser;
import datameer.dap.sdk.importjob.RecordParserWrapper;

public class CustomTransactionRecordParserTest {

    private CustomTransactionRecordSchemaDetector _schemaDetector;
    private CustomTransactionRecordParser _parser;
    private ParseConfiguration _parseConfiguration;

    @Before
    public void setUp() {
        _schemaDetector = new CustomTransactionRecordSchemaDetector();
        _parser = new CustomTransactionRecordParser(_schemaDetector.detectFields());
        _parseConfiguration = ParseConfiguration.NORMAL;
    }

    @Test
    public void testParseRecords() throws Exception {
        String[] lines = getFileContent();
        List<RawRecord> records = parseRecords(_parser, lines);
        assertEquals(2, records.size());
        assertArrayEquals(new Object[] { "1", "SomeCustomer", "product1" }, records.get(0).getValues().toArray());
        assertArrayEquals(new Object[] { "2", "OtherCustomer", "product2" }, records.get(1).getValues().toArray());
    }

    @Test
    public void testOnlyInclude1Field() throws Exception {
        Field[] fields = _schemaDetector.detectFields();
        for (Field field : fields) {
            field.setInclude(false);
        }
        fields[1].setInclude(true);
        _parser = new CustomTransactionRecordParser(fields);
        String[] fileContent = getFileContent();
        List<RawRecord> records = parseRecords(_parser, fileContent);
        assertEquals(2, records.size());
        assertArrayEquals(new Object[] { "SomeCustomer" }, records.get(0).getValues().toArray());
        assertArrayEquals(new Object[] { "OtherCustomer" }, records.get(1).getValues().toArray());
    }

    private String[] getFileContent() {
        return "Transaction:1\nCustomer:SomeCustomer\nProduct-Id:product1\n\nTransaction:2\nCustomer:OtherCustomer\nProduct-Id:product2".split("\n");
    }

    @Test
    public void testIsValidFirstReadRecordSource() {
        assertTrue(_parser.isValidFirstReadRecordSource("Transaction:1"));
        assertFalse(_parser.isValidFirstReadRecordSource("Customer:SomeCustomer"));
    }

    @Test
    public void testRecordCanSpanMultipleRecordSources() {
        assertTrue(_parser.recordCanSpanMultipleRecordSources());
    }

    private <T> List<RawRecord> parseRecords(RecordParser<T> recordParser, T... recordSources) throws Exception {
        List<RawRecord> records = Lists.newArrayList();
        ListRawRecordCollector collector = new ListRawRecordCollector(records, Integer.MAX_VALUE, null);
        recordParser.configureRecordCollector(collector);
        for (T recordSource : recordSources) {
            recordParser.parse(_parseConfiguration, collector, recordSource);
        }
        MultipleSourceRecordParser<T> multipleSourceParser = RecordParserWrapper.getMultiSourceRecordParser(recordParser);
        if (multipleSourceParser != null) {
            multipleSourceParser.convertFinalRecords(_parseConfiguration, collector, true);
        }
        return records;
    }
}
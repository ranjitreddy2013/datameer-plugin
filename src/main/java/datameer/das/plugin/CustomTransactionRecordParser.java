package datameer.das.plugin;

import datameer.dap.sdk.common.Field;
import datameer.dap.sdk.common.RawRecordCollector;
import datameer.dap.sdk.importjob.FixedStructureTextParser;
import datameer.dap.sdk.importjob.MultiLineFixedStructureTextRecordParser;
import datameer.dap.sdk.importjob.TextFieldAnalyzer;
import datameer.dap.sdk.util.ManifestMetaData;

public class CustomTransactionRecordParser extends MultiLineFixedStructureTextRecordParser {

    public static class CustomTransactionTextParser implements FixedStructureTextParser {

        private static final long serialVersionUID = ManifestMetaData.SERIAL_VERSION_UID;

        @Override
        public String[] parseStringFields(String line) throws Exception {
            String[] linesOfRecord = line.split("\n[\r]?");
            for (int i = 0; i < linesOfRecord.length; i++) {
                linesOfRecord[i] = linesOfRecord[i].split(":", 2)[1];
            }
            return linesOfRecord;
        }

        @Override
        public void configureSchemaDetection(TextFieldAnalyzer fieldAnalyzer) {
            // nothing todo
        }

        @Override
        public void configureRecordCollector(RawRecordCollector recordCollector) {
            // nothing todo
        }
    }

    public CustomTransactionRecordParser(Field[] allFields) {
        super(allFields, new CustomTransactionTextParser());
    }

    /**
     * This method helps to ensure the validness of splits over record sources. If the containing
     * record source element is a file and the record source is a line of that file, then this
     * method should return false only if the given line can't be parsed without previous lines.
     * This will not be called for the first split of a file.
     * 
     * Only implement if {@link #recordCanSpanMultipleRecordSources()} is true!
     * 
     * @return true if the give record source is valid as a first read record source
     */
    @Override
    public boolean isValidFirstReadRecordSource(String line) {
        return line.startsWith(CustomTransactionRecordSchemaDetector.TRANSACTION_PREFIX);
    }

    @Override
    protected boolean isLastRow(String line) {
        return line.startsWith(CustomTransactionRecordSchemaDetector.PRODUCT_ID);
    }

    @Override
    protected boolean isRecordSeparatorLine(String line) {
        return line.trim().isEmpty();
    }
}
package datameer.das.plugin;

import org.apache.hadoop.conf.Configuration;

import datameer.dap.sdk.common.Field;
import datameer.dap.sdk.importjob.RecordParser;
import datameer.dap.sdk.importjob.RecordSchemaDetector;
import datameer.dap.sdk.importjob.TextBasedFileType;

public class CustomTransactionFileType extends TextBasedFileType {

    private static final String ID = CustomTransactionFileType.class.getName();

    @Override
    public RecordSchemaDetector<String> createRecordSchemaDetector(FileTypeModel<String> model) {
        return new CustomTransactionRecordSchemaDetector();
    }

    @Override
    public RecordParser<String> createRecordParser(Field[] fields, Configuration conf, FileTypeModel<String> model) {
        return new CustomTransactionRecordParser(fields);
    }

    @Override
    public String getName() {
        return "Cloak";
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public boolean canAutoMergeNewFields() {
        return true;
    }
}

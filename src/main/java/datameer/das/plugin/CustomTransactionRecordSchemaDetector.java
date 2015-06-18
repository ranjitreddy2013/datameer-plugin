package datameer.das.plugin;

import java.util.ArrayList;
import java.util.List;

import datameer.dap.sdk.common.Field;
import datameer.dap.sdk.importjob.NoDataRecordSchemaDetector;
import datameer.dap.sdk.schema.ValueType;

public class CustomTransactionRecordSchemaDetector extends NoDataRecordSchemaDetector<String> {

    static final String TRANSACTION_PREFIX = "Transaction";
    static final String CUSTOMER = "Customer";
    static final String PRODUCT_ID = "ProductId";

    /**
     * Makes a good guess for the field definition. This includes:
     * <ul>
     * <li>The number of fields</li>
     * <li>Suggestion for header names</li>
     * <li>Type for each field.</li>
     * </ul>
     * 
     * @return a good first guess of how fields for this data source might look like.
     */
    @Override
    public Field[] detectFields() {
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field(TRANSACTION_PREFIX, "0", ValueType.INTEGER));
        fields.add(new Field(CUSTOMER, "1", ValueType.STRING));
        fields.add(new Field(PRODUCT_ID, "2", ValueType.STRING));
        return fields.toArray(new Field[fields.size()]);
    }

}
package com.epam.booking.util;
import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.springframework.core.io.Resource;

import java.io.InputStream;

public class ColumnSensingReplacementDataSetLoader extends AbstractDataSetLoader {

    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        try (InputStream inputStream = resource.getInputStream()) {
            return createReplacementDataSet(builder.build(inputStream));
        }
    }

    private ReplacementDataSet createReplacementDataSet(FlatXmlDataSet dataSet) {
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
        replacementDataSet.addReplacementObject("[NULL]", null);
        return replacementDataSet;
    }
}
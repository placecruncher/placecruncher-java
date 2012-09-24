package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class AbstractObjectFactoryTest {
    public class TestObject {
        private String stringProperty;
        private Date dateProperty;
        private List<String> listProperty = new ArrayList<String>();
        public String getStringProperty() {
            return stringProperty;
        }
        public void setStringProperty(String stringProperty) {
            this.stringProperty = stringProperty;
        }
        public Date getDateProperty() {
            return dateProperty;
        }
        public void setDateProperty(Date dateProperty) {
            this.dateProperty = dateProperty;
        }
        public List<String> getListProperty() {
            return listProperty;
        }
        public void setListProperty(List<String> listProperty) {
            this.listProperty = listProperty;
        }
        public void addListValue(String value) {
            this.listProperty.add(value);
        }

    }

    class TestObjectFactory extends AbstractObjectFactory<TestObject> {

        @Override
        public TestObject instance(int id, Map<String, Object> properties) {
            TestObject object = new TestObject();
            return object;
        }
    }

    private TestObjectFactory factory;

    @Before
    public void setUp() {
        factory = new TestObjectFactory();
    }

    @Test
    public void setStringProperty() {
        String stringValue = "stringValue";
        TestObject o = factory.build("stringProperty", stringValue);

        Assert.assertEquals(stringValue, o.getStringProperty());
    }

    @Test
    public void setDateProperty() {
        Date value = new Date();
        TestObject o = factory.build("dateProperty", value);

        Assert.assertEquals(value, o.getDateProperty());
    }

    @Test
    public void setListProperty() {
        String value1 = "value1";
        String value2 = "value2";

        TestObject o = factory.build(
                "listProperty[0]", value1,
                "listProperty[2]", value2);

        Assert.assertEquals(value1, o.getListProperty().get(0));
        Assert.assertEquals(value2, o.getListProperty().get(2));

    }
}

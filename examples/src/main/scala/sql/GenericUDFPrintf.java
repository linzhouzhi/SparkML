package sql;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.BooleanObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.Text;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by lzz on 16/9/11.
 */
public class GenericUDFPrintf extends GenericUDF {
    private ObjectInspector[] argumentOIs;
    private final Text result = new Text();

    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        argumentOIs = objectInspectors;
        return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.US);
        String pattern = ((StringObjectInspector) argumentOIs[0]).getPrimitiveJavaObject(deferredObjects[0].get());
        ArrayList argumentList = new ArrayList();
        for( int i = 1; i < deferredObjects.length; i++){
            switch ( ((PrimitiveObjectInspector)argumentOIs[i]).getPrimitiveCategory() ) {
                case BOOLEAN: argumentList.add(((BooleanObjectInspector)argumentOIs[i]).get(deferredObjects[i].get()));
                    break;
                case BINARY:
                    argumentList.add(deferredObjects[i].get());
                    break;
                default:
                    argumentList.add(deferredObjects[i].get());
                    break;
            }
        }

        formatter.format(pattern,argumentList.toArray());
        result.set(sb.toString());
        return  result;
    }

    @Override
    public String getDisplayString(String[] children) {
        assert (children.length >= 2);
        StringBuilder sb = new StringBuilder();
        sb.append("printf(");
        for( int i = 0; i < children.length -1; i++){
            sb.append(children[i]).append(", ");
        }
        sb.append(children[children.length - 1]).append(")");
        return sb.toString();
    }
}

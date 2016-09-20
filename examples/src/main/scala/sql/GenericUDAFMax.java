package sql;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.exec.vector.expressions.aggregates.VectorAggregateExpression;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.UDFType;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;

import java.io.ObjectOutput;

/**
 * Created by lzz on 16/9/13.
 */

public class GenericUDAFMax extends AbstractGenericUDAFResolver {
    @Override
    public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters) throws SemanticException{
        if(parameters.length != 1){
            throw new UDFArgumentTypeException(parameters.length -1,"Exactly one argument is expected.");
        }
        ObjectInspector oi = TypeInfoUtils.getStandardJavaObjectInspectorFromTypeInfo(parameters[0]);
        if(!ObjectInspectorUtils.compareSupported(oi)){
            throw new UDFArgumentTypeException(parameters.length -1,
                    "Cannot support comparison of map<> type or complex type containing map<>.");
        }
        return new org.apache.hadoop.hive.ql.udf.generic.GenericUDAFMax.GenericUDAFMaxEvaluator();
    }

    @UDFType(distinctLike = true)
    public static class GenericUDAFMaxEvaluator extends GenericUDAFEvaluator{
        ObjectInspector inputOI;
        ObjectInspector outputOI;
        public ObjectInspector init(Mode m,ObjectInspector[] parameters) throws HiveException{
            assert (parameters.length == 1);
            super.init(m,parameters);
            inputOI = parameters[0];
            outputOI = ObjectInspectorUtils.getStandardObjectInspector(inputOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
            return outputOI;
        }

        static class MaxAgg implements AggregationBuffer{
            Object o;
        }

        public AggregationBuffer getNewAggregationBuffer() throws HiveException {
            MaxAgg result = new MaxAgg();
            return  result;
        }

        public void reset(AggregationBuffer agg) throws HiveException {
            MaxAgg myagg = (MaxAgg) agg;
            myagg.o = null;
        }

        public void iterate(AggregationBuffer agg, Object[] parameters) throws HiveException {
            assert (parameters.length == 1);
            merge(agg,parameters[0]);
        }

        public Object terminatePartial(AggregationBuffer agg) throws HiveException {
            return terminate(agg);
        }

        public void merge(AggregationBuffer agg, Object partial) throws HiveException {
            if(partial != null){
                MaxAgg myagg = (MaxAgg) agg;
                int r = ObjectInspectorUtils.compare(myagg.o,outputOI,partial,inputOI);
                if(myagg.o == null || r < 0){
                    myagg.o = ObjectInspectorUtils.copyToStandardObject(partial,inputOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
                }
            }
        }

        public Object terminate(AggregationBuffer agg) throws HiveException {
            MaxAgg myagg = (MaxAgg) agg;
            return myagg.o;
        }
    }
}

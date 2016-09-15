package sql;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;

/**
 * Created by lzz on 16/9/11.
 */
public class UDAFAvg extends UDAF {
    public static class UDAFAvgState{
        private long count;
        private double sum;
    }

    public static class UDAFAvgEvaluator implements UDAFEvaluator{
        UDAFAvgState state;
        public UDAFAvgEvaluator(){
            super();
            state = new UDAFAvgState();
            init();
        }
        @Override
        public void init() {
            state.sum = 0;
            state.count = 0;
        }
        public void iterate(Double o){
            if( o != null ){
                state.sum += o;
                state.count++;
            }
        }

        public UDAFAvgState terminatePartial(){
            return state.count == 0 ? null : state;
        }

        public void merge(UDAFAvgState o){
            if(o != null){
                state.sum += o.sum;
                state.count += o.count;
            }
        }

        public Double terminate(){
            return state.count == 0 ? null : Double.valueOf(state.sum / state.count );
        }
    }
}

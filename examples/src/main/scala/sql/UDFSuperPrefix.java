package sql;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by lzz on 16/9/11.
 */
public class UDFSuperPrefix extends UDF {
    private int lineNo = 0;
    private final Text cache = new Text();

    public Text evaluate(Text prefix){
        StringBuffer sb = new StringBuffer();
        if( prefix != null ){
            sb.append(prefix.toString());
        }
        sb.append("_");
        sb.append(++lineNo);
        cache.set(sb.toString());
        return cache;
    }

    public  String evaluate(String prefix){
        StringBuffer sb = new StringBuffer();
        if(prefix != null){
            sb.append(prefix.toString());
        }
        sb.append("_");
        sb.append(++lineNo);
        return sb.toString();
    }

}

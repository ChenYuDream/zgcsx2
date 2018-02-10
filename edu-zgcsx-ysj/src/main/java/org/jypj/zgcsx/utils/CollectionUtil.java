package org.jypj.zgcsx.utils;

import java.util.Collection;

/**
 * Created by jian_wu on 2017/11/22.
 * @author jian_wu
 */
public class CollectionUtil {

    public static Boolean isEmpty(Collection<?> collection ){
        if(collection!=null&&collection.size()>0){
            return false;
        }else{
            return true;
        }
    }

    public static Boolean isNotEmpty(Collection<?> collection){
        if(collection!=null&&collection.size()>0){
            return true;
        }else{
            return false;
        }
    }

}

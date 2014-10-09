
package com.nostra13.universalimageloader.cache;

import java.io.Serializable;

import android.R.bool;
import android.graphics.Bitmap;

//public interface Cache {
//
//    public CacheEntry get(String key);
//
//    public boolean put(String key, CacheEntry entry);
//
//    public void remove(String key);
//
//    public void clear();
//
//    public static class CacheEntry implements Serializable {
//
//        private static final long serialVersionUID = 1L;
//
//        public CacheEntry(Object data){
//            this.data = data;
//        }
//        
//        public Object data;
//
//        public int size() {
//            if (data==null) {
//                return 0;
//            }
//            if (data instanceof byte[]) {
//                return ((byte[])data).length;
//            }
//            if (data instanceof Bitmap) {
//                return ((Bitmap)data).getByteCount();
//            }
//            return 0;
//        }
//
//    }
//
//}

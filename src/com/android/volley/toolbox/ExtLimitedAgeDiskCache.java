
package com.android.volley.toolbox;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.android.volley.Cache;
import com.android.volley.VolleyLog;
import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;

public class ExtLimitedAgeDiskCache implements Cache {

    private final LimitedAgeDiskCache limitedAgeDiskCache;

    /** The root directory to use for the cache. */
    private final File mRootDirectory;

    /**
     * Constructs an instance of the DiskBasedCache at the specified directory.
     * 
     * @param rootDirectory The root directory of the cache.
     */
    public ExtLimitedAgeDiskCache(File rootDirectory, long maxAge) {
        mRootDirectory = rootDirectory;
        limitedAgeDiskCache = new LimitedAgeDiskCache(mRootDirectory, maxAge);
    }

    /**
     * Clears the cache. Deletes all cached files from disk.
     */
    @Override
    public synchronized void clear() {
        limitedAgeDiskCache.clear();
        VolleyLog.d("Cache cleared.");
    }

    /**
     * Returns the cache entry with the specified key if it exists, null
     * otherwise.
     */
    @Override
    public synchronized Entry get(String key) {
        FileInputStream inputStream = null;
        try {
            Entry entry = new Entry();
            File file = limitedAgeDiskCache.get(key);
            if (file==null) {
                return null;
            }
            inputStream = new FileInputStream(file);
            entry.data = streamToBytes(inputStream, (int)file.length());
            entry.ttl = System.currentTimeMillis()+10000;
            return entry;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally{
            if (inputStream!=null) {
                try {
                    inputStream.close();
                } catch (Exception e2) {
                    return null;
                }
            }
        }
    }

    /**
     * Initializes the DiskBasedCache by scanning for all files currently in the
     * specified root directory. Creates the root directory if necessary.
     */
    @Override
    public synchronized void initialize() {
        if (!mRootDirectory.exists()) {
            if (!mRootDirectory.mkdirs()) {
                VolleyLog.e("Unable to create cache dir %s", mRootDirectory.getAbsolutePath());
            }
            return;
        }
    }

    /**
     * Invalidates an entry in the cache.
     * 
     * @param key Cache key
     * @param fullExpire True to fully expire the entry, false to soft expire
     */
    @Override
    public synchronized void invalidate(String key, boolean fullExpire) {

    }

    /**
     * Puts the entry with the specified key into the cache.
     */
    @Override
    public synchronized void put(String key, Entry entry) {
        try {
            limitedAgeDiskCache.save(key, new ByteArrayInputStream(entry.data), null);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Removes the specified key from the cache if it exists.
     */
    @Override
    public synchronized void remove(String key) {
        limitedAgeDiskCache.remove(key);
    }

    /**
     * Returns a file object for the given cache key.
     */
    public File getFileForKey(String key) {
        return limitedAgeDiskCache.get(key);
    }


    /**
     * Reads the contents of an InputStream into a byte[].
     */
    private static byte[] streamToBytes(InputStream in, int length) throws IOException {
        byte[] bytes = new byte[length];
        int count;
        int pos = 0;
        while (pos < length && ((count = in.read(bytes, pos, length - pos)) != -1)) {
            pos += count;
        }
        if (pos != length) {
            throw new IOException("Expected " + length + " bytes, read " + pos + " bytes");
        }
        return bytes;
    }

}

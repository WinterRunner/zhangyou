package com.threesome.basecommon.net;

import java.util.Map;
import java.util.TreeMap;

/**
 * author: L.K.X
 * created on: 2017/7/15 下午6:22
 * description:
 */

public class NetMap {
    private Map<String, Object> map;
    public NetMap() {
        map = new TreeMap<>();
    }

    public NetMap put(String key, Object value) {
        map.put(key, value);
        return this;
    }
    public Map build() {
        return map;
    }
}

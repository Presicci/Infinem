package io.ruin.data.impl;

import com.google.common.collect.Maps;
import com.google.gson.*;
import io.ruin.api.utils.JsonUtils;
import io.ruin.api.utils.ServerWrapper;
import io.ruin.data.DataFile;
import io.ruin.model.map.MultiZone;
import io.ruin.model.map.Region;
import io.ruin.model.map.dynamic.DynamicMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class region_keys extends DataFile {

    public static final int[] NULL_KEYS = new int[4];

    @Override
    public String path() {
       // return "region_keys.json";
        return "xteas.json";
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public Object fromJson(String fileName, String json) {

        /*Map<Integer, int[]> key_map;
        key_map = JsonUtils.fromJson(json, Map.class, Integer.class, int[].class);
        for(int regionId = 0; regionId < Region.LOADED.length; regionId++) {
            Region region = new Region(regionId);
            if((region.keys = key_map.get(regionId)) != null && !isValid(region.id, region.keys)) {
                //System.err.println("Invalid Keys for Region (" + regionId + "): base=(" + region.baseX + ", " + region.baseY + ") keys=" + Arrays.toString(region.keys));
                region.keys = null;
            }
            Region.LOADED[regionId] = region;
        }


        //Load each region
        for(Region region : Region.LOADED) {
            try {
                region.init();
            } catch (Exception ex) {
                System.err.println("Error loading region " + region.id);
                ex.printStackTrace();
            }
        }

        MultiZone.load();
        DynamicMap.load();
        return key_map;*/
        List<XTEA> xteas = JsonUtils.fromJson(json, List.class, XTEA.class);
        Map<Integer, int[]> keys = new HashMap<>(xteas.size());
        for (XTEA xtea : xteas) {
            keys.put(xtea.getMapsquare(), xtea.getKey());
        }

        for(int regionId = 0; regionId < Region.LOADED.length; regionId++) {
            Region region = new Region(regionId);
            if((region.keys = keys.get(regionId)) != null && !isValid(region.id, region.keys)) {
                System.err.println("Invalid Keys for Region (" + regionId + "): base=(" + region.baseX + ", " + region.baseY + ") keys=" + Arrays.toString(region.keys));
                region.keys = null;
            }
            Region.LOADED[regionId] = region;
        }
        for(Region region : Region.LOADED) {
            try {
                region.init();
            } catch (Exception ex) {
                System.err.println("Error loading region " + region.id);
                ex.printStackTrace();
            }
        }

        MultiZone.load();
        DynamicMap.load();
        return keys;
    }

    private static boolean isValid(int id, int[] keys) {
        Region r = new Region(id);
        r.keys = keys;
        try {
            r.getLandscapeData();
            return true;
        } catch(Throwable t) {
            return false;
        }
    }

}
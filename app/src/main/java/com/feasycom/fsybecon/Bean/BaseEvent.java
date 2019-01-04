package com.feasycom.fsybecon.Bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Copyright 2017 Shenzhen Feasycom Technology co.,Ltd
 */

public class BaseEvent implements Serializable {
    public static final int  DELE_BEACON_EVENT=1;
    public static final int  OTA_EVENT_YES=2;
    public static final int  OTA_EVENT_NO=3;
    public static final int  PIN_EVENT=4;

    private int eventId;
    private String index;
    private HashMap<String,Object> hm=new HashMap<String,Object>();
    public BaseEvent(int evenid) {
        this.eventId=evenid;
    }
    public BaseEvent(String index,int evenid) {
        this.index = index;
        this.eventId=evenid;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void  setObject(String key,Object value){
        hm.put(key,value);
    }
    public Object getObject(String key){
        return hm.get(key);
    }
}

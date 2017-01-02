package com.hooapps.todolist;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 12/29/16.
 */

@IgnoreExtraProperties
public class Task {
    String priority;
    String payload;

    public Task(String a, String b){
        payload = a;
        priority = b;
    }

    public Task() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("payload", payload);
        result.put("priority", priority);
        return result;
    }
}

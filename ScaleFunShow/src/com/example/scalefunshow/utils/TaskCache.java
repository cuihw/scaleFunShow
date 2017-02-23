package com.example.scalefunshow.utils;

import com.example.scalefunshow.bean.TaskBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuihuawei on 2/23/2017.
 */

public class TaskCache {

    static List<TaskBean> taskList = new ArrayList<TaskBean>();
    public static TaskBean current;
    public static List<TaskBean> getTaskList() {
        return taskList;
    }

    public static void setTaskList(List<TaskBean> taskList) {
        TaskCache.taskList = taskList;
    }
}

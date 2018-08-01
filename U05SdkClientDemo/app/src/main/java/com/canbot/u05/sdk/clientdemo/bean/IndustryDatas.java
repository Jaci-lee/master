package com.canbot.u05.sdk.clientdemo.bean;

import android.graphics.drawable.Drawable;

public class IndustryDatas {

        private int id;

        /**
         * 图标名称
         */
        private String taskName;

        private Runnable runnable;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getTaskName() {
                return taskName;
        }

        public void setTaskName(String taskName) {
                this.taskName = taskName;
        }

        public Runnable getRunnable() {
                return runnable;
        }

        public void setRunnable(Runnable runnable) {
                this.runnable = runnable;
        }


        public IndustryDatas(int id, String taskName, Runnable runnable) {
                this.id = id;
                this.taskName = taskName;
                this.runnable = runnable;
        }

        @Override
        public String toString() {
                return "IndustryDatas{" +
                        "id=" + id +
                        ", taskName='" + taskName + '\'' +
                        ", runnable=" + runnable +
                        '}';
        }
}

package com.canbot.u05.sdk.clientdemo.bean;


import java.util.Arrays;


public class PersonData {

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }


        private int id;


        private String name;


        private String sex;


        private String content;

        /**
         * 识别回复内容类型，1 表示tts  2 表示 音频文件（此音频文件必须存放在机器人头部）
         */
        private int voicetype;

        private byte[] features;

        private String identification;

        private String path;

        private String email;

        private String phone;

        private long time;

        private String dirType;

        private int groupId = 0;

        private int isVip = 0;

        public int getIsVip() {
                return isVip;
        }

        public void setIsVip(int isVip) {
                this.isVip = isVip;
        }

        public int getGroupId() {
                return groupId;
        }

        public void setGroupId(int groupId) {
                this.groupId = groupId;
        }

        public String getDirType() {
                return dirType;
        }

        public void setDirType(String dirType) {
                this.dirType = dirType;
        }

        public long getTime() {
                return time;
        }

        public void setTime(long time) {
                this.time = time;
        }


/*	@DatabaseField(columnName = "time")
    private long time;*/

        public PersonData(String name, String sex, String content, int voicetype, byte[] features, String identification,
                          String path, String email, String phone, int groupId) {
                super();
                this.name = name;
                this.sex = sex;
                this.content = content;
                this.voicetype = voicetype;
                this.features = features;
                this.identification = identification;
                this.path = path;
                this.email = email;
                this.phone = phone;
                this.groupId = groupId;
        }

        public PersonData(String name, String sex, String content, int voicetype, byte[] features, String identification,
                          String path, String email, String phone, int groupId, int isVip) {
                super();
                this.name = name;
                this.sex = sex;
                this.content = content;
                this.voicetype = voicetype;
                this.features = features;
                this.identification = identification;
                this.path = path;
                this.email = email;
                this.phone = phone;
                this.groupId = groupId;
                this.isVip = isVip;
        }

        public PersonData(byte[] features, PersonVerifyData data) {
                super();
                this.features = features;
                this.name = data.getName();
                this.sex = data.getSex();
                this.content = data.getContent();
                this.voicetype = data.getVoicetype();
                this.identification = data.getIdentification();
                this.path = data.getPath();
                this.email = data.getEmail();
                this.phone = data.getPhone();
                this.groupId = data.getGroupId();
                this.isVip = data.getIsVip();
        }

        public PersonData() {
                super();
        }

        @Override
        public String toString() {
                return "PersonData [name=" + name + ", sex=" + sex + ", content=" + content + ", voicetype=" + voicetype
                        + ", features=" + Arrays.toString(features) + ", identification=" + identification + ", path=" + path
                        + ", email=" + email + ", phone=" + phone + ", groupId=" + groupId + ",isVip=" + isVip + "]\n";
        }


        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getSex() {
                return sex;
        }

        public void setSex(String sex) {
                this.sex = sex;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public int getVoicetype() {
                return voicetype;
        }

        public void setVoicetype(int voicetype) {
                this.voicetype = voicetype;
        }

        public byte[] getFeatures() {
                return features;
        }

        public void setFeatures(byte[] features) {
                this.features = features;
        }

        public String getIdentification() {
                return identification;
        }

        public void setIdentification(String identification) {
                this.identification = identification;
        }

        public String getPath() {
                return path;
        }

        public void setPath(String path) {
                this.path = path;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }
}

package com.canbot.u05.sdk.clientdemo.bean;

public class PersonVerifyData {

        public static final int VOICE_TYPE = 2;

        public static final int TTS_TYPE = 1;

        public static final int DEFAULT_TYPE = 0;

        private String name;

        private String sex;

        private String content;

        private int voicetype;//8423 tts : 8426 .mp3

        private String identification;

        private String path;

        private String email;

        private String phone;

        private int groupId = 1;

        private int isVip = 0;

        public PersonVerifyData(String name, String sex, String content, int voicetype, String identification, String path,
                                String email, String phone, int groupId, int isVip) {
                super();
                this.name = name;
                this.sex = sex;
                this.content = content;
                this.voicetype = voicetype;
                this.identification = identification;
                this.path = path;
                this.email = email;
                this.phone = phone;
                this.groupId = groupId;
                this.isVip = isVip;
        }

        public PersonVerifyData(String name, String sex, String content, int voicetype, String identification, String path,
                                String email, String phone, int groupId) {
                super();
                this.name = name;
                this.sex = sex;
                this.content = content;
                this.voicetype = voicetype;
                this.identification = identification;
                this.path = path;
                this.email = email;
                this.phone = phone;
                this.groupId = groupId;
        }


        public PersonVerifyData() {
                super();
        }

        public int getGroupId() {
                return groupId;
        }

        public void setGroupId(int groupId) {
                this.groupId = groupId;
        }


        public PersonVerifyData(PersonData one) {
                this.name = one.getName();
                this.sex = one.getSex();
                this.content = one.getContent();
                this.voicetype = one.getVoicetype();
                this.identification = one.getIdentification();
                this.path = one.getPath();
                this.email = one.getEmail();
                this.phone = one.getPhone();
                this.groupId = one.getGroupId();
                this.groupId = one.getGroupId();
                this.isVip = one.getIsVip();
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

        /**
         * 8423 tts : 8426 .mp3
         *
         * @param voicetype
         */
        public void setVoicetype(int voicetype) {
                this.voicetype = voicetype;
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

        public int getIsVip() {
                return isVip;
        }

        public void setIsVip(int isVip) {
                this.isVip = isVip;
        }

        @Override
        public String toString() {
                return "PersonVerifyData [name=" + name + ", sex=" + sex + ", content=" + content + ", voicetype=" + voicetype
                        + ", identification=" + identification + ", path=" + path + ", email=" + email + ", phone=" + phone
                        + ", groupId=" + groupId + ",isVip=" + isVip + " ]\n";
        }

}

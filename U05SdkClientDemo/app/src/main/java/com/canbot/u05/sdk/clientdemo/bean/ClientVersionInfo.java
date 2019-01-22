package com.canbot.u05.sdk.clientdemo.bean;


/**
 * @author zmp
 * Created by zmp on 2018/12/13
 * 发送客户端版本号
 */
public class ClientVersionInfo {

        /**
         * 硬件版本号.
         */
        private HardwareVersionInfo hardwareVersionInfo;

        /**
         * slam版本号.
         */
        private String slamVersion;

        /**
         * 头部固件版本号
         */
        private String touDeviceVersion;

        /**
         * 头部软件版本号.
         */
        private String touVersion;

        /**
         * lam版本号.
         */
        private String gpuVersion;

        /**
         * 头部mac地址.
         */
        private String touMacAddress;

        /**
         * 胸口mac地址.
         */
        private String macAddress;

        /**
         * 胸口固件版本号
         */
        private String deviceVersion;

        /**
         * 胸口软件版本
         */
        private String versionName;

        public ClientVersionInfo() {
        }

        public HardwareVersionInfo getHardwareVersionInfo() {
                return hardwareVersionInfo;
        }

        public void setHardwareVersionInfo(HardwareVersionInfo hardwareVersionInfo) {
                this.hardwareVersionInfo = hardwareVersionInfo;
        }

        public String getSlamVersion() {
                return slamVersion;
        }

        public void setSlamVersion(String slamVersion) {
                this.slamVersion = slamVersion;
        }

        public String getTouDeviceVersion() {
                return touDeviceVersion;
        }

        public void setTouDeviceVersion(String touDeviceVersion) {
                this.touDeviceVersion = touDeviceVersion;
        }

        public String getTouVersion() {
                return touVersion;
        }

        public void setTouVersion(String touVersion) {
                this.touVersion = touVersion;
        }

        public String getGpuVersion() {
                return gpuVersion;
        }

        public void setGpuVersion(String gpuVersion) {
                this.gpuVersion = gpuVersion;
        }

        public String getTouMacAddress() {
                return touMacAddress;
        }

        public void setTouMacAddress(String touMacAddress) {
                this.touMacAddress = touMacAddress;
        }

        public String getMacAddress() {
                return macAddress;
        }

        public void setMacAddress(String macAddress) {
                this.macAddress = macAddress;
        }

        public String getDeviceVersion() {
                return deviceVersion;
        }

        public void setDeviceVersion(String deviceVersion) {
                this.deviceVersion = deviceVersion;
        }

        public String getVersionName() {
                return versionName;
        }

        public void setVersionName(String versionName) {
                this.versionName = versionName;
        }

        @Override
        public String toString() {
                return "ClientVersionInfo{" +
                       "hardwareVersionInfo=" + hardwareVersionInfo +
                       ", slamVersion='" + slamVersion + '\'' +
                       ", touDeviceVersion='" + touDeviceVersion + '\'' +
                       ", touVersion='" + touVersion + '\'' +
                       ", gpuVersion='" + gpuVersion + '\'' +
                       ", touMacAddress='" + touMacAddress + '\'' +
                       ", macAddress='" + macAddress + '\'' +
                       ", deviceVersion='" + deviceVersion + '\'' +
                       ", versionName='" + versionName + '\'' +
                       '}';
        }
}

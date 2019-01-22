package com.canbot.u05.sdk.clientdemo.bean;

/**
 * Created by Administrator on 2017/9/20.
 */

/**
 * Byte0~Byte1： 头部上下版本 （2Byte）
 * Byte2~Byte3： 头部上下loader版本（2Byte）
 * Byte4~Byte5： 头部左右版本（2Byte）
 * Byte6~Byte7： 头部左右loader版本（2Byte）
 * Byte8~Byte9： 左臂肩部前后版本（2Byte）
 * Byte10~Byte11：左臂肩部前后loader版本（2Byte）
 * Byte12~Byte13： 左臂肩部上下版本（2Byte）
 * Byte14~Byte15： 左臂肩部上下loader版本（2Byte）
 * Byte16~Byte17： 左臂肘部旋转版本（2Byte）
 * Byte18~Byte19： 左臂肘部旋转loader版本（2Byte）
 * Byte20~Byte21： 左臂肘部上下版本（2Byte）
 * Byte22~Byte23： 左臂肘部上下loader版本（2Byte）
 * Byte24~Byte25： 左臂手腕版本（2Byte）
 * Byte26~Byte27： 左臂手腕loader版本（2Byte）
 * Byte28~Byte29： 右臂肩部前后版本（2Byte）
 * Byte30~Byte31： 右臂肩部前后loader版本（2Byte）
 * Byte32~Byte33： 右臂肩部上下版本（2Byte）
 * Byte34~Byte35： 右臂肩部上下loader版本（2Byte）
 * Byte36~Byte37： 右臂肘部旋转版本（2Byte）
 * Byte38~Byte39： 右臂肘部旋转loader版本（2Byte）
 * Byte40~Byte41： 右臂肘部上下版本（2Byte）
 * Byte42~Byte43： 右臂肘部上下loader版本（2Byte）
 * Byte44~Byte45： 右臂手腕版本（2Byte）
 * Byte46~Byte47： 右臂手腕loader版本（2Byte）
 * Byte48~Byte49： 左手腕版本（2Byte）
 * Byte50~Byte51： 左手loader版本（2Byte）
 * Byte52~Byte53： 右手版本（2Byte）
 * Byte54~Byte55： 右手loader版本（2Byte）
 * Byte56~Byte59：FPGA版本（4Byte）
 * Byte60~Byte63： M4版本（4Byte）
 * Byte64~Byte67： M4 loader版本（4Byte）
 * Byte68~Byte71： 动作库版本（4Byte）
 * Byte72~Byte75： 动作库创建时间（4Byte）
 */
public class HardwareVersionInfo {

        //------------ head ---------------------//
        private String head_ver; // 头部上下版本

        private String head_ver_loader;//  头部上下loader版本

        private String head_hor;//  头部左右版本

        private String head_hor_loader;// 头部上下版本


        //------------ left_arm---------------------//
        private String left_arm_shoulder_ahead_back;// 左臂肩部前后版本

        private String left_arm_shoulder_ahead_back_loader;// 左臂肩部前后loader版本

        private String left_arm_shoulder_top_down;// 左臂肩部上下版本

        private String left_arm_shoulder_top_down_loader;// 左臂肩部上下loader版本

        private String left_arm_elbow_rotate;// 左臂肘部旋转版本

        private String left_arm_elbow_rotate_loader;// 左臂肘部旋转loader版本

        private String left_arm_elbow_top_down;// 左臂肘部上下版本

        private String left_arm_elbow_top_down_loader;// 左臂肘部上下loader版本

        private String left_arm_wrist;//左臂手腕版本

        private String left_arm_wrist_loader;//左臂手腕loader版本

        //------------ right_arm---------------------//
        private String right_arm_shoulder_ahead_back;// 左臂肩部前后版本

        private String right_arm_shoulder_ahead_back_loader;// 左臂肩部前后loader版本

        private String right_arm_shoulder_top_down;// 左臂肩部上下版本

        private String right_arm_shoulder_top_down_loader;// 左臂肩部上下loader版本

        private String right_arm_elbow_rotate;// 左臂肘部旋转版本

        private String right_arm_elbow_rotate_loader;// 右臂肘部旋转loader版本

        private String right_arm_elbow_top_down;// 右臂肘部上下版本

        private String right_arm_elbow_top_down_loader;// 右臂肘部上下loader版本

        private String right_arm_wrist;//右臂手腕版本

        private String right_arm_wrist_loader;//右臂手腕loader版本


        private String left_arm;//左手版本

        private String left_arm_loader;//左手loader版本

        private String right_arm;//右手版本

        private String right_arm_loader;//右手loader·版本


        //------------ IC---------------------//
        private String fpga;//FPGA版本（4Byte）

        private String m4;//M4版本（4Byte）

        private String m4_loader;//M4 loader版本（4Byte）


        //------------ action---------------------//
        private String actionLib;//动作库版本（4Byte）

        private String actionLib_createTime;//动作库创建时间（4Byte）


        public HardwareVersionInfo() {
        }

        public String getActionLibCreateTime() {
                return actionLib_createTime;
        }

        public String getHeadVer() {
                return head_ver;
        }

        public String getHeadVerLoader() {
                return head_ver_loader;
        }

        public String getHeadHor() {
                return head_hor;
        }

        public String getHeadHorLoader() {
                return head_hor_loader;
        }

        public String getLeftArmShoulderAheadBack() {
                return left_arm_shoulder_ahead_back;
        }

        public String getLeftArmShoulderAheadBackLoader() {
                return left_arm_shoulder_ahead_back_loader;
        }

        public String getLeftArmShoulderTopDown() {
                return left_arm_shoulder_top_down;
        }

        public String getLeftArmShoulderTopDownLoader() {
                return left_arm_shoulder_top_down_loader;
        }

        public String getLeftArmElbowRotate() {
                return left_arm_elbow_rotate;
        }

        public String getLeftArmElbowRotateLoader() {
                return left_arm_elbow_rotate_loader;
        }

        public String getLeftArmElbowTopDown() {
                return left_arm_elbow_top_down;
        }

        public String getLeftArmElbowTopDownLoader() {
                return left_arm_elbow_top_down_loader;
        }

        public String getLeftArmWrist() {
                return left_arm_wrist;
        }

        public String getLeftArmWristLoader() {
                return left_arm_wrist_loader;
        }

        public String getRightArmShoulderAheadBack() {
                return right_arm_shoulder_ahead_back;
        }

        public String getRightArmShoulderAheadBackLoader() {
                return right_arm_shoulder_ahead_back_loader;
        }

        public String getRightArmShoulderTopDown() {
                return right_arm_shoulder_top_down;
        }

        public String getRightArmShoulderTopDownLoader() {
                return right_arm_shoulder_top_down_loader;
        }

        public String getRightArmElbowRotate() {
                return right_arm_elbow_rotate;
        }

        public String getRightArmElbowRotateLoader() {
                return right_arm_elbow_rotate_loader;
        }

        public String getRightArmElbowTopDown() {
                return right_arm_elbow_top_down;
        }

        public String getRightArmElbowTopDownLoader() {
                return right_arm_elbow_top_down_loader;
        }

        public String getRightArmWrist() {
                return right_arm_wrist;
        }

        public String getRightArmWristLoader() {
                return right_arm_wrist_loader;
        }

        public String getLeftArm() {
                return left_arm;
        }

        public String getLeftArmLoader() {
                return left_arm_loader;
        }

        public String getRightArm() {
                return right_arm;
        }

        public String getRightArmLoader() {
                return right_arm_loader;
        }

        public String getFpga() {
                return fpga;
        }

        public String getM4() {
                return m4;
        }

        public String getM4Loader() {
                return m4_loader;
        }

        public String getActionLib() {
                return actionLib;
        }

        public void setHeadVer(String head_ver) {
                this.head_ver = head_ver;
        }

        public void setHeadVerLoader(String head_ver_loader) {
                this.head_ver_loader = head_ver_loader;
        }

        public void setHeadHor(String head_hor) {
                this.head_hor = head_hor;
        }

        public void setHeadHorLoader(String head_hor_loader) {
                this.head_hor_loader = head_hor_loader;
        }

        public void setLeftArmShoulderAheadBack(String left_arm_shoulder_ahead_back) {
                this.left_arm_shoulder_ahead_back = left_arm_shoulder_ahead_back;
        }

        public void setLeftArmShoulderAheadBackLoader(String left_arm_shoulder_ahead_back_loader) {
                this.left_arm_shoulder_ahead_back_loader = left_arm_shoulder_ahead_back_loader;
        }

        public void setLeftArmShoulderTopDown(String left_arm_shoulder_top_down) {
                this.left_arm_shoulder_top_down = left_arm_shoulder_top_down;
        }

        public void setLeftArmShoulderTopDownLoader(String left_arm_shoulder_top_down_loader) {
                this.left_arm_shoulder_top_down_loader = left_arm_shoulder_top_down_loader;
        }

        public void setLeftArmElbowRotate(String left_arm_elbow_rotate) {
                this.left_arm_elbow_rotate = left_arm_elbow_rotate;
        }

        public void setLeftArmElbowRotateLoader(String left_arm_elbow_rotate_loader) {
                this.left_arm_elbow_rotate_loader = left_arm_elbow_rotate_loader;
        }

        public void setLeftArmElbowTopDown(String left_arm_elbow_top_down) {
                this.left_arm_elbow_top_down = left_arm_elbow_top_down;
        }

        public void setLeftArmElbowTopDownLoader(String left_arm_elbow_top_down_loader) {
                this.left_arm_elbow_top_down_loader = left_arm_elbow_top_down_loader;
        }

        public void setLeftArmWrist(String left_arm_wrist) {
                this.left_arm_wrist = left_arm_wrist;
        }

        public void setLeftArmWristLoader(String left_arm_wrist_loader) {
                this.left_arm_wrist_loader = left_arm_wrist_loader;
        }

        public void setRightArmShoulderAheadBack(String right_arm_shoulder_ahead_back) {
                this.right_arm_shoulder_ahead_back = right_arm_shoulder_ahead_back;
        }

        public void setRightArmShoulderAheadBackLoader(String right_arm_shoulder_ahead_back_loader) {
                this.right_arm_shoulder_ahead_back_loader = right_arm_shoulder_ahead_back_loader;
        }

        public void setRightArmShoulderTopDown(String right_arm_shoulder_top_down) {
                this.right_arm_shoulder_top_down = right_arm_shoulder_top_down;
        }

        public void setRightArmShoulderTopDownLoader(String right_arm_shoulder_top_down_loader) {
                this.right_arm_shoulder_top_down_loader = right_arm_shoulder_top_down_loader;
        }

        public void setRightArmElbowRotate(String right_arm_elbow_rotate) {
                this.right_arm_elbow_rotate = right_arm_elbow_rotate;
        }

        public void setRightArmElbowRotateLoader(String right_arm_elbow_rotate_loader) {
                this.right_arm_elbow_rotate_loader = right_arm_elbow_rotate_loader;
        }

        public void setRightArmElbowTopDown(String right_arm_elbow_top_down) {
                this.right_arm_elbow_top_down = right_arm_elbow_top_down;
        }

        public void setRightArmElbowTopDownLoader(String right_arm_elbow_top_down_loader) {
                this.right_arm_elbow_top_down_loader = right_arm_elbow_top_down_loader;
        }

        public void setRightArmWrist(String right_arm_elbow_wrist) {
                this.right_arm_wrist = right_arm_elbow_wrist;
        }

        public void setRightArmWristLoader(String right_arm_elbow_wrist_loader) {
                this.right_arm_wrist_loader = right_arm_elbow_wrist_loader;
        }

        public void setLeftArm(String left_arm) {
                this.left_arm = left_arm;
        }

        public void setLeftArmLoader(String left_arm_loader) {
                this.left_arm_loader = left_arm_loader;
        }

        public void setRightArm(String right_arm) {
                this.right_arm = right_arm;
        }

        public void setRightArmLoader(String right_arm_loader) {
                this.right_arm_loader = right_arm_loader;
        }

        public void setFpga(String fpga) {
                this.fpga = fpga;
        }

        public void setM4(String m4) {
                this.m4 = m4;
        }

        public void setM4Loader(String m4_loader) {
                this.m4_loader = m4_loader;
        }

        public void setActionLib(String actionLib) {
                this.actionLib = actionLib;
        }

        public void setActionLibCreateTime(String actionLib_createTime) {
                this.actionLib_createTime = actionLib_createTime;
        }

        @Override
        public String toString() {
                return "HardwareVersionInfo{"
                        + "head_ver='" + head_ver + '\''
                        + ", head_ver_loader='" + head_ver_loader + '\''
                        + ", head_hor='" + head_hor + '\''
                        + ", head_hor_loader='" + head_hor_loader + '\''
                        + ", left_arm_shoulder_ahead_back='" + left_arm_shoulder_ahead_back + '\''
                        + ", left_arm_shoulder_ahead_back_loader='" + left_arm_shoulder_ahead_back_loader + '\''
                        + ", left_arm_shoulder_top_down='" + left_arm_shoulder_top_down + '\''
                        + ", left_arm_shoulder_top_down_loader='" + left_arm_shoulder_top_down_loader + '\''
                        + ", left_arm_elbow_rotate='" + left_arm_elbow_rotate + '\''
                        + ", left_arm_elbow_rotate_loader='" + left_arm_elbow_rotate_loader + '\''
                        + ", left_arm_elbow_top_down='" + left_arm_elbow_top_down + '\''
                        + ", left_arm_elbow_top_down_loader='" + left_arm_elbow_top_down_loader + '\''
                        + ", left_arm_wrist='" + left_arm_wrist + '\''
                        + ", left_arm_wrist_loader='" + left_arm_wrist_loader + '\''
                        + ", right_arm_shoulder_ahead_back='" + right_arm_shoulder_ahead_back + '\''
                        + ", right_arm_shoulder_ahead_back_loader='" + right_arm_shoulder_ahead_back_loader + '\''
                        + ", right_arm_shoulder_top_down='" + right_arm_shoulder_top_down + '\''
                        + ", right_arm_shoulder_top_down_loader='" + right_arm_shoulder_top_down_loader + '\''
                        + ", right_arm_elbow_rotate='" + right_arm_elbow_rotate + '\''
                        + ", right_arm_elbow_rotate_loader='" + right_arm_elbow_rotate_loader + '\''
                        + ", right_arm_elbow_top_down='" + right_arm_elbow_top_down + '\''
                        + ", right_arm_elbow_top_down_loader='" + right_arm_elbow_top_down_loader + '\''
                        + ", right_arm_elbow_wrist='" + right_arm_wrist + '\''
                        + ", right_arm_elbow_wrist_loader='" + right_arm_wrist_loader + '\''
                        + ", left_arm='" + left_arm + '\''
                        + ", left_arm_loader='" + left_arm_loader + '\''
                        + ", right_arm='" + right_arm + '\''
                        + ", right_arm_loader='" + right_arm_loader + '\''
                        + ", fpga='" + fpga + '\''
                        + ", m4='" + m4 + '\''
                        + ", m4_loader='" + m4_loader + '\''
                        + ", actionLib='" + actionLib + '\''
                        + ", actionLib_createTime='" + actionLib_createTime + '\''
                        + '}';
        }
}

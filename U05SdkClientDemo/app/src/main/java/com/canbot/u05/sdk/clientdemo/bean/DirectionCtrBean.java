package com.canbot.u05.sdk.clientdemo.bean;

public class DirectionCtrBean {
    public enum Direction {
        FORWARD(1),
        BACK(2),
        ROTATE(3);

        Direction(int i) {
        }
    }

    private Direction direction;

    public DirectionCtrBean() {
    }

    public DirectionCtrBean(Direction direction, int speed, int data) {
        this.direction = direction;
        this.speed = speed;
        this.data = data;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        if (direction == Direction.FORWARD || direction == Direction.BACK) {
            if (speed > 800) {
                speed = 800;
            } else if (speed < 300) {
                speed = 300;
            }

        } else {
            if (speed > 20) {
                speed = 20;
            } else if (speed < 10) {
                speed = 10;
            }
        }
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getData() {
        if (direction == Direction.FORWARD || direction == Direction.BACK) {
            if (data < 0) {
                data = 0;
            }
        }
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    /**
     * mm/s, min = 300 , max = 800
     * r/m,(每分钟机器转多少圈)；这个不是很准确， min=10,max=20
     */
    private int speed;

    /**
     * 前进和后退时，表示距离： mm
     * 左转和右转时，表示角度:  >0,向右 ; <0，向左
     */
    private int data;

    @Override
    public String toString() {
        return "DirectionCtrBean{" +
                "direction=" + direction +
                ", speed=" + speed +
                ", data=" + data +
                '}';
    }
}

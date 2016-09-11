package com.ebox.pub.service.task;

import java.util.Timer;
import java.util.TimerTask;

public class TaskData {
	private Timer timer;
	private Long lastRunning;
	private TimerTask task;
	private Integer type;	//��������
	private Integer period;	//��������
	private Integer timeout;	//����ʱʱ��
	
	public TaskData(Integer type,Integer period,Integer timeout)
	{
		this.type = type;
		this.period = period;
		this.timeout = timeout;
	}
	
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	public Long getLastRunning() {
		return lastRunning;
	}
	public void setLastRunning(Long lastRunning) {
		this.lastRunning = lastRunning;
	}
	public TimerTask getTask() {
		return task;
	}
	public void setTask(TimerTask task) {
		this.task = task;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}

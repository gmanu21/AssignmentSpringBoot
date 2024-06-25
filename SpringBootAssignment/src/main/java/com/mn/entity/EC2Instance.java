package com.mn.entity;

public class EC2Instance {
	private String instanceId;

	public EC2Instance(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	@Override
	public String toString() {
		return "EC2Instance [instanceId=" + instanceId + "]";
	}

}

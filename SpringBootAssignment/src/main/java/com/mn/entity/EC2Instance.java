package com.mn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EC2Instance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String instanceId;

	public EC2Instance() {
	
	}
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

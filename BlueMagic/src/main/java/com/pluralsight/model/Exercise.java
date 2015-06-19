package com.pluralsight.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name="exercise")
public class Exercise {
	
	
	@Id
	@GeneratedValue
	@Column(name="exercise_id")
	private Long id;
	
	@Range(min = 1, max = 120)
	@Column(name="minutes")
	private int minutes;
	
	@NotNull
	@Column(name="activity")
	private String activity;
	
	@ManyToOne
	private Goal goal;

	public String getActivity() {
		return activity;
	}

	public Goal getGoal() {
		return goal;
	}

	public Long getId() {
		return id;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
}

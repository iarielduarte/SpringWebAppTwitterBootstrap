package ar.com.api.ibera.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="logs")
@NoArgsConstructor
public class Log {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="date")
	@Getter @Setter private Date date;
	@Column(name="details")
	@Getter @Setter private String details;
	@Column(name="username")
	@Getter @Setter private String username;
	@Column(name="url")
	@Getter @Setter private String url;
	
	public Log(Date date, String details, String username, String url) {
		super();
		this.date = date;
		this.details = details;
		this.username = username;
		this.url = url;
	}
	
	
}

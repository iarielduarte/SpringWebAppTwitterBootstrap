package ar.com.api.ibera.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer")
public class Customer {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@NotNull
	@Size(min=6,max=20)
	@Column(name="dni")
	private String dni;
	
	@NotNull
	@Size(min=2,max=50)
	@Column(name="name")
	private String name;
	
	@Size(min=6,max=20)
	@Column(name="phone")
	private String phone;
	
	@Column(name="celphone")
	private String celphone;
	
	@Column(name="city")
	private String city;
	
	@Size(min=2,max=100)
	@Column(name="address")
	private String address;
	
	@Column(name="province")
	private String province;
	
	
}

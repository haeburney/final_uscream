package com.example.uscream.account;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.Id;

import com.example.uscream.store.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@ManyToOne
	@JoinColumn(name="store", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Store store;
	private String pwd;
	private String managername;
	private String accounttype;
	
	
	
}

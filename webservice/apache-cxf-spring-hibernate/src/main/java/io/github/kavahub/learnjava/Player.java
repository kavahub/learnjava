package io.github.kavahub.learnjava;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * 
 * 实体
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Entity
@Table(name = "PLAYER")
@Data
public class Player {
    @Id 
	@SequenceGenerator(name="player_sequence", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE , generator="player_sequence")
	@Column(name = "PLAYER_ID")
	private int playerId;

	@Column(name= "NAME")
	private String name;

	@Column(name= "AGE")
	private int age;

	@Column(name= "MATCHES")
	private int matches;

	// default constructor
	public Player() {
		super();
	}

	// 3-arg parameterized-constructor
	public Player(String name, int age, int matches) {
		super();
		this.name = name;
		this.age = age;
		this.matches = matches;
	}

	// 4-arg parameterized-constructor
	public Player(int playerId, String name, int age, int matches) {
		super();
		this.playerId = playerId;
		this.name = name;
		this.age = age;
		this.matches = matches;
	}
}

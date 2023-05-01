package com.greenlight.global.domain.model.entity;

import java.sql.Timestamp;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

@DynamicInsert
@Data
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
public abstract class Base {

	@ColumnDefault("'server'")
	private String insMbrId;

	@ColumnDefault("'server'")
	private String updMbrId;

	@CreationTimestamp
	private Timestamp createTm;

	@UpdateTimestamp
	private Timestamp updateTm;

	/*@PrePersist
	public void prePersist() {
//		this.createTm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss"));
//		this.updateTm = createTm;
		this.createTm = LocalDateTime.now();
		this.updateTm = this.createTm;
	}

	@PreUpdate
	public void preUpdate() {
//		this.updateTm = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss"));
		this.createTm = LocalDateTime.now();
	}*/
}

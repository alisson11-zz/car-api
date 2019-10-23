package com.fictiusclean.car.api.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Car {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotEmpty(message = "Nome do carro é de preenchimento obrigatório")
	private String name;

	@NotEmpty(message = "Marca é de preenchimento obrigatório")
	private String brand;

	@NotEmpty(message = "Modelo é de preenchimento obrigatório")
	private String model;

	@NotNull(message = "Data de fabricação é de preenchimento obrigatório")
	private Date manufacturingDate;

	@Column(precision = 15, scale = 3)
	@DecimalMin(value = "0.001", message = "Consumo médio de combustível na cidade deve ter valor mínimo de 0.001")
	@DecimalMax(value = "999.999", message = "Consumo médio de combustível na cidade deve ter valor máximo de 999.999")
	@NotNull(message = "Consumo médio de combustível na cidade é de preenchimento obrigatório")
	private BigDecimal averageCityConsumption;

	@Column(precision = 15, scale = 3)
	@DecimalMin(value = "0.001", message = "Consumo médio de combustível na estrada deve ter valor mínimo de 0.001")
	@DecimalMax(value = "999.999", message = "Consumo médio de combustível na estrada deve ter valor máximo de 999.999")
	@NotNull(message = "Consumo médio de combustível na estrada é de preenchimento obrigatório")
	private BigDecimal averageHighwayConsumption;

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	private LocalDateTime updatedAt;

}

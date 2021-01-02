package com.happy3w.persistence.jpa.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Company")
public class CompanyEntity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "website")
    private String website;

    @Basic
    @Column(name = "catalog")
    private String catalog;

    @Basic
    @Column(name = "favoriteDate")
    private Date favoriteDate;
}

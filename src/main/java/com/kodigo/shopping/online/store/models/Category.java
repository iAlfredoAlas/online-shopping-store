package com.kodigo.shopping.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET category_status = false WHERE id_category=?")
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_category")
    @Getter
    @Setter
    private Long idCategory;

    @Basic(optional = false)
    @Column(name = "category_name")
    @Getter
    @Setter
    private String categoryName;

    @Basic(optional = false)
    @Column(name = "category_description")
    @Getter
    @Setter
    private String categoryDescription;

    @Basic(optional = false)
    @Column(name = "category_status")
    @Getter
    @Setter
    private Boolean isCategoryActive = Boolean.TRUE;

}

package com.kodigo.shopping.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET product_status = false WHERE id_product=?")
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_product")
    @Getter
    @Setter
    private Long idProduct;

    @Basic(optional = false)
    @Column(name = "product_name")
    @Getter
    @Setter
    private String productName;

    @Basic(optional = false)
    @Column(name = "product_description")
    @Getter
    @Setter
    private String productDescription;

    @Basic(optional = false)
    @Column(name = "stock")
    @Getter
    @Setter
    private Integer stock;

    @Basic(optional = false)
    @Column(name = "price")
    @Getter
    @Setter
    private BigDecimal price;

    @Basic(optional = false)
    @Column(name = "product_status")
    @Getter
    @Setter
    private Boolean isProductActive = Boolean.TRUE;

    @JoinColumn(name = "idCategory", referencedColumnName = "id_category", foreignKey = @ForeignKey(name = "FK_product_category"))
    @ManyToOne(optional = false, targetEntity = Category.class)
    @Getter
    @Setter
    private Category idCategory;

}

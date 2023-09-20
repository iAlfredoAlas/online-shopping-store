package com.kodigo.shopping.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@SQLDelete(sql = "UPDATE cart SET cart_status = false WHERE id_cart=?")
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @Column(name = "id_cart")
    @Getter
    @Setter
    private Long idCart;

    @MapsId
    @JoinColumn(name = "id_cart", referencedColumnName = "id_user", foreignKey = @ForeignKey(name = "FK_cart_user"))
    @OneToOne(optional = false, targetEntity = User.class)
    private User idUser;

    @Basic(optional = false)
    @Column(name = "quantity")
    @Getter
    @Setter
    private Integer quatity;

    @Basic(optional = false)
    @Column(name = "cart_status")
    @Getter
    @Setter
    private Boolean isCartActive = Boolean.TRUE;

    @JoinColumn(name = "id_order_detail", referencedColumnName = "id_order_detail", foreignKey = @ForeignKey(name = "FK_cart_order_detail"))
    @ManyToOne(optional = false, targetEntity = OrderDetail.class)
    @Getter
    @Setter
    private OrderDetail idOrderDetail;

    @JoinColumn(name = "id_product", referencedColumnName = "id_product", foreignKey = @ForeignKey(name = "FK_cart_product"))
    @ManyToOne(optional = false, targetEntity = Product.class)
    @Getter
    @Setter
    private Product idProduct;

}

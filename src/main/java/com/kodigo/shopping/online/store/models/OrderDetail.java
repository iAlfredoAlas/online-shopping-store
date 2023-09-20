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
@Table(name = "order_detail")
@SQLDelete(sql = "UPDATE order_detail SET order_detail_status = false WHERE id_order_detail=?")
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_order_detail")
    @Getter
    @Setter
    private Long idOrderDetail;

    @Basic(optional = false)
    @Column(name = "quantity")
    @Getter
    @Setter
    private Integer quantity;

    @Basic(optional = false)
    @Column(name = "subtotal")
    @Getter
    @Setter
    private BigDecimal subtotal;

    @Basic(optional = false)
    @Column(name = "order_detail_status")
    @Getter
    @Setter
    private Boolean isOrderDetailActive  = Boolean.TRUE;

    @JoinColumn(name = "idClientOrder", referencedColumnName = "id_client_order", foreignKey = @ForeignKey(name = "FK_order_detail_client_order"))
    @ManyToOne(optional = false, targetEntity = ClientOrder.class)
    @Getter
    @Setter
    private ClientOrder idClientOrder;

    @JoinColumn(name = "idProduct", referencedColumnName = "id_product", foreignKey = @ForeignKey(name = "FK_order_detail_product"))
    @ManyToOne(optional = false, targetEntity = Product.class)
    @Getter
    @Setter
    private Product idProduct;

}

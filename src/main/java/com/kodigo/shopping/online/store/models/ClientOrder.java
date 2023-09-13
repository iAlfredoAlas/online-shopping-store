package com.kodigo.shopping.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "client_order")
@SQLDelete(sql = "UPDATE client_order SET order_status = false WHERE id_client_order=?")
@AllArgsConstructor
@NoArgsConstructor
public class ClientOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_client_order")
    @Getter
    @Setter
    private Long idClientOrder;

    @Basic(optional = false)
    @Column(name = "order_status")
    @Getter
    @Setter
    private Boolean isClientOrderActive = Boolean.TRUE;

    @Basic(optional = false)
    @Column(name = "date_order")
    @Getter
    @Setter
    private LocalDate dateOrder;

    @JoinColumn(name = "idUser", referencedColumnName = "id_user", foreignKey = @ForeignKey(name = "FK_order_user"))
    @ManyToOne(optional = false, targetEntity = User.class)
    @Getter
    @Setter
    private User idUser;

}

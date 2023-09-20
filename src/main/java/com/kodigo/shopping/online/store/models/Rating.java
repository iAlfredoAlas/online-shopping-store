package com.kodigo.shopping.online.store.models;

import com.kodigo.shopping.online.store.util.ETypeRating;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "rating")
@SQLDelete(sql = "UPDATE rating SET rating_status = false WHERE id_rating=?")
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rating")
    @Getter
    @Setter
    private Long idRating;

    @Basic(optional = false)
    @Column(name = "rating_detail")
    @Getter
    @Setter
    private String ratingDetail;

    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    @Column(name = "type_rating")
    @Getter
    @Setter
    private ETypeRating typeRating;

    @Basic(optional = false)
    @Column(name = "date_rating")
    @Getter
    @Setter
    private LocalDate dateRating;

    @Basic(optional = false)
    @Column(name = "rating_status")
    @Getter
    @Setter
    private Boolean isRatingActive;

    @JoinColumn(name = "idUser", referencedColumnName = "id_user", foreignKey = @ForeignKey(name = "FK_rating_user"))
    @ManyToOne(optional = false, targetEntity = User.class)
    @Getter
    @Setter
    private User idUser;

    @JoinColumn(name = "idProduct", referencedColumnName = "id_product", foreignKey = @ForeignKey(name = "FK_rating_product"))
    @ManyToOne(optional = false, targetEntity = Product.class)
    @Getter
    @Setter
    private Product idProduct;

}

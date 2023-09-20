package com.kodigo.shopping.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rol")
@SQLDelete(sql = "UPDATE rol SET status_rol = false WHERE id_rol=?")
@AllArgsConstructor
@NoArgsConstructor
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol")
    @Getter
    @Setter
    private Long idRol;

    @Basic(optional = false)
    @Column(name = "name_rol")
    @Getter
    @Setter
    private String nameRol;

    @Basic(optional = false)
    @Column(name = "status_rol")
    @Getter
    @Setter
    private Boolean isRolActive = Boolean.TRUE;

}

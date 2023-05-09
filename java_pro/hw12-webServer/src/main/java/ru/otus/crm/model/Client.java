package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private Phone phone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private Address address;

    public Client() {
    }

    public Client(Long id, String name, Address address, Phone phone)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;

        if (address != null) {
            address.setClient(this);
        }

        phone.setClient(this);
    }

    public Client(String name, Address address, Phone phone)
    {
        this(null, name, address, phone);
    }

    @Override
    public Client clone() {

        Address unProxiedAddress = this.address;
        if(this.address instanceof HibernateProxy) {
            unProxiedAddress = Hibernate.unproxy(this.address, Address.class);
        }

        Phone unProxiedPhone = this.phone;
        if(this.phone instanceof HibernateProxy) {
            unProxiedPhone = Hibernate.unproxy(this.phone, Phone.class);
        }

        return new Client(this.id, this.name, unProxiedAddress, unProxiedPhone);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

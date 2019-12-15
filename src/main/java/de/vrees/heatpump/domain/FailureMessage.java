package de.vrees.heatpump.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.vrees.heatpump.domain.enumeration.FailureLevel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A FailureMessage.
 */
@Entity
@Table(name = "failure_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FailureMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "failure_level")
    private FailureLevel failureLevel;

    @Column(name = "msg")
    private String msg;

    @Column(name = "parameter")
    private String parameter;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private Processdata processdata;

    public FailureMessage() {
    }

    public FailureMessage(FailureLevel failureLevel, String msg, String parameter) {
        this.failureLevel = failureLevel;
        this.msg = msg;
        this.parameter = parameter;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FailureLevel getFailureLevel() {
        return failureLevel;
    }

    public FailureMessage failureLevel(FailureLevel failureLevel) {
        this.failureLevel = failureLevel;
        return this;
    }

    public void setFailureLevel(FailureLevel failureLevel) {
        this.failureLevel = failureLevel;
    }

    public String getMsg() {
        return msg;
    }

    public FailureMessage msg(String msg) {
        this.msg = msg;
        return this;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getParameter() {
        return parameter;
    }

    public FailureMessage parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Processdata getProcessdata() {
        return processdata;
    }

    public FailureMessage processdata(Processdata processdata) {
        this.processdata = processdata;
        return this;
    }

    public void setProcessdata(Processdata processdata) {
        this.processdata = processdata;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FailureMessage)) {
            return false;
        }
        return id != null && id.equals(((FailureMessage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FailureMessage{" +
            "id=" + getId() +
            ", failureLevel='" + getFailureLevel() + "'" +
            ", msg='" + getMsg() + "'" +
            ", parameter='" + getParameter() + "'" +
            "}";
    }
}

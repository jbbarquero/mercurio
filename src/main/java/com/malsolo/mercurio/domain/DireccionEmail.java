/*
 * No Copyright 2014 the original author or authors.
 * No special license  * 
 */

package com.malsolo.mercurio.domain;

import java.util.Objects;

/**
 *
 * @author Javier Beneito Barquero <jbbarquero@gmail.com>
 */
public class DireccionEmail extends Direccion {
    
    private String email;

    public DireccionEmail() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.email);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DireccionEmail other = (DireccionEmail) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.email, other.email);
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .addValue(this.getId())
                .addValue(this.email)
                .toString();
    }
    
}

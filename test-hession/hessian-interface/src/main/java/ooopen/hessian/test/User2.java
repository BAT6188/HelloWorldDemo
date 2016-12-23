package ooopen.hessian.test;

import java.io.Serializable;

public class User2 extends User implements Serializable{
    protected String aaa;


    @Override
    public String toString() {
        return "User2{" +
                "aaa='" + aaa + '\'' +
                "} " + super.toString();
    }
}

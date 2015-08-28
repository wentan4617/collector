package com.futong.domain;

import java.io.Serializable;


public class User  {

    /**
     *
     */
    private long id;
    private String userName;
    private int phone;
    private LogFile f;
    
    
    
    public LogFile getF() {
		return f;
	}
	public void setF(LogFile f) {
		this.f = f;
	}
	public User(){}
    public User(long id, String userName, int phone) {
        super();
        this.id = id;
        this.userName = userName;
        this.phone = phone;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", userName=" + userName + ", phone=" + phone +   "]";
    }
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((id == null) ? 0 : id.hashCode());
//        return result;
//    }
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        User other = (User) obj;
//        if (id == null) {
//            if (other.id != null)
//                return false;
//        } else if (!id.equals(other.id))
//            return false;
//        return true;
//    }

}
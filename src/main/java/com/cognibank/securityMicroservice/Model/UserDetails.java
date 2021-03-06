package com.cognibank.securityMicroservice.Model;

import javax.persistence.*;

    @Entity
    public class UserDetails {

        @Id
        private long userId;
        private String email;
        private String phone;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "UserDetails{" +
                    "userId=" + userId +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }


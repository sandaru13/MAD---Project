package com.example.adzz;

public class Product {

        //String productId;
        private String name;
        private String cato;

        public Product(){

        }

        /*public void setProductId(String productId) {
            this.productId = productId;
        }*/

        public void setName(String name) {
            this.name = name;
        }

        public void setCato(String cato) {
            this.cato = cato;
        }

        /*public Product(String productId, String name, String cato) {
            this.productId = productId;
            this.name = name;
            this.cato = cato;
        }*/

        /*public String getProductId() {
            return productId;
        }*/

        public String getName() {
            return name;
        }

        public String getCato() {
            return cato;
        }
}

package org.meicode.meimall.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.meicode.meimall.Utils;

import java.util.ArrayList;

public class Order implements Parcelable {
    private int id;
    private ArrayList<Integer> items;
    private String address;
    private String email;
    private String phoneNumber;
    private String zipCode;
    private String paymentMethod;
    private boolean success;
    private double totalPrice;

    public Order() {
    }

    public Order(ArrayList<Integer> items, String address, String email, String phoneNumber, String zipCode, String paymentMethod, boolean success, double totalPrice) {
        this.id = Utils.getOrderId();
        this.items = items;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.paymentMethod = paymentMethod;
        this.success = success;
        this.totalPrice = totalPrice;
    }

    protected Order(Parcel in) {
        id = in.readInt();
        address = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        zipCode = in.readString();
        paymentMethod = in.readString();
        success = in.readByte() != 0;
        totalPrice = in.readDouble();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", success=" + success +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(zipCode);
        dest.writeString(paymentMethod);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeDouble(totalPrice);
    }
}

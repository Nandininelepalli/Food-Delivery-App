package com.tap.daoimplementation;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tap.dao.OrderDAO;
import com.tap.model.Order;
import com.tap.utility.DBConnection;

public class OrderDAOImpl implements OrderDAO {
	private static final String GET_ORDER_QUERY="Select * from `Order` where `orderId`=?";
	private static final String GET_ALL_ORDERS_QUERY="Select * from `Order`";
	private static final String INSERT_ORDER_QUERY="Insert into `Order`(`totalAmount`,`status`,`paymentMode`)values(?,?,?,?,?)";
	private static final String UPDATE_ORDER_QUERY="Update `Order` set `totalAmount`=? `status`=? `paymentMode`=?"; //where `orderId`=?;
	private static final String DELETE_ORDER_QUERY="Delete from `Order` where `orderId`=?";

	@Override
	public void addOrder(Order order) {
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(INSERT_ORDER_QUERY)){
			//prepareStatement.setDate(1,order.getOrderDate());
			prepareStatement.setDouble(2, order.getTotalAmount());
			prepareStatement.setString(3, order.getStatus());
			prepareStatement.setString(4,order.getPaymentMode());
			int res=prepareStatement.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Order getOrder(int orderId) {
		Order order=null;
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(GET_ORDER_QUERY))
		{
			prepareStatement.setInt(1, orderId);
			ResultSet res=prepareStatement.executeQuery();
//			int userId=res.getInt("userId");
//			int restaurantId=res.getInt("restaurantId");
//			Double totalAmount=res.getDouble("totalAmount");
//			String status=res.getString("status");
//			String paymentMode=res.getString("paymentMode");
//			order=new Order(orderId,userId,restaurantId,null,totalAmount,status,paymentMode);
			order=extractOrder(res);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) {
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(UPDATE_ORDER_QUERY)){
			prepareStatement.setDouble(1, order.getTotalAmount());
			prepareStatement.setString(1, order.getStatus());
			prepareStatement.setString(1, order.getPaymentMode());
			prepareStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrder(int orderId) {
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(DELETE_ORDER_QUERY);){
			prepareStatement.setInt(1, orderId);
			prepareStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getAllOrdersByUser(int userId) {
		ArrayList<Order>orderList=new ArrayList<Order>();
		try {
			Connection connection=DBConnection.getConnection();
			Statement statement=connection.createStatement();
			ResultSet res=statement.executeQuery(GET_ALL_ORDERS_QUERY);
			while(res.next()) {
				Order order=extractOrder(res);
				orderList.add(order);
				}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
	
	
	Order extractOrder(ResultSet res)throws SQLException{
		int orderid=res.getInt("orderId");
		int userid=res.getInt("userId");
		int restaurantid=res.getInt("restaurantId");
		Double totalamount=res.getDouble("totalAmount");
		String status=res.getString("status");
		String paymentmode=res.getString("paymentMode");
		Order order=new Order(orderid,userid,restaurantid,null,totalamount,status,paymentmode);
		return order;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

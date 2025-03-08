package com.tap.daoimplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderItemDAO;
import com.tap.model.OrderItem;
import com.tap.utility.DBConnection;

public class OrderItemImpl implements OrderItemDAO {
	private static final String GET_ORDERITEM_QUERY="Select * from `OrderItem` where `orderId`=?";
	private static final String GET_ALL_ORDERITEMS_QUERY="Select * from `OrderItem`";
	private static final String INSERT_ORDERITEM_QUERY="Insert into `OrderItem`(`orderId`,`menuId`,`quantity`,`totalPrice`)values(?,?,?,?)";
	private static final String UPDATE_ORDERITEM_QUERY="Update `OrderItem` set `menuId`=? `quantity`=?"; //where `orderId`=?;
	private static final String DELETE_ORDERITEM_QUERY="Delete from `OrderItem` where `orderItemId`=?";

	@Override
	public void addOrderItem(OrderItem orderItem) {
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(INSERT_ORDERITEM_QUERY);){
			prepareStatement.setInt(1,orderItem.getOrderId());
			prepareStatement.setInt(1,orderItem.getMenuId());
			prepareStatement.setInt(1,orderItem.getQuantity());
			prepareStatement.setDouble(1,orderItem.getTotalPrice());
			int res=prepareStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public OrderItem getOrderItem(int orderItemId) {
		OrderItem orderItem=null;
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(GET_ORDERITEM_QUERY);){
			prepareStatement.setInt(1, orderItemId);
			ResultSet res=prepareStatement.executeQuery();
//			int orderId=res.getInt("orderId");
//			int menuId=res.getInt("menuId");
//			int quantity=res.getInt("quantity");
//			double totalPrice=res.getDouble("totalPrice");
//			orderItem=new OrderItem(orderItemId,orderId,menuId,quantity,totalPrice);
			orderItem=extractOrderItem(res);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return orderItem;
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(UPDATE_ORDERITEM_QUERY);){
			prepareStatement.setInt(1, orderItem.getMenuId());
			prepareStatement.setInt(2,orderItem.getQuantity());
			prepareStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrderItem(int orderItemId) {
		try(Connection connection=DBConnection.getConnection();
			PreparedStatement prepareStatement=connection.prepareStatement(DELETE_ORDERITEM_QUERY);){
			prepareStatement.setInt(1, orderItemId);
			prepareStatement.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderItem> getOrderItemsByOrder(int orderId) {
		ArrayList<OrderItem>orderItemList=new ArrayList<OrderItem>();
		try {
			Connection connection=DBConnection.getConnection();
			Statement statement=connection.createStatement();
			ResultSet res=statement.executeQuery(GET_ALL_ORDERITEMS_QUERY);
			while(res.next()) {
				OrderItem orderItem=extractOrderItem(res);
				orderItemList.add(orderItem);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return orderItemList;
	}
	
	
	OrderItem extractOrderItem(ResultSet res)throws SQLException{
		int orderitemid=res.getInt("orderItemId");
		int orderid=res.getInt("orderId");
		int menuid=res.getInt("menuId");
		int quantity=res.getInt("quantity");
		double totalprice=res.getDouble("totalPrice");
		 OrderItem orderItem=new OrderItem(orderitemid,orderid,menuid,quantity,totalprice);
		 return orderItem;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

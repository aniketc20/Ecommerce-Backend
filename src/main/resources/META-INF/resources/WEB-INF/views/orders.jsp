<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	    <script src=
	"https://code.jquery.com/jquery-3.6.0.min.js">
	    </script>
    	<style>
			table {
			  font-family: arial, sans-serif;
			  border-collapse: collapse;
			  width: 100%;
			}
			
			td, th {
			  border: 1px solid #dddddd;
			  text-align: left;
			  padding: 8px;
			}
			
			tr:nth-child(even) {
			  background-color: #dddddd;
			}
			.counter input {
			    width: 50px;
			    line-height: 30px;
			    font-size: 20px;
			    text-align: center;
			    
			}
		</style>
    </head>
<body>
<h3>My Orders</h3>
<table>
	<tr>
		<th>Products</th>
		<th>Total</th>
	</tr>
		<c:forEach var="order" items="${orders}">
	        <tr>
	        	<td>
		        	<c:forEach var="product" items="${order.products}">
		        		<c:out value="${product.product}"/>,
		        	</c:forEach>
	        	</td>
	            <td><c:out value="${order.totalPrice}"/></td>
	    	</tr>
	    </c:forEach>
</table>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <h3>
    	Dash-board
    </h3>
    <body>
    	<form:form action="add" method="post" modelAttribute="product">
	    	<form:input path="productName" type="text" class="form-control"
	        required="required" />
	        <form:input path="price" type="number" class="form-control"
	        required="required" />
	        <form:input path="desc" type="text" class="form-control"
	        required="required" />
        <form:button>Save</form:button>
    </form:form>
    
    <h3>Products Details</h3>
	<hr size="4" color="gray"/>
	<table>
		<tr>
		    <th>Product</th>
		    <th>Description</th>
		    <th>Price</th>
		    <th>Add/Delete</th>
	  	</tr>
	    <c:forEach var="product" items="${products}">
	        <tr>
	            <td><c:out value="${product.productName}"/></td>
	            <td><c:out value="${product.desc}"/></td>
	            <td><c:out value="${product.price}"/></td>
	            <td>
	            	<div class="counter">
					  <button class="down" onClick='decreaseCount(event, this, ${product.price}, ${product.productId})'>-</button>
					  <input type="text" value="0" disabled>
					  <button class="up" onClick='increaseCount(event, this, ${product.price}, ${product.productId})'>+</button>
					</div>
	            </td>
	        </tr>
	    </c:forEach>
	</table>
		<h4>
	    	Total price:<p id="demo"></p>
	    </h4>
	    <button onClick="buy()">Checkout</button>
	<script>
	let sum = 0;
	const products = {};
	function increaseCount(a, b, price, pId) {
		  var input = b.previousElementSibling;
		  var value = parseInt(input.value, 10);
		  value = isNaN(value) ? 0 : value;
		  value++;
		  input.value = value;
		  sum = sum + price;
		  if(products[pId]) {
			  products[pId] = products[pId] + 1;
		  }
		  else {
			  products[pId] = 1;
		  }
		  console.log(products);
		  document.getElementById("demo").innerHTML = sum;
		}

		function decreaseCount(a, b, price, pId) {
		  var input = b.nextElementSibling;
		  var value = parseInt(input.value, 10);
		  if (value > 0) {
		    value = isNaN(value) ? 0 : value;
		    value--;
		    input.value = value;
		    sum = sum - price;
		    let i = 0;
		    if(products[pId]) {
				  products[pId] = products[pId] - 1;
				  if(products[pId] == 0) {
					  delete products[pId];
				  }
			  }
			  else {
				  products[pId] = 1;
			  }
		    console.log(products);
		    document.getElementById("demo").innerHTML = sum;
		  }
		}
		
		function buy() {
			$.ajax({
			    type : "POST",
			    url : "/buy",
			    data : {prods:products,cost:sum},
			    timeout : 100000,
			    success : function(response) {
			        console.log("SUCCESS"); 
			    },
			    error : function(e) {
			        console.log("ERROR: ", e);
			    },
			    done : function(e) {
			        console.log("DONE");
			    }
			});
			}
	</script>
    </body>
</html>

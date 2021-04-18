var stompClient = null;

function connect() {
	var socket = new SockJS('/flighttickets-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		console.log('Connected: ' + frame);
		stompClient.subscribe('/update/availability', function(updateData) {
			if (JSON.parse(updateData.body).statusCodeValue == 200) {
				showUpdatedAvailability(
						JSON.parse(updateData.body).body[0].capacity, JSON
								.parse(updateData.body).body[0].flightNumber);
			} else if (JSON.parse(updateData.body).statusCodeValue == 406) {
				alert("Opps!!! Required Number of Tickets Not available");
			} else if (JSON.parse(updateData.body).statusCodeValue == 400) {
				alert("Opps!!! Bad Request Please check request Body");
			}
		});
	});
}

function buyTicket(fNo) {

	var no_tickets = prompt('Enter Number of Tickets');
	if (no_tickets <= 0) {
		alert("Invalid Ticket quantity")
	} else {
		stompClient.send("/flight/bookticket", {}, JSON.stringify({
			'flightNumber' : fNo.id,
			'noOfTickets' : no_tickets
		}));
	}
}

function searchFlights() {
	var scity = document.getElementById("scity").value;
	var dcity = document.getElementById("dcity").value;
	var http = new XMLHttpRequest();
	var url = 'http://localhost:8080/flight/searchflights';
	let params = new Object();
	params.sourcecity = scity;
	params.destinationcity = dcity;
	http.open('POST', url, true);

	http.setRequestHeader('Content-type', 'application/json');

	http.onreadystatechange = function() {

		if (http.readyState == 4 && http.status == 200) {
			responseData = JSON.parse(http.responseText);
			var htmlheading = "<table border='2'>";
			htmlheading += "<tr>";
			htmlheading += "<td>Flight Number</td>";
			htmlheading += "<td>Source City</td>";
			htmlheading += "<td>Destination City</td>";
			htmlheading += "<td>Flight Duration</td>";
			htmlheading += "<td>Available Tickets</td>";
			htmlheading += "<td>Buy Ticket</td>";
			htmlheading += "</tr>";
			htmlheading += "</table>";
			document.getElementById("flightsheading").innerHTML = htmlheading;

			var html = "<table border='2'>";
			for (i = 0; i < responseData.length; i++) {
				html += "<tr>";
				html += "<td>" + responseData[i].flightNumber + "</td>";
				html += "<td>" + responseData[i].sourceCity + "</td>";
				html += "<td>" + responseData[i].desctinationCity + "</td>";
				html += "<td>" + responseData[i].duration + "</td>";
				html += '<td id=' + responseData[i].flightNumber + '>'
						+ responseData[i].capacity + '</td>';
				html += "<td><input type='button' value='Buy Ticket' onclick='buyTicket("
						+ responseData[i].flightNumber + ")'></td>";
				html += "</tr>";
			}
			html += "</table>";
			document.getElementById("flights").innerHTML = html;
		} else if (http.readyState == 4 && http.status == 404) {
			alert("No Flights Found");
		}
	}
	http.send(JSON.stringify(params));
}

function showUpdatedAvailability(updatedCapacity, flightNumber) {
	document.getElementById(flightNumber).innerHTML = "<td>" + updatedCapacity
			+ "</td>";
}
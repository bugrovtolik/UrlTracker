var stompClient = null;
var urls = [];
var interval = setInterval(sendUrls, 1000);

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#info").show();
    }
}

function connect() {
    var socket = new SockJS('/wsocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/info/urldata', function (info) {
			var data = JSON.parse(info.body);
            showUrlInfo(data.url, data.time, data.foundWord, data.codeLength);
        });
    });
}

function disconnect() {
	clearInterval(interval);
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function addUrl() {
	var json = {'url': $("#url").val(), 'word': $("#word").val()};
	var contains = false;
	for (var i in urls) {
		if (urls[i].url === $("#url").val()) {
			contains = true;
		}
	}
	if (!contains) {
		urls.push(json);
	}
}

function sendUrls() {
	for (var i in urls) {
		stompClient.send("/link/getinfo", {}, JSON.stringify(urls[i]));
	}
}

function showUrlInfo(url, time, foundWord, codeLength) {	
	
	var table = document.getElementById("info");
	for (var r = 0; r < table.rows.length; r++) {
		if (table.rows[r].cells[0].innerHTML === url) {
			if (table.rows[r].cells[1].innerHTML < time) {
				table.rows[r].cells[1].setAttribute("bgcolor","#f08080"); //light red
			} else {
				table.rows[r].cells[1].setAttribute("bgcolor","#90ee90"); //light green
			}
			table.rows[r].cells[1].innerHTML = time;
			table.rows[r].cells[2].innerHTML = foundWord;
			table.rows[r].cells[3].innerHTML = codeLength;
			break;
		} else if (r === table.rows.length - 1) {
			var row = document.createElement("tr");

			var cell = document.createElement("td");
			var cellText = document.createTextNode(url);
			cell.appendChild(cellText);
			row.appendChild(cell);

			cell = document.createElement("td");
			cellText = document.createTextNode(time);
			cell.appendChild(cellText);
			row.appendChild(cell);
			
			cell = document.createElement("td");
			cellText = document.createTextNode(foundWord);
			cell.appendChild(cellText);
			row.appendChild(cell);
			
			cell = document.createElement("td");
			cellText = document.createTextNode(codeLength);
			cell.appendChild(cellText);
			row.appendChild(cell);
			$("#info").append(row);
		}
	}
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { addUrl(); });
});

